package lk.ins.library.dto;

import lk.ins.library.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-11
 **/
@Data @AllArgsConstructor @NoArgsConstructor
public class CategoryDTO implements Serializable {
    @NotNull(message = "Category is is required")
    private Integer id;
    @NotNull(message = "Category name is required")
    private String name;
}
