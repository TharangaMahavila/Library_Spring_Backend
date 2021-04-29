package lk.ins.library.dto;

import lk.ins.library.entity.Author;
import lk.ins.library.entity.Category;
import lk.ins.library.entity.Medium;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-24
 **/
@Data @AllArgsConstructor @NoArgsConstructor
public class BookDTO implements Serializable {

    @NotNull(message = "Book id is requied")
    private String bookId;
    @NotNull(message = "Book english name is required")
    private String englishName;
    @NotNull(message = "Book sinhala name is required")
    private String sinhalaName;
    private Integer year;
    private BigDecimal price;
    @NotNull(message = "Book language medium is required")
    private Medium medium;
    private Integer pages;
    private String image;
    private String note;
    @NotNull(message = "Book author is required")
    private Integer author;
    @NotNull(message = "Book category is required")
    private Set<CategoryDTO> categories;
}
