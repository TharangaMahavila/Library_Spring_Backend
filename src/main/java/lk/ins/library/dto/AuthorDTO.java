package lk.ins.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-02-24
 **/
@Data @AllArgsConstructor @NoArgsConstructor
public class AuthorDTO implements Serializable {

    private int id;
    @NotNull(message = "Author name is reqired")
    private String name;
}
