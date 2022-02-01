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
import project.demo.dto.*;
import project.demo.repository.MemberRepository;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberLoginServiceImple implements MemberLoginService {


    private final MemberRepository mr;
    private final MemberJoinService mj;
    private final JavaMailSender mailSender;

    /**
     * 아이디 비밀번호 일치,불일치 체크
     */
    @Override
    public int idSamePw(IdPwDto idPwDto) {
        String id = idPwDto.getId();
        String salt = null;
        String loginPw = null;
        String pw = idPwDto.getPw();
        List<Object[]> saltAndPw = mr.findSaltAndPwById(id);

        //아이디 불일치
        if(saltAndPw.isEmpty()) {
            return 0;
        }

        //비번 체크
        for(Object[] info : saltAndPw) {
            salt = (String)info[0];
            loginPw = (String)info[1];
        }
        String dbPw = mj.SHA512(pw, salt);

        //아이디와 비번 일치
        if(dbPw.equals(loginPw)) {
            return 1;
        }

        //아이디 일치, 비번 불일치
        return -1;
    }

    /**
     * 핸드폰번호로 id 찾기
     */
    @Override
    public boolean idSearchByPhone(NamePhoneDto namePhoneDto) {
        String name = namePhoneDto.getName();
        String phone = namePhoneDto.getPhone();
        String id = mr.idSearchByNameAndPhone(name, phone);
        log.info(id);
        if(id.isEmpty()) {
            return false;
        }
        phoneMessage(phone, id);
        return true;
    }

    /**
     * 핸드폰번호로 id 값 보내기
     */
    //@Override
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

    //@Override
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

    /**
     * email로 id 찾기
     */
    @Override
    public boolean idSearchByEmail(NameEmailDto nameEmailDto) {
        String name = nameEmailDto.getName();
        String email = nameEmailDto.getEmail();
        log.info("{}", nameEmailDto);
        String id = mr.idSearchByNameAndEmail(name, email);
        if(id.isEmpty()) {
            return false;
        }
        sendMail(email, id);
        return true;
    }

    /**
     * email로 id 값 보내기
     */
    //@Override
    public void sendMail(String email, String id) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("아이디 찾기 메일 전송");
        message.setText("상상도서관 아이디는 : "+id+ "입니다.");
        mailSender.send(message);
        log.info(String.valueOf(message));
    }

    /**
     * 로그인 멤버 정보 찾기
     */
    @Override
    public Optional<Member> findLoginMember(String id) {
        return mr.findMemberByLoginId(id);
    }

    /**
     * 핸드폰번호로 인증번호 보내기
     */
    @Override
    public int phoneMessage(String phone) {
        String phoneNumber = phone;
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
        int number = makeNumber();
        toJson.put("content", "상상도서관 인증번호"+"["+number+"]"+"입니다.");
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

        return number;
    }

    /**
     * 인증번호 난수 생성
     */
    //@Override
    public int makeNumber() {
        return (int)(Math.random()*100000);
    }

    /**
     * 핸드폰번호로 pw 찾기
     */
    @Override
    public boolean pwSearchByPhone(IdPhoneDto idPhoneDto) {
        String id = idPhoneDto.getId();
        String phone = idPhoneDto.getPhone();
        String name = mr.pwSearchByIdAndPhone(id, phone);
        log.info(name);
        if(name.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * email로 pw 찾기
     */
    @Override
    public boolean pwSearchByEmail(IdEmailDto idEmailDto) {
        String id = idEmailDto.getId();
        String email = idEmailDto.getEmail();
        log.info("{}", idEmailDto);
        String name = mr.pwSearchByIdAndEmail(id, email);
        if(id.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * email로 인증번호 전송
     */
    @Override
    public String sendMailCertification(EmailAddressDto emailAddressDto) {
        String email = emailAddressDto.getEmailAddress();
        Random random = new Random();
        String key = "";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        for(int i=0; i<3; i++) {
            int index = random.nextInt(25)+65;
            key += (char)index;
        }
        key += makeNumber();
        message.setSubject("인증번호 입력을 위한 메일 전송");
        message.setText("상상도서관 인증번호 : "+key+ "입니다.");
        mailSender.send(message);
        log.info(String.valueOf(message));
        return key;
    }

    /**
     * 새 비밀번호 등록하기
     */
    @Override
    public void memberUpdate(IdPwDto idPwDto) {

        log.info("pwUpdate Service@@@@@@@@@@@@@@@@@@@@@@");
        String index = mr.findMemberByIndex(idPwDto.getId());
        String pw = SHA512(idPwDto.getPw(), salt());

        log.info("222222222index :: " + index);
        log.info("222222222pw :: " + pw);

        mr.pwUpdate(index, pw);
        return;
    }

    /**
     * id, phoneNumber로 회원정보 체크
     */
    @Override
    public boolean findMemberByIdAndPhone(String id, String phone) {
        Optional<Member> member = mr.findNameByIdAndPhone(id, phone);
        if(member.isEmpty()){
            return false;
        } else {
            return true;
        }
    }

    //비밀번호 암호화
    public String salt() {
        String salt="";
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] bytes = new byte[16];
            random.nextBytes(bytes);
            salt = new String(java.util.Base64.getEncoder().encode(bytes));}
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } return salt;

    }

    public String SHA512(String pw, String hash) {
        String salt = hash+pw;
        String hex = null;
        try {
            MessageDigest msg = MessageDigest.getInstance("SHA-512");
            msg.update(salt.getBytes());
            hex = String.format("%128x", new BigInteger(1, msg.digest()));
        } catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
        return hex;
    }
}
