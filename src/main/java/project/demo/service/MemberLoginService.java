package project.demo.service;

import project.demo.domain.Member;
import project.demo.dto.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public interface MemberLoginService {
    /**
     * 아이디 비밀번호 일치,불일치 체크
     */
    int idSamePw(IdPwDto idPwDto);

    /**
     * 핸드폰번호로 id 찾기
     */
    boolean idSearchByPhone(NamePhoneDto namePhoneDto);

    /**
     * email로 id 찾기
     */
    boolean idSearchByEmail(NameEmailDto nameEmailDto);

    /**
     * 로그인 멤버 정보 찾기
     */
    Optional<Member> findLoginMember(String id);

    /**
     * 핸드폰번호로 인증번호 보내기
     */
    public int phoneMessage(PhoneNumberDto phoneNumberDto);

    /**
     * 핸드폰번호로 pw 찾기
     */
    boolean pwSearchByPhone(IdPhoneDto idPhoneDto);

    /**
     * email로 pw 찾기
     */
    boolean pwSearchByEmail(IdEmailDto idEmailDto);

    /**
     * email로 인증번호 전송
     */
    public String sendMailCertification(EmailAddressDto emailAddressDto);


    //void phoneMessage(String phoneNumber, String id);
    //String makeSignature(String timestamp, String url, String accessKey, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException;
    //void sendMail(String email, String id);
    //public int makeNumber();

}
