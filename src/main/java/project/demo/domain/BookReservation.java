package project.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="BOOK_RESERVATION")
@Getter @Setter
public class BookReservation {

    @Column(name="RESERVATION_INDEX")
    @Id
    private String index;

    @Column(name="RESERVATION_DATE")
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_INDEX")
    private Member member;

    //연관관계 메서드
    public void setMember(Member member) {
        if(this.member != null){
            this.member.getBookReservations().remove(this);
        }
        this.member = member;
        member.getBookReservations().add(this);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="BOOK_INDEX")
    private Book book;

    //연관관계 메서드
    public void setBook(Book book) {
        if(this.book != null){
            this.book.getBookReservations().remove(this);
        }
        this.book = book;
        book.getBookReservations().add(this);
    }


}
