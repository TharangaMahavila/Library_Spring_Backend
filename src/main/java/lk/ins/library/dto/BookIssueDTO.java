package lk.ins.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-25
 **/
@Data @AllArgsConstructor @NoArgsConstructor
public class BookIssueDTO implements Serializable {

    @NotNull(message = "Student details is required")
    private String studentId;
    @NotNull(message = "Book reference details is required")
    private String bookRefId;
    @NotNull(message = "Issue date is required")
    private Date issueDate;
    private Date submittedDate;
    @NotNull(message = "Submit states is required")
    private boolean submitStatus;
}
