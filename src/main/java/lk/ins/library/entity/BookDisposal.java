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
@Table(name = "book_disposal")
public class BookDisposal implements SuperEntity{

    @Id
    @Column(name = "ref_no")
    private String refNo;
    private Date date;
    @Column(columnDefinition = "TEXT")
    private String note;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private java.util.Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

 /*   @OneToOne
    @JoinColumn(name = "ref_no",referencedColumnName = "ref_no")
    private BookReference bookReference;*/
}
