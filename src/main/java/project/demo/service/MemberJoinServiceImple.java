package project.demo.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import project.demo.domain.Member;
import project.demo.dto.*;
import project.demo.repository.MemberRepository;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberJoinServiceImple implements MemberJoinService {

    private final MemberRepository m;

    private final JavaMailSender mailSender;
    //ID 중복체크
    @Override
    public boolean idOverlap(IdGetDto idGetDto) {
        String id = idGetDto.getId();
        String result = m.findMemberById(id);
        if(StringUtils.isEmpty(result)){
            return true;
        }
        return false;
    }
    //닉네임 중복체크
    @Override
    public boolean nickNameOverlap(NickNameGetDto nickNameGetDto) {
        String nickName = nickNameGetDto.getNickName();
        String result = m.findMemberByNickName(nickName);
        if(StringUtils.isEmpty(result)){
            return true;
        }
        return false;
    }

    @Override
    public int phoneMessage(PhoneNumberGetDto phoneNumberGetDto) {
        String phoneNumber = phoneNumberGetDto.getPhoneNumber();
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
    //인증번호 난수생성
    @Override
    public int makeNumber() {
        return (int)(Math.random()*100000);
    }

    //sens api 암호화화
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

    //email 보내기
    @Override
    public String sendMail(EmailAddressGetDto emailAddressDto) {
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


    @Override
    public boolean makeMember(MemberJoinGetDto memberJoinGetDto) {

        String tempIndex;
        String index;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdfyyMM = new SimpleDateFormat("yyMM");
        String strTodayymm = sdfyyMM.format(c.getTime());

        SimpleDateFormat sdfmm = new SimpleDateFormat("MM");
        String strTodaymm = sdfmm.format(c.getTime());
        SimpleDateFormat sdfdd = new SimpleDateFormat("dd");
        int strTodaydd = Integer.parseInt(sdfdd.format(c.getTime()));
        String maxIndex = m.findMaxIndex();

        if(StringUtils.isEmpty(maxIndex)) {
             index = strTodayymm + "001";
        } else {
             tempIndex = maxIndex.substring(2, 4);
             log.info(tempIndex);
            int indexNumber = 0;
            if(strTodaydd == 1 && tempIndex != strTodaymm) {
                indexNumber = 1;
            }else {
                indexNumber = Integer.parseInt(maxIndex.substring(4,7));
                indexNumber++;
            }
            String indexBack = String.format("%03d", indexNumber);
            index = strTodayymm + indexBack ;
        }
        String salt = salt();
        Member member = new Member();
        member.setId(memberJoinGetDto.getId());
        member.setIndex(index);
        member.setEmail(memberJoinGetDto.getEmail());
        member.setGender(memberJoinGetDto.getGender());
        member.setName(memberJoinGetDto.getName());
        member.setPw(SHA512(memberJoinGetDto.getPw(), salt));
        member.setPhoneNumber(memberJoinGetDto.getPhoneNumber());
        member.setJoinDate(LocalDateTime.now());
        member.setSalt(salt);
        boolean result = m.insertMember(member);

        return result;

    }
    //비밀번호 암호화

    @Override
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

    @Override
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
