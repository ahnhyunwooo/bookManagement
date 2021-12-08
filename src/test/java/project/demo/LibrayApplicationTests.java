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

    }

}
