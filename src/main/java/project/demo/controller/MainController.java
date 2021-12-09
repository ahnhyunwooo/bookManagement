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

    private final MemberLoginServiceImple ml;

    @GetMapping
    public String mainPage(){
        return "main.html";
    }
    @GetMapping("login")
    public String loginPage(@ModelAttribute("member") IdPwGetDto idPwGetDto){
        return "login.html";
    }
    @PostMapping("login")
    public String loginInfo(@Validated @ModelAttribute("member") IdPwGetDto idPwGetDto, BindingResult bindingResult, Model model){
        log.info("idPwGetDto ={}",idPwGetDto);
        if(bindingResult.hasErrors()) {
            return "login";
        }
        //아이디 존재여부
        int result = ml.idSamePw(idPwGetDto);
        if(result == 1) {
            return "main.html";
        } else if(result == -1) {
            bindingResult.addError(new ObjectError("idPwErr", null, null,"비밀번호가 틀렸습니다."));
        } else {
            bindingResult.addError(new ObjectError("idPwErr", null, null,"아이디가 존재하지 않습니다."));
        }
        log.info("{}", result);
        return "login";
    }


}
