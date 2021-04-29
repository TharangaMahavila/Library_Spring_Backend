package lk.ins.library.dto;

import lk.ins.library.entity.BookReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-25
 **/
@Data @AllArgsConstructor @NoArgsConstructor
public class BookDisposalDTO implements Serializable {

    @NotNull(message = "Book reference is required")
    private String refNo;
    @NotNull(message = "Date is required")
    private Date date;
    private String note;
}
