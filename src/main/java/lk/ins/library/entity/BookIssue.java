package lk.ins.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-25
 **/
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "book_issue")
public class BookIssue implements SuperEntity {

    @EmbeddedId
    private BookIssuePK bookIssuePK;
    @Column(name = "submitted_date")
    private Date submittedDate;
    @Column(name = "submit_status")
    private boolean submitStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private java.util.Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
