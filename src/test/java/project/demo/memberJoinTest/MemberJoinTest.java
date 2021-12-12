package project.demo.memberJoinTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.demo.domain.Member;
import project.demo.dto.MemberJoinGetDto;
import project.demo.repository.MemberRepository;
import project.demo.service.MemberJoinService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class MemberJoinTest {

    @PersistenceContext
    private EntityManager e;
    
    @Autowired
    MemberJoinService ms;

    @Autowired
    MemberRepository mr;

    @Test
    public void memberSaveTest() {
        MemberJoinGetDto memberJoinGetDto = new MemberJoinGetDto();
        memberJoinGetDto.setGender("M");
        memberJoinGetDto.setEmail("dksgusdnx");
        memberJoinGetDto.setName("안현우");
        memberJoinGetDto.setPhoneNumber("01099498902");
        memberJoinGetDto.setNickName("안현우");
        ms.makeMember(memberJoinGetDto);
        Member member = e.find(Member.class, "2112001");
        System.out.println("@@@member = " + member);
    }
}
