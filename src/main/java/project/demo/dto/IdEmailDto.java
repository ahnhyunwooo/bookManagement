package project.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class IdEmailDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String id;

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
}
