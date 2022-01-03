package project.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.demo.domain.Book;
import project.demo.domain.UploadFile;
import project.demo.dto.BookRegisterDto;
import project.demo.dto.MyMenuDto;
import project.demo.repository.BookRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyMenuServiceImple implements MyMenuService{

    @Value("${file.dir}")
    private String fileDir;

    private final BookRepository bookRepository;

    //왼쪽 nav바 목록추가
    @Override
    public List<MyMenuDto> searchMyMenu() {
        List<MyMenuDto> result = new ArrayList<>();
        result.add(insertMyMenu("#","회원정보"));
        result.add(insertMyMenu("#","공지사항"));
        result.add(insertMyMenu("#","게시판"));
        result.add(insertMyMenu("#","도서등록"));
        return result;
    }
    @Override
    public boolean insertBook(BookRegisterDto bookRegisterDto) throws IOException {
        List<UploadFile> uploadFiles = storeFiles(bookRegisterDto.getFile());
        Book book = new Book();
        book.setAuthor(bookRegisterDto.getBookAuthor());
        book.setGenre(bookRegisterDto.getBookGenre());
        book.setName(bookRegisterDto.getBookName());
        book.setPublisher(bookRegisterDto.getBookPublisher());
        book.setYn("y");
        book.setRegisterDate(LocalDateTime.now());
        book.setRentalCount(0);
        book.setCount(bookRegisterDto.getBookCount());
        book.setIndex("123");
        log.info("book1 ={}",book);
        return bookRepository.insertBook(book);
    }

    @Override
    public MyMenuDto insertMyMenu(String url, String text) {
        MyMenuDto myMenuDto = new MyMenuDto(text, url);
        return myMenuDto;
    }
    //파일 저장주소
    @Override
    public String getFullPath(String filename) {
        return fileDir + filename;
    }
    //여러개 파일저장
    @Override
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles)
            throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }
    //한개 파일 저장
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException
    {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new UploadFile(originalFilename, storeFileName);
    }
    //확장자포함 파일 이름만들기
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }
    //확장자
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
