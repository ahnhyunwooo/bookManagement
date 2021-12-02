package project.demo.service;

import project.demo.dto.EmailAddressDto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface MemberJoinServiceImple {

    public boolean idOverlap(Map<String,String> id);
    public boolean nickNameOverlap(Map<String,String> nickName);
    public int phoneMessage(Map<String,String> phoneNumber);
    public String makeSignature(String timestamp, String url, String accessKey, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException;
    public int makeNumber();
    public String sendMail(EmailAddressDto emailAddressDto);
}
