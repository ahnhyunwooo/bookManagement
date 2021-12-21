package project.demo.service;
import org.springframework.web.multipart.MultipartFile;
import project.demo.domain.UploadFile;
import project.demo.dto.BookRegisterDto;
import project.demo.dto.MyMenuDto;

import java.io.IOException;
import java.util.List;

public interface MyMenuService {
    List<MyMenuDto>searchMyMenu();
    MyMenuDto insertMyMenu(String url, String text);
    String getFullPath(String filename);
    UploadFile storeFile(MultipartFile multipartFile) throws IOException;
    List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException;
    public boolean insertBook(BookRegisterDto bookRegisterDto) throws IOException;
}
