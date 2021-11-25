package project.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
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
