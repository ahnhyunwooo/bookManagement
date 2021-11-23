package project.demo.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="BOOK_MANAGEMENT")
public class BookManagement {

    @Column(name="RENTAL_INDEX")
    @Id
    private String index;

    @Column(name="RENTAL_DATE")
    private LocalDateTime date;

    @Column(name="DEADLINE_DATE")
    private LocalDateTime deadlineDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_INDEX")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="BOOK_INDEX")
    private Book book;

}
