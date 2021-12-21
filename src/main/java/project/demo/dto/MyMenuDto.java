package project.demo.dto;

import lombok.Data;

@Data
public class MyMenuDto {
    private String text;
    private String url;

    public MyMenuDto(String text, String url) {
        this.text = text;
        this.url = url;
    }
}
