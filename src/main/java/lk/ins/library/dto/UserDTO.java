package lk.ins.library.dto;

import lk.ins.library.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-19
 **/
@Data @AllArgsConstructor @NoArgsConstructor
public class UserDTO implements Serializable {
    @NotNull(message = "Username is required")
    private String username;
    @NotNull(message = "Password is required")
    private String password;
    @NotNull(message = "Active status is required")
    private boolean isActive;
    @NotNull(message = "User role is required")
    private Role role;
}
