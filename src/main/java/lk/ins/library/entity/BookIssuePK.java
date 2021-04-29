package lk.ins.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-25
 **/
@Embeddable
@Data @AllArgsConstructor @NoArgsConstructor
public class BookIssuePK implements Serializable {
    @ManyToOne
    @JoinColumn(name = "student_id",referencedColumnName = "reg_no")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "ref_no",referencedColumnName = "ref_no")
    private BookReference bookReference;
    @Column(name = "issue_date")
    private Date issueDate;
}
