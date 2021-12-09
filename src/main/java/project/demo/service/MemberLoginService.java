package project.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.demo.dto.IdPwGetDto;
import project.demo.repository.MemberRepositoryImple;

import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberLoginService implements MemberLoginServiceImple{


    private final MemberRepositoryImple mr;
    private final MemberJoinServiceImple mj;

    @Override
    public int idSamePw(IdPwGetDto idPwGetDto) {
        String id = idPwGetDto.getId();
        String salt = null;
        String loginPw = null;
        String pw = idPwGetDto.getPw();
        List<Object[]> saltAndPw = mr.findSaltAndPwById(id);
        //아이디 없음
        if(saltAndPw.isEmpty()) {
            return 0;
        }
        for(Object[] info : saltAndPw) {
            salt = (String)info[0];
            loginPw = (String)info[1];
        }
        String dbPw = mj.SHA512(pw, salt);
        //아이디 비번 일치
        if(dbPw.equals(loginPw)) {
            return 1;
        }
        //아이디 일치 비번 불일치
        return -1;
    }
}
