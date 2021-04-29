package lk.ins.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-15
 **/
@Embeddable
@Data @AllArgsConstructor @NoArgsConstructor
public class Name {

    private String initial;
    @Column(name = "f_name")
    private String fname;
    @Column(name = "l_name")
    private String lname;
}
