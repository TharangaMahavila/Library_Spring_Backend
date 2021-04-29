package lk.ins.library.dto.custom;

import lk.ins.library.dto.CategoryDTO;
import lk.ins.library.dto.RackDTO;
import lk.ins.library.entity.Author;
import lk.ins.library.entity.Medium;
import lk.ins.library.entity.Rack;
import lk.ins.library.entity.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-27
 **/
@Data @AllArgsConstructor @NoArgsConstructor
public class BookCustomDTO implements Serializable {
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
    private RackDTO rackNo;
    private List<CategoryDTO> categories;
    private boolean isAvailable;
}
