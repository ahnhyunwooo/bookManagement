package project.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import project.demo.domain.Member;
import project.demo.dto.IdPwGetDto;
import project.demo.dto.NameEmailGetDto;
import project.demo.dto.NamePhoneGetDto;
import project.demo.repository.MemberRepository;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberLoginServiceImple implements MemberLoginService {


    private final MemberRepository mr;
    private final MemberJoinService mj;
    private final JavaMailSender mailSender;

    //아이디 비밀번호 맞는지 확인
    @Override
    public int idSamePw(IdPwGetDto idPwGetDto) {
        String id = idPwGetDto.getId();
        String salt = null;
        String loginPw = null;
        String pw = idPwGetDto.getPw();
        List<Object[]> saltAndPw = mr.findSaltAndPwById(id);
        //아이디 없음
        if(saltAndPw.isEmpty()) {
            return 0;
        }
        for(Object[] info : saltAndPw) {
            salt = (String)info[0];
            loginPw = (String)info[1];
        }
        String dbPw = mj.SHA512(pw, salt);
        //아이디 비번 일치
        if(dbPw.equals(loginPw)) {
            return 1;
        }
        //아이디 일치 비번 불일치
        return -1;
    }

    @Override
    public boolean idSearchByPhone(NamePhoneGetDto namePhoneGetDto) {
        String name = namePhoneGetDto.getName();
        String phone = namePhoneGetDto.getPhone();
        String id = mr.idSearchByNameAndPhone(name, phone);
        log.info(id);
        if(id.isEmpty()) {
            return false;
        }
        phoneMessage(phone, id);
        return true;
    }

    //핸드폰으로 아이디 보내기
    @Override
    public void phoneMessage(String phoneNumber, String id) {
        String timestamp = Long.toString(System.currentTimeMillis());
        String ncpServiceID = "ncp:sms:kr:275984439775:toyproject";
        String url = "https://sens.apigw.ntruss.com/sms/v2/services/"+ncpServiceID+"/messages";
        String urlBack ="/sms/v2/services/"+ncpServiceID+"/messages";
        String accessKey = "AkHun7IKEQmKYKTYXnBa";
        String method = "POST";
        String secretKey ="sdsrwMTdaWwrRlQLLRoyPNjiqHscB8ynnGEde2Rq";
        JSONObject bodyJson = new JSONObject();
        JSONObject toJson = new JSONObject();
        JSONArray toArr = new JSONArray();
        toJson.put("content", "상상도서관 아이디"+"["+id+"]"+"입니다.");
        toJson.put("to", phoneNumber);
        toArr.add(toJson);
        bodyJson.put("type", "sms");
        bodyJson.put("contentType", "COMM");
        bodyJson.put("countryCode", "82");
        bodyJson.put("from","01099498902");
        bodyJson.put("content", "상상도서관");
        bodyJson.put("messages", toArr);

        String body = bodyJson.toJSONString();
        log.info(body);
        try {
            URL requestUrl = new URL(url);
            HttpURLConnection con = (HttpURLConnection)requestUrl.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("x-ncp-apigw-timestamp", timestamp);
            con.setRequestProperty("x-ncp-iam-access-key", accessKey);
            con.setRequestProperty("x-ncp-apigw-signature-v2", makeSignature(timestamp, urlBack, accessKey, secretKey));
            con.setRequestMethod(method);
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(body.getBytes());
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            log.info(String.valueOf(responseCode));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String makeSignature(String timestamp, String url, String accessKey, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        String space = " "; // one space
        String newLine = "\n"; // new line
        String method = "POST";
        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();
        SecretKeySpec signingKey = null;

        try {
            signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Mac mac = null;
        mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);
        byte[] rawHmac = new byte[0];
        try {
            rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String encodeBase64String = Base64.encodeBase64String(rawHmac);
        return encodeBase64String;
    }

    @Override
    public boolean idSearchByEmail(NameEmailGetDto nameEmailGetDto) {
        String name = nameEmailGetDto.getName();
        String email = nameEmailGetDto.getEmail();
        log.info("{}", nameEmailGetDto);
        String id = mr.idSearchByNameAndEmail(name, email);
        if(id.isEmpty()) {
            return false;
        }
        sendMail(email, id);
        return true;
    }
    //이메일 아이디 찾기
    @Override
    public void sendMail(String email, String id) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("아이디 찾기 메일 전송");
        message.setText("상상도서관 아이디는 : "+id+ "입니다.");
        mailSender.send(message);
        log.info(String.valueOf(message));
    }
    //로그인 멤버 정보 찾기
    @Override
    public Optional<Member> findLoginMember(String id) {
        return mr.findMemberByLoginId(id);
    }
}
