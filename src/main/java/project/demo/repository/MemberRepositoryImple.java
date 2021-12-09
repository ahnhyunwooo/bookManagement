package project.demo.repository;

import project.demo.domain.Member;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MemberRepositoryImple {

    public String findMemberById(String id);
    public String  findMemberByNickName(String nickName);
    public boolean insertMember(Member member);
    public String findMaxIndex();
    public List<Object[]> findSaltAndPwById(String id);
    public String findPwBySalt(String salt);

}
