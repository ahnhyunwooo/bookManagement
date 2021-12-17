package project.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.demo.dto.MyMenuSenderDto;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyMenuServiceImple implements MyMenuService{

    @Override
    public List<MyMenuSenderDto> searchMyMenu() {
        List<MyMenuSenderDto> result = new ArrayList<>();
        result.add(insertMyMenu("#","회원정보"));
        result.add(insertMyMenu("#","공지사항"));
        result.add(insertMyMenu("#","게시판"));
        result.add(insertMyMenu("#","도서등록"));
        return result;
    }

    @Override
    public MyMenuSenderDto insertMyMenu(String url, String text) {
        MyMenuSenderDto myMenuSenderDto = new MyMenuSenderDto(text, url);
        return myMenuSenderDto;
    }
}
