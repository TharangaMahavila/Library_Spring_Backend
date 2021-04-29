package lk.ins.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-02-26
 **/
@Data @AllArgsConstructor @NoArgsConstructor
public class RackDTO implements Serializable {

    @NotNull(message = "Rack id is required")
    private Integer id;
    @NotNull(message = "Rack number is required")
    private String rackNo;
    @NotNull(message = "Shell number is required")
    private String shellNo;
}
