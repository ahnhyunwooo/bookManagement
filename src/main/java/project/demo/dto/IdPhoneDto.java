package project.demo.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
public class IdPhoneDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String id;

    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    private String phone;
}
