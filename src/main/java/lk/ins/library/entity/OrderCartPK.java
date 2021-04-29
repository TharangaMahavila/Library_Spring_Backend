package lk.ins.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-04-02
 **/
@Embeddable
@Data @AllArgsConstructor @NoArgsConstructor
public class OrderCartPK implements Serializable {
    @ManyToOne
    @JoinColumn(name = "student_id",referencedColumnName = "reg_no")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "ref_no",referencedColumnName = "ref_no")
    private BookReference bookReference;
}
