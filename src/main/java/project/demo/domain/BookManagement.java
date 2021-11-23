package project.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
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

    //연관관계 메서드
    public void setMember(Member member) {
        if(this.member != null){
            this.member.getBookManagements().remove(this);
        }
        this.member = member;
        member.getBookManagements().add(this);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="BOOK_INDEX")
    private Book book;

    //연관관계 메서드
    public void setBook(Book book) {
        if(this.book != null){
            this.book.getBookManagements().remove(this);
        }
        this.book = book;
        book.getBookManagements().add(this);
    }


}
