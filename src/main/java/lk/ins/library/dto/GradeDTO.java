package lk.ins.library.dto;

import lk.ins.library.entity.Section;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-15
 **/
@Data @AllArgsConstructor @NoArgsConstructor
public class GradeDTO {

    @NotNull(message = "Grade id is required")
    private int id;
    @NotNull(message = "grade is required")
    private Integer grade;
    @NotNull(message = "section is required")
    private Section section;
    @NotNull(message = "year is required")
    private Integer year;
}
