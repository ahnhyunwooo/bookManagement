package project.demo.memberLoginTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.demo.repository.MemberRepository;
import project.demo.service.MemberJoinService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
public class MemberLoginTest {
    @PersistenceContext
    private EntityManager e;

    @Autowired
    MemberJoinService ms;

    @Autowired
    MemberRepository mr;

    @Test
    public void findSaltAndPwByIdTest() {
        List result = mr.findSaltAndPwById("dksgusdnw");
        //log.info("result = {}", result.get(0));

    }
}
