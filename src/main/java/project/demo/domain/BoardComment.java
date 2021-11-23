package project.demo.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="BOARD_COMMENT")
@Getter
public class BoardComment {

    @Column(name="COMMENT_INDEX")
    @Id
    private String index;

    @Column(name="COMMENT_CONTENT")
    private String content;

    @Column(name="COMMENT_INSDATE")
    private LocalDateTime insDate;

    @Column(name="COMMENT_UDP_DATE")
    private LocalDateTime udpDate;

    @ManyToOne
    @JoinColumn(name="MEMBER_INDEX")
    private Member member;

    @ManyToOne
    @JoinColumn(name="BOARD_INDEX")
    private Board board;

}
