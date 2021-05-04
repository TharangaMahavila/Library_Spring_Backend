package lk.ins.library.entity.custom;

import lk.ins.library.entity.BookReference;
import lk.ins.library.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-05-04
 **/
@Data @AllArgsConstructor @NoArgsConstructor
public class CartCustomEntity implements SuperEntity {

    private String userId;
    private BookCustomEntity bookCustomEntity;
    private Date requestedAt;
    private boolean requestStatus;

}
