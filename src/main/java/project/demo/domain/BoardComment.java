package project.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="BOARD_COMMENT")
@Getter @Setter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MEMBER_INDEX")
    private Member member;

    //연관관계 메서드
    public void setMember(Member member) {
        if(this.member != null){
            this.member.getBoardComments().remove(this);
        }
        this.member = member;
        member.getBoardComments().add(this);
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="BOARD_INDEX")
    private Board board;
    //연관관계 메서드
    public void setBoard(Board board) {
        if(this.board != null){
            this.board.getBoardComments().remove(this);
        }
        this.board = board;
        board.getBoardComments().add(this);
    }

}
