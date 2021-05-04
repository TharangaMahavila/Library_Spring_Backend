package lk.ins.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-04-02
 **/
@Data @AllArgsConstructor @NoArgsConstructor
public class OrderCartDTO implements Serializable {

    @NotNull(message = "User id is required")
    private String userId;
    @NotNull(message = "Book reference is required")
    private String refId;
    private Date requestedAt;
    private boolean requestStatus;
}
