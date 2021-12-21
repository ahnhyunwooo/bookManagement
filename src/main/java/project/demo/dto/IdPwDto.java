package project.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class IdPwDto {
    @NotBlank(message = "아이디를 입력해주세요.")
    String id;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    String pw;
}
