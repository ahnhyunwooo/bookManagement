package project.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.demo.domain.Member;
import project.demo.dto.*;
import project.demo.service.MemberJoinServiceImple;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberJoinController {

    private final MemberJoinServiceImple m;

    //초기화면
    @GetMapping("login-join")
    public String loginJoin( @ModelAttribute MemberJoinGetDto memberJoinGetDto){
        return "memberJoin";
    }


    //id 중복체크
    @PostMapping("/idOverlap")
    @ResponseBody
    public boolean idOverlapCheck(@RequestBody IdGetDto idGetDto){
        return m.idOverlap(idGetDto);
    }

    @PostMapping("login-join")
    public String loginJoin(@Validated @ModelAttribute MemberJoinGetDto memberJoinGetDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        log.info("memberJoinGetDto ={}",memberJoinGetDto);
        if(bindingResult.hasErrors()) {
            return "memberJoin";
        }
        boolean result = m.makeMember(memberJoinGetDto);

        return "redirect:/login";
    }

    //닉네임 중복체크
    @PostMapping("/nickNameOverlap")
    @ResponseBody
    public boolean nickNameOverlapCheck(@RequestBody NickNameGetDto nickNameGetDto){
        return m.nickNameOverlap(nickNameGetDto);
    }
    //핸드폰 인증번호
    @PostMapping("/phoneMessage")
    @ResponseBody
    public int phoneMessage(@RequestBody PhoneNumberGetDto phoneNumberGetDto) {
        int number = m.phoneMessage(phoneNumberGetDto);
        return number;
    }

    //email 인증
    @PostMapping("/email")
    @ResponseBody
    public String emailNumber(@RequestBody EmailAddressGetDto emailAddressDto) {
        return m.sendMail(emailAddressDto);
    }
}
