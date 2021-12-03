package project.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.demo.domain.Member;
import project.demo.dto.EmailAddressGetDto;
import project.demo.dto.IdGetDto;
import project.demo.dto.NickNameGetDto;
import project.demo.dto.PhoneNumberGetDto;
import project.demo.service.MemberJoinServiceImple;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberJoinController {

    private final MemberJoinServiceImple m;

    //id 중복체크
    @PostMapping("/idOverlap")
    @ResponseBody
    public boolean idOverlapCheck(@RequestBody IdGetDto idGetDto){
        return m.idOverlap(idGetDto);
    }

    @PostMapping("login-join")
    public String loginJoin(@ModelAttribute Member member, Model model){
        boolean result = m.makeMember(member);
        //member index
        //joindate set
    ////고쳐주세요!!!!!!!!!!!!!!!!!!!
        log.info("@@@member :: "+member);
        model.addAttribute("result",result);
        return "login-join";
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
