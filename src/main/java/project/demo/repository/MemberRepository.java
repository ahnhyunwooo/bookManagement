package project.demo.repository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.demo.domain.Member;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Transient;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberRepository implements MemberRepositoryImple{


    private final EntityManager e;

    //id로 회원정보 찾기
    @Override
    //@Transactional
    public List<String> findMemberById(String id) {

        List<String> resultList = e.createQuery("select m.id from Member m where m.id = :id",String.class)
                .setParameter("id",id)
                .getResultList();
        return resultList;
    }
}
