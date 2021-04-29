package lk.ins.library.dto;

import lk.ins.library.entity.Book;
import lk.ins.library.entity.Rack;
import lk.ins.library.entity.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-25
 **/
@Data @AllArgsConstructor @NoArgsConstructor
public class BookReferenceDTO implements Serializable {

    @NotNull(message = "Book reference id is required")
    private String refNo;
    private String barcode;
    @NotNull(message = "Book reference states is required")
    private boolean isReference;
    @NotNull(message = "Book is required")
    private String bookId;
    @NotNull(message = "Supplier source is required")
    private Integer supplierId;
    @NotNull(message = "Rack is required")
    private Integer rackId;
}
