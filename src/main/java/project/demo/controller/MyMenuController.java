package project.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.demo.dto.MyMenuSenderDto;
import project.demo.service.MyMenuService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/myMenu")
public class MyMenuController {

    private final MyMenuService myMenuService;
    //도서 등록 페이지
    @GetMapping("/bookRegister")
    public String bookRegisterPage(Model model) {
        List<MyMenuSenderDto> myMenuSenderDtos = myMenuService.searchMyMenu();
        model.addAttribute("myMenuList",myMenuSenderDtos);
        for (MyMenuSenderDto myMenuSenderDto : myMenuSenderDtos) {
            log.info("myMenuSenderDtos {}",myMenuSenderDto);
        }
        return "bookRegister";
    }
}
