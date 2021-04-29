package lk.ins.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-02-26
 **/
@Embeddable
@Data @AllArgsConstructor @NoArgsConstructor
public class RackPK implements Serializable {

    @Column(name = "rack_no")
    private String rackNo;
    @Column(name = "shell_no")
    private String shellNo;
}
