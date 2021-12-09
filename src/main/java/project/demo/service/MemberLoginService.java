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
    public boolean idSamePw(IdPwGetDto idPwGetDto) {
        String id = idPwGetDto.getId();
        String salt = null;
        String loginPw = null;
        String pw = idPwGetDto.getPw();
        List<Object[]> saltAndPw = mr.findSaltAndPwById(id);
        if(saltAndPw == null) {
            return false;
        }
        for(Object[] info : saltAndPw) {
            salt = (String)info[0];
            loginPw = (String)info[1];
        }
        String dbPw = mj.SHA512(pw, salt);
        if(dbPw.equals(loginPw)) {
            return true;
        }
        return false;
    }
}
