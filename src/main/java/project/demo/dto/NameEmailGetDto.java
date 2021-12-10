package project.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NameEmailGetDto {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
}
