package lk.ins.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-12
 **/
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "grade")
public class Grade implements SuperEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Embedded
    @Column(unique = true)
    private GradePK gradePK;

/*    @JsonIgnore
    @ManyToMany(mappedBy = "grades")
    private Set<Student> students = new HashSet<>();*/

/*    public Grade(int id,GradePK gradePK) {
        this.id = id;
        this.gradePK = gradePK;
    }*/
    public Grade(int id,Integer grade,Section section,Integer year) {
        this.id = id;
        this.gradePK = new GradePK(grade,section,year);
    }
}
