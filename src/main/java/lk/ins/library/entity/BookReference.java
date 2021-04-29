package lk.ins.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-25
 **/
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "book_reference")
public class BookReference implements SuperEntity{

    @Id
    @Column(name = "ref_no")
    private String refNo;
    private String barcode;
    @Column(name = "is_reference")
    private boolean isReference;

    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "book_id",referencedColumnName = "id",nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "supplier_id",referencedColumnName = "id",nullable = false)
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "rack_id",referencedColumnName = "id",nullable = false)
    private Rack rack;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
