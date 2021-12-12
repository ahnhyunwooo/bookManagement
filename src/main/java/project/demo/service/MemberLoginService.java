package project.demo.service;

import project.demo.dto.EmailAddressGetDto;
import project.demo.dto.IdPwGetDto;
import project.demo.dto.NameEmailGetDto;
import project.demo.dto.NamePhoneGetDto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface MemberLoginService {
    int idSamePw(IdPwGetDto idPwGetDto);
    boolean idSearchByPhone(NamePhoneGetDto namePhoneGetDto);
    void phoneMessage(String phoneNumber, String id);
    String makeSignature(String timestamp, String url, String accessKey, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException;
    boolean idSearchByEmail(NameEmailGetDto nameEmailGetDto);
    void sendMail(String email, String id);
}
