package project.demo.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.*;

@Slf4j
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
    private String fileIndexTemp;
    public void fileCheck() {
        for(int i=0; i<file.size(); i++) {
            if(file.get(i).getOriginalFilename().equals("")) {
                file.remove(i);
                i--;
            }
        }
    }
    /**
     multipartFiles 기존 파일
     name 새로운 파일 이름
     file 새롭게 변경된 파일
     newFiles 최종
     */
    public List<MultipartFile> fileUpdate(List<MultipartFile>multipartFiles, String [] name) {
        List<MultipartFile> newFiles = new ArrayList<>();
        Set<String> set = new HashSet<>();
        for (int i =0 ; i<name.length; i++) {
            if(name[i].equals("") || set.contains(name[i])) {
                continue;
            }
            set.add(name[i]);
            for(int j=0; j<multipartFiles.size(); j++) {
                if(name[i].equals(multipartFiles.get(j).getOriginalFilename())) {
                    newFiles.add(multipartFiles.get(j));
                    break;
                }
            }
        }
        for(int i=0;i< newFiles.size() ; i++) {
            file.add(newFiles.get(i));
        }
        fileCheck();
        return file;
    }
}
