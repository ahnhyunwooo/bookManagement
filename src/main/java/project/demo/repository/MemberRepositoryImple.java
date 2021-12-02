package project.demo.repository;

import project.demo.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryImple {

    public List<String> findMemberById(String id);
    public List<String> findMemberByNickName(String nickName);
    public boolean insertMember(Member member);

}
