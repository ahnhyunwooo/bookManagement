package project.demo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class BookRegisterDto {


    private List<MultipartFile> file;
    @NotBlank(message = "책 이름을 입력해주세요.")
    private String bookName;
    @NotBlank(message = "책 출판사를 입력해주세요.")
    private String bookPublisher;
    @NotBlank(message = "책 저자를 입력해주세요.")
    private String bookAuthor;
    @NotBlank(message = "책 장르를 선택해주세요.")
    private String bookGenre;
    @NotBlank(message = "개수를 입력해주세요.")
    private String bookCount;

    public boolean fileEmpty(List<MultipartFile> file) {
        for (MultipartFile multipartFile : file) {
            if(multipartFile.getOriginalFilename().equals("") || multipartFile.getOriginalFilename() ==null) {
                return true;
            }
        }
        return false;
    }
}
