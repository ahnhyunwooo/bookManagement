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
import project.demo.dto.EmailAddressGetDto;
import project.demo.dto.IdGetDto;
import project.demo.dto.NickNameGetDto;
import project.demo.dto.PhoneNumberGetDto;
import project.demo.repository.MemberRepositoryImple;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberJoinService implements MemberJoinServiceImple{

    private final MemberRepositoryImple m;

    private final JavaMailSender mailSender;
    //ID 중복체크
    @Override
    public boolean idOverlap(IdGetDto idGetDto) {
        String id = idGetDto.getId();
        List<String>  result = m.findMemberById(id);
        if(result.isEmpty()){
            return true;
        }
        return false;
    }
    //닉네임 중복체크
    @Override
    public boolean nickNameOverlap(NickNameGetDto nickNameGetDto) {
        String nickName = nickNameGetDto.getNickName();
        List<String> result = m.findMemberByNickName(nickName);
        if(result.isEmpty()){
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

    private int indexNumber = 0;
    private boolean[] calendarCheck = {false,false,false,false,false,false,false,false,false,false,false,false,false};

    @Override
    public boolean makeMember(Member member) {

        Calendar c = Calendar.getInstance();

        SimpleDateFormat sdfyyMM = new SimpleDateFormat("yyMM");
        int strTodayYYMM = Integer.parseInt(sdfyyMM.format(c.getTime()));

        SimpleDateFormat sdfMM = new SimpleDateFormat("MM");
        int strTodayMM = Integer.parseInt(sdfMM.format(c.getTime()));

        SimpleDateFormat sdfdd = new SimpleDateFormat("dd");
        int strTodaydd = Integer.parseInt(sdfdd.format(c.getTime()));


        if((strTodaydd == 1) && (!calendarCheck[strTodayMM])){
            indexNumber = 0;
            calendarCheck[strTodayMM] = true;
            if(strTodayMM == 1){
                calendarCheck[12] = false;
            } else {
                calendarCheck[strTodayMM-1] = false;
            }
        }
        indexNumber++;
        String resultIndexNumber = String.format("%03d", indexNumber);

        String memberIndex = strTodayYYMM + String.valueOf(resultIndexNumber);
        member.setIndex(memberIndex);
        member.setJoinDate(LocalDateTime.now());
        //joindate
        boolean result = m.insertMember(member);

        return result;

    }
}
