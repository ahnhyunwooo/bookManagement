package project.demo.memberJoinTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.demo.controller.MemberJoinController;
import project.demo.domain.Member;
import project.demo.dto.MemberJoinGetDto;
import project.demo.repository.MemberRepositoryImple;
import project.demo.service.MemberJoinServiceImple;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class MemberJoinTest {

    @PersistenceContext
    private EntityManager e;
    
    @Autowired
    MemberJoinServiceImple ms;

    @Autowired
    MemberRepositoryImple mr;

    @Test
    public void memberSaveTest() {
        MemberJoinGetDto memberJoinGetDto = new MemberJoinGetDto("배지영","배지영","qowldud12", "배찌","W", "00000","123");
        ms.makeMember(memberJoinGetDto);
        Member member = e.find(Member.class, "2112001");
        System.out.println("member = " + member);
    }
}
