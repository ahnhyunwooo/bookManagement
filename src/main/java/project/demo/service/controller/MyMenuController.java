package project.demo.service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.demo.dto.BookRegisterDto;
import project.demo.dto.MyMenuDto;
import project.demo.service.MyMenuService;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/myMenu")
public class MyMenuController {

    private final MyMenuService myMenuService;
    private List<MultipartFile> multipartFiles;
    //도서 등록 페이지
    @GetMapping("/bookRegister")
    public String bookRegisterPage(Model model, @ModelAttribute("book") BookRegisterDto bookRegisterDto) {
        List<MyMenuDto> myMenuDtos = myMenuService.searchMyMenu();
        model.addAttribute("myMenuList", myMenuDtos);
        multipartFiles = new ArrayList<>();
        for (MyMenuDto myMenuDto : myMenuDtos) {
            log.info("myMenuSenderDtos {}", myMenuDto);
        }
        return "bookRegister";
    }
    //도서 등록 데이터 받기
    @PostMapping("/bookRegister")
    public String bookRegisterForm(@Validated @ModelAttribute("book") BookRegisterDto bookRegisterDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, HttpServletRequest httpServletRequest) throws IOException {
        List<MyMenuDto> myMenuDtos = myMenuService.searchMyMenu();
        model.addAttribute("myMenuList", myMenuDtos);
        String[] fileNames = httpServletRequest.getParameterValues("fileName");
        multipartFiles = bookRegisterDto.fileUpdate(multipartFiles, fileNames);
        for(int i =0 ; i<bookRegisterDto.getFile().size();i++) {
            log.info("real = {}",multipartFiles.get(i).getOriginalFilename());
        }
        if(bookRegisterDto.getFile().size() == 0) {
            bindingResult.rejectValue("file", null, "파일을 첨부해주세요.");
        }
        if(bindingResult.hasErrors()) {
            log.info("@@@fileIndexTemp :: " + bookRegisterDto.getFileIndexTemp());
            model.addAttribute("fileIndexTemp", bookRegisterDto.getFileIndexTemp());
            model.addAttribute("fileList",bookRegisterDto.getFile());
            return "bookRegister";
        }
        if(myMenuService.insertBook(bookRegisterDto)) {
            return "main";
        }
        return "bookRegister";

    }

}
