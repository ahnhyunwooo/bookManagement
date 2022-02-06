package project.demo.service.controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {


    @GetMapping("main")
    public String mainPage(){
        return "main";
    }

}
