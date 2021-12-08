package project.demo.controller;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Log4j2
@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String mainPage(){
        return "main.html";
    }
    @GetMapping("login")
    public String loginPage(){
        return "login.html";
    }



}
