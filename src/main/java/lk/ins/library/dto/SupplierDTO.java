package lk.ins.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-24
 **/
@Data @AllArgsConstructor @NoArgsConstructor
public class SupplierDTO implements Serializable {
    private int id;
    @NotNull(message = "Supplier name is required")
    @NotEmpty(message = "Supplier name should not be empty")
    private String name;
}
