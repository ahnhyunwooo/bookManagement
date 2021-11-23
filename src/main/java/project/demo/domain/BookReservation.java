package project.demo.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="BOOK_RESERVATION")
@Getter
public class BookReservation {

    @Column(name="RESERVATION_INDEX")
    @Id
    private String index;

    @Column(name="RESERVATION_DATE")
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_INDEX")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="BOOK_INDEX")
    private Book book;
}
