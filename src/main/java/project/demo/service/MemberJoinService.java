package project.demo.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.demo.domain.Member;
import project.demo.repository.MemberRepositoryImple;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberJoinService implements MemberJoinServiceImple{

    private final MemberRepositoryImple m;

    //ID 중복체크 메서드
    @Override
    public boolean idOverlap(Map<String,String> info) {
        String id = info.get("id");
        List<String>  result = m.findMemberById(id);
        if(result.isEmpty()){
            return true;
        }
        return false;
    }

    @Override
    public boolean nickNameOverlap(Map<String, String> info) {
        String nickName = info.get("nickName");
        List<String> result = m.findMemberByNickName(nickName);
        if(result.isEmpty()){
            return true;
        }
        return false;
    }
}
