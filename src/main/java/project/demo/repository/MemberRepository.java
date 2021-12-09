package project.demo.repository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.demo.domain.Member;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberRepository implements MemberRepositoryImple{


    private final EntityManager e;

    //id로 회원정보 찾기
    @Override
    //@Transactional
    public String findMemberById(String id) {
        String result="";
        try {
            result = e.createQuery("select m.id from Member m where m.id = :id", String.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    @Override
    public String findMemberByNickName(String nickName) {
        String result="";
        try {
            result = e.createQuery("select m.id from Member m where m.nickName = :nickName",String.class)
                    .setParameter("nickName", nickName)
                    .getSingleResult();
        }catch (Exception e) {
            return result;
        }
        return result;
    }

    @Override
    @Transactional
    public boolean insertMember(Member member) {
        try {
            e.persist(member);
            log.info("member = {}",member);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public String findMaxIndex() {
        String result = e.createQuery("select  max(m.index) from Member m", String.class)
                .getSingleResult();
        return result;
    }
    //아이디 비밀번호 확인
    @Override
    public List<Object[]> findSaltAndPwById(String id) {
        try{
            List<Object[]> result = e.createQuery("select m.salt, m.pw from Member m where m.id = :id")
                    .setParameter("id", id)
                    .getResultList();
            return result;
        }catch (Exception e) {
            return null;
        }
    }

    //salt로 pw 찾기
    @Override
    public String findPwBySalt(String salt) {
        try{
            String result = e.createQuery("select m.pw from Member m where m.salt = :salt", String.class)
                    .setParameter("salt", salt)
                    .getSingleResult();
            return result;
        }catch (Exception e) {
            return null;
        }

    }
}
