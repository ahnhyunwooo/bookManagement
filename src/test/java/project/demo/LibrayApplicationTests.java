package project.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.demo.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@SpringBootTest
@Transactional
class LibrayApplicationTests {

    @PersistenceContext
    private  EntityManager e;
    @Test
    public void func(){

        Member m = new Member();
        m.setIndex("123");
        e.persist(m);
        Optional<Member> member = Optional.ofNullable(e.find(Member.class, "123"));
        System.out.println("@@@@@@@@@"+member.get().getIndex());
    }

}
