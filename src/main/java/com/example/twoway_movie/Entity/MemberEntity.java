package com.example.twoway_movie.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Table(name="TWOWAY_MOVIE_MEMBER")
@SequenceGenerator(
        name = "member",
        sequenceName = "member_seq",
        allocationSize = 1,
        initialValue = 1002
)
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "member")
    @Column   private  long usernum;
    @Column    private String userid;
    @Column    private String userpw;
    @Column    private String username;
    @Column    private String usertell;
    @Column  private String  role;

}
