package lk.ins.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-04-02
 **/
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "order_cart")
public class OrderCart implements SuperEntity{

    @EmbeddedId
    private OrderCartPK orderCartPK;
    @Temporal(TemporalType.DATE)
    @Column(name = "request_at")
    private Date requestedAt;
    @Column(name = "request_status")
    private boolean requestStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private java.util.Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
