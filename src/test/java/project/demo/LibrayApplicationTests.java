package project.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.demo.controller.MemberJoinController;

import project.demo.repository.MemberRepositoryImple;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


@SpringBootTest
@Transactional
class LibrayApplicationTests {

    @PersistenceContext
    private  EntityManager e;

    @Autowired
    MemberJoinController mj;

    @Autowired
    MemberRepositoryImple mr;

    @Test
    public void func(){

        /*Member m = new Member();
        m.setIndex("123");
        e.persist(m);
        Optional<Member> member = Optional.ofNullable(e.find(Member.class, "123"));
        System.out.println("@@@@@@@@@"+member.get().getIndex());*/
        String maxIndex = mr.findMaxIndex();
        System.out.println("@@@@"+maxIndex);
//        Optional<String> memberByNickName = mr.findMemberByNickName("123");
//        System.out.println("@@@"+memberByNickName);
        Object memberById = mr.findMemberByNickName("123");
        System.out.println("memberById = " + memberById);
    }

}
