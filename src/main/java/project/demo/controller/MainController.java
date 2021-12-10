package project.demo.controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.demo.dto.IdPwGetDto;
import project.demo.service.MemberLoginServiceImple;


@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {


    @GetMapping
    public String mainPage(){
        return "main.html";
    }

}
