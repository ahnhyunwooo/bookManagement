package project.demo.service;

import project.demo.dto.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface MemberJoinService {

    public boolean idOverlap(IdDto idDto);
    public boolean nickNameOverlap(NickNameDto nickNameDto);
    public int phoneMessage(PhoneNumberDto phoneNumberDto);
    public String makeSignature(String timestamp, String url, String accessKey, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException;
    public int makeNumber();
    public String sendMail(EmailAddressDto emailAddressDto);
    public boolean makeMember(MemberJoinGetDto member);
    public String salt();
    public String SHA512(String pw, String hash);
}
