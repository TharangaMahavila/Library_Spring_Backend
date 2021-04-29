package lk.ins.library.entity.custom;

import lk.ins.library.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-25
 **/
@Data @AllArgsConstructor @NoArgsConstructor
public class BookCustomEntity implements SuperEntity {
    private String refNo;
    private String bookId;
    private String barcode;
    private String englishName;
    private String sinhalaName;
    private Integer year;
    private BigDecimal price;
    private Medium medium;
    private Integer pages;
    private String note;
    private String image;
    private Author author;
    private boolean isReference;
    private Supplier supplier;
    private Rack rackNo;
}
