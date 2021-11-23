package project.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="CATEGORY")
@Getter @Setter
public class Category {

    @Column(name="CATEGORY_INDEX")
    @Id
    private  String index;

    @Column(name="CATEGORY_NAME")
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();


}
