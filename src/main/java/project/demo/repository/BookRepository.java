package project.demo.repository;

import project.demo.domain.Book;

public interface BookRepository {

    boolean insertBook(Book book);
    Book findBookByName(String name);
}
