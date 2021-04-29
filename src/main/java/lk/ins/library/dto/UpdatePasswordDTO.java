package lk.ins.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-18
 **/
@Data @AllArgsConstructor @NoArgsConstructor
public class UpdatePasswordDTO implements Serializable {

    @NotNull(message = "Username is required")
    private String username;
    @NotNull(message = "Current password is required")
    private String currentPassword;
    @NotNull(message = "New password is required")
    private String newPassword;
}
