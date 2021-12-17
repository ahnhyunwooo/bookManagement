package project.demo.service;
import project.demo.dto.MyMenuSenderDto;
import java.util.List;

public interface MyMenuService {
    List<MyMenuSenderDto>searchMyMenu();
    MyMenuSenderDto insertMyMenu(String url, String text);
}
