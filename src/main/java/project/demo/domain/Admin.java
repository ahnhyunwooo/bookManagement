package project.demo.domain;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Admin {


    @Column(name="ADMIN_INDEX")
    @Id
    private String index;

    @Column(name="ADMIN_NAME")
    private String name;

    @Column(name="ADMIN_ID")
    private String id;

    @Column(name="ADMIN_pw")
    private String pw;

    @Column(name="ADMIN_NICKNAME")
    private String nickName;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();


}
