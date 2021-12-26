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
import project.demo.dto.*;
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

    /**
     * 아이디 찾기 - 연락처로 찾기
     */
    @PostMapping("login/idSearch/phone")
    @ResponseBody
    public boolean idSearchPhone(@Validated @RequestBody NamePhoneDto namePhoneDto, BindingResult bindingResult) {
        log.info("member = {}", namePhoneDto);
        if(ml.idSearchByPhone(namePhoneDto)) {
            log.info("정보 일치!!");
            return true;
        }else {
            log.info("정보 불일치!!");
            return false;
        }
    }

    /**
     * 아이디 찾기 - Email로 찾기
     */
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

        if(way.equals("phone")) {
            return "pwSearchPhone";
        }else {
            return "pwSearchEmail";
        }
    }

    /**
     * 비밀번호 찾기 - 연락처로 찾기
     */
    @PostMapping("login/pwSearch/phone")
    @ResponseBody
    public boolean pwSearchPhone(@Validated @RequestBody IdPhoneDto idPhoneDto, BindingResult bindingResult) {
        log.info("member = {}", idPhoneDto);
        if(ml.pwSearchByPhone(idPhoneDto)) {
            log.info("정보 일치!!");
            return true;
        }else {
            log.info("정보 불일치!!");
            return false;
        }
    }

    /**
     * 비밀번호찾기 - 핸드폰 인증
     */
    @PostMapping("login/pwSearch/phone/phoneMessage")
    @ResponseBody
    public int phoneMessage(@RequestBody PhoneNumberDto phoneNumberDto) {
        log.info("우와 여기탄다");
        int number = ml.phoneMessage(phoneNumberDto);
        log.info("인증번호 : " + number);
        return number;
    }

    /**
     * 비밀번호 찾기 - Email로 찾기
     */
    @PostMapping("login/pwSearch/email")
    @ResponseBody
    public boolean pwSearchEmail(@Validated @RequestBody IdEmailDto idEmailDto, BindingResult bindingResult) {
        log.info("member = {}", idEmailDto);
        if(ml.pwSearchByEmail(idEmailDto)) {
            return true;
        }else {
            return false;
        }
    }

    /**
     *비밀번호 찾기 - Email 인증
     */
    @PostMapping("login/pwSearch/email/sendEmailCertification")
    @ResponseBody
    public String emailNumber(@RequestBody EmailAddressDto emailAddressDto) {
        return ml.sendMailCertification(emailAddressDto);
    }


    /**
     *비밀번호 찾기 - 새 비밀번호 등록하기
     */
    @GetMapping("login/pwSearch/newPw")
    public String newPwPage() {
        return "newPw";
    }

    /////////////개발중/////////////////
    @PostMapping("/pwChange")
    public String pwChange(@Validated @ModelAttribute IdPwDto idPwDto, BindingResult bindingResult){
        log.info("idPwDt{}",idPwDto);
        if(bindingResult.hasErrors()) {
            return "";
        }
        //boolean result = ml.updateMember(idPwDto);

        return "redirect:/login";
    }

}
