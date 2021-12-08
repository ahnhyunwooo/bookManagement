package project.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class MemberJoinGetDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^[a-z]+[a-z0-9]{5,19}$",
    message = "아이디를 영문자로 시작하는 영문자 또는 숫자 6~20자로 입력하세요.")
    private String id;
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",
            message = "최소 8 자, 하나 이상의 문자와 하나의 숫자로 입력하세요.")
    private String pw;
    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^([a-zA-Z0-9ㄱ-ㅎ|ㅏ-ㅣ|가-힣]).{1,5}$",
            message = "2~6자 입력해주세요.")
    private String nickName;
    @NotBlank(message = "성별을 선택해주세요.")
    private String gender;
    @NotBlank(message = "핸드폰을 입력해주세요.")
    private String phoneNumber;
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
}
