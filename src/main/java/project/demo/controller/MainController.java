package project.demo.controller;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.Parameter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.demo.domain.Member;

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
    @GetMapping("login-join")
    public String loginJoin(){
        return "memberJoin.html";
    }



}
