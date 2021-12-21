package project.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.demo.annotation.Login;
import project.demo.domain.Member;
import project.demo.dto.IdPwDto;
import project.demo.dto.NameEmailDto;
import project.demo.dto.NamePhoneDto;
import project.demo.service.MemberLoginService;
import project.demo.session.SessionConst;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final MemberLoginService ml;

    /**
     * 로그인
     */
    @GetMapping("/login")
    public String loginPage(@ModelAttribute("member") IdPwDto idPwDto, @Login Member loginMember, Model model){
        if(loginMember!=null) {
            log.info("loginMember2 = {}",loginMember);
            model.addAttribute("loginMember",loginMember);
            return "redirect:/main";
        }
       return "login";
    }

    @PostMapping("/login")
    public String loginInfo(@Validated @ModelAttribute("member") IdPwDto idPwDto, BindingResult bindingResult, @RequestParam(defaultValue = "/main")String redirectURL, HttpServletRequest request){
        log.info("idPwGetDto ={}", idPwDto);
        if(bindingResult.hasErrors()) {
            return "login";
        }
        //아이디 존재여부
        int result = ml.idSamePw(idPwDto);
        if(result == 1) {
            //세션생성
            HttpSession session = request.getSession();
            //회원정보 조회
            Optional<Member> loginMember = ml.findLoginMember(idPwDto.getId());
            log.info("loginMember1 = {}", loginMember);
            //세션에 로그인 회원 정보 보관
            session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember.get());
            log.info("1redirectURL:{}",redirectURL);
            return "redirect:"+redirectURL;
        } else if(result == -1) {
            bindingResult.addError(new ObjectError("idPwErr", null, null,"비밀번호가 틀렸습니다."));
        } else {
            bindingResult.addError(new ObjectError("idPwErr", null, null,"아이디가 존재하지 않습니다."));
        }
        log.info("{}", result);
        return "login";
    }

    /**
     * 아이디 찾기
     */
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
    public boolean idSearchPhone(@Validated @RequestBody NamePhoneDto namePhoneDto, BindingResult bindingResult) {
        log.info("member = {}", namePhoneDto);
        if(ml.idSearchByPhone(namePhoneDto)) {
            return true;
        }else {
            return false;
        }
    }
    @PostMapping("login/idSearch/email")
    @ResponseBody
    public boolean idSearchEmail(@Validated @RequestBody NameEmailDto nameEmailDto, BindingResult bindingResult) {
        log.info("member = {}", nameEmailDto);
        if(ml.idSearchByEmail(nameEmailDto)) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 비밀번호 찾기
     */
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

    @PostMapping("login/pwSearch/phone")
    @ResponseBody
    public boolean pwSearchPhone(@Validated @RequestBody NamePhoneDto namePhoneDto, BindingResult bindingResult) {
        log.info("member = {}", namePhoneDto);
        if(ml.idSearchByPhone(namePhoneDto)) {
            return true;
        }else {
            return false;
        }
    }
    /*
    @PostMapping("login/idSearch/email")
    @ResponseBody
    public boolean pwSearchEmail(@Validated @RequestBody NameEmailGetDto nameEmailGetDto, BindingResult bindingResult) {
        log.info("member = {}", nameEmailGetDto);
        if(ml.idSearchByEmail(nameEmailGetDto)) {
            return true;
        }else {
            return false;
        }
    }*/

}
