package project.demo.dto;

import lombok.Data;

@Data
public class MyMenuSenderDto {
    private String text;
    private String url;

    public MyMenuSenderDto(String text, String url) {
        this.text = text;
        this.url = url;
    }
}
