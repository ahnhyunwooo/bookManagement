package project.demo.service;

import project.demo.domain.Member;
import project.demo.dto.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface MemberJoinService {

    public boolean idOverlap(IdGetDto idGetDto);
    public boolean nickNameOverlap(NickNameGetDto nickNameGetDto);
    public int phoneMessage(PhoneNumberGetDto phoneNumberGetDto);
    public String makeSignature(String timestamp, String url, String accessKey, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException;
    public int makeNumber();
    public String sendMail(EmailAddressGetDto emailAddressDto);
    public boolean makeMember(MemberJoinGetDto member);
    public String salt();
    public String SHA512(String pw, String hash);
}
