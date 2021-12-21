package project.demo.service;

import project.demo.domain.Member;
import project.demo.dto.IdPwDto;
import project.demo.dto.NameEmailDto;
import project.demo.dto.NamePhoneDto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public interface MemberLoginService {
    int idSamePw(IdPwDto idPwDto);
    boolean idSearchByPhone(NamePhoneDto namePhoneDto);
    void phoneMessage(String phoneNumber, String id);
    String makeSignature(String timestamp, String url, String accessKey, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException;
    boolean idSearchByEmail(NameEmailDto nameEmailDto);
    void sendMail(String email, String id);
    Optional<Member> findLoginMember(String id);
}
