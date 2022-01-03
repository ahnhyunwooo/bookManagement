package project.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@ToString
@Table(name="BOOK")
public class Book {

    @Column(name="BOOK_INDEX")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String index;

    @Column(name="BOOK_NAME")
    private String name;

    @Column(name="BOOK_PUBLISHER")
    private String publisher;

    @Column(name="BOOK_AUTHOR")
    private String author;

    @Column(name="BOOK_GENRE")
    private String genre;

    @Column(name="BOOK_REGISTER_DATE")
    private LocalDateTime registerDate;

    @Column(name="BOOK_COUNT")
    private String count;

    @Column(name="BOOK_YN")
    private String yn;


    @Column(name="BOOK_RENTAL_COUNT")
    private int rentalCount;

    @OneToMany(mappedBy = "book")
    private List<BookReservation> bookReservations = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<BookManagement> bookManagements = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ADMIN_INDEX")
    private Admin admin;

    //연관관계 메서드
    public void setAdmin(Admin admin) {
        if(this.admin != null){
            this.admin.getBooks().remove(this);
        }
        this.admin = admin;
        admin.getBooks().add(this);
    }


}
