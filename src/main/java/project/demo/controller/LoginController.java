package project.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.demo.dto.IdPwGetDto;
import project.demo.dto.NameEmailGetDto;
import project.demo.dto.NamePhoneGetDto;
import project.demo.service.MemberLoginService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final MemberLoginService ml;

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
            return "main";
        } else if(result == -1) {
            bindingResult.addError(new ObjectError("idPwErr", null, null,"비밀번호가 틀렸습니다."));
        } else {
            bindingResult.addError(new ObjectError("idPwErr", null, null,"아이디가 존재하지 않습니다."));
        }
        log.info("{}", result);
        return "login";
    }
    @GetMapping("login/idSearch")
    public String idSearchPage() {
        return "idSearch";
    }
    @PostMapping("login/idSearch")
    public String idSearchChoiceForm(@RequestParam("id_search")String way) {
        log.info("{}",way);
        if(way.equals("phone")) {
            return "idSearchPhone";
        }else {
            return "idSearchEmail";
        }
    }
    @PostMapping("login/idSearch/phone")
    @ResponseBody
    public boolean idSearchPhone(@Validated @RequestBody NamePhoneGetDto namePhoneGetDto, BindingResult bindingResult) {
        log.info("member = {}", namePhoneGetDto);
        if(ml.idSearchByPhone(namePhoneGetDto)) {
            return true;
        }else {
            return false;
        }
    }
    @PostMapping("login/idSearch/email")
    @ResponseBody
    public boolean idSearchEmail(@Validated @RequestBody NameEmailGetDto nameEmailGetDto, BindingResult bindingResult) {
        log.info("member = {}", nameEmailGetDto);
        if(ml.idSearchByEmail(nameEmailGetDto)) {
            return true;
        }else {
            return false;
        }
    }
    @GetMapping("login/pwSearch")
    public String pwSearchPage() {
        return "pwSearch";
    }
    @PostMapping("login/pwSearch")
    public String pwSearchChoiceForm(@RequestParam("pw_search")String way) {
        log.info("@@@@{}", way);
        if(way.equals("phone")) {
            return "pwSearchPhone";
        }else {
            return "pwSearchEmail";
        }

    }


}
