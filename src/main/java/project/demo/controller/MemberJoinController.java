package project.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import project.demo.service.MemberJoinServiceImple;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberJoinController {

    private final MemberJoinServiceImple m;
    //id 중복체크
    @PostMapping("/idOverlap")
    @ResponseBody
    public boolean idOverlapCheck(@RequestBody Map<String,String> info){
        return m.idOverlap(info);
    }

    @PostMapping("login-join")
    public String loginJoin(){
        return "memberJoin.html";
    }
}
