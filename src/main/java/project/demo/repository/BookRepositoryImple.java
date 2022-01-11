package project.demo.repository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.demo.domain.Book;
import project.demo.domain.Member;

import javax.persistence.EntityManager;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookRepositoryImple implements BookRepository {

    private final EntityManager e;

    @Override
    @Transactional
    public boolean insertBook(Book book) {
        try {
            log.info("book = {}", book);
            e.persist(book);

            return true;
        }catch(Exception e){
            System.out.println("aaaa = " );
            return false;
        }
    }

    @Override
    public Book findBookByName(String name) {
        Book result = null;
        try {
            result = e.createQuery("select b from Book b where b.name = :name", Book.class)
                    .setParameter("name", name)
                    .getSingleResult();
        }catch (Exception e) {
            return null;
        }
        return result;
    }
}
