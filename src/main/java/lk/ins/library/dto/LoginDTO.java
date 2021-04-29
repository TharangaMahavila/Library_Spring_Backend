package lk.ins.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-07
 **/
@Data @AllArgsConstructor @NoArgsConstructor
public class LoginDTO implements Serializable {

    private String username;
    private String password;
}
