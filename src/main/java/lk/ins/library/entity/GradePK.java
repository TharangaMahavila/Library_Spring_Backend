package lk.ins.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-13
 **/
@Embeddable
@Data @AllArgsConstructor @NoArgsConstructor
public class GradePK implements Serializable {
    private Integer grade;
    private Section section;
    private Integer year;
}
