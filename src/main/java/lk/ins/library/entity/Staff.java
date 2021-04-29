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
 * @since : 2021-03-24
 **/
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "staff")
public class Staff implements SuperEntity{
    @Id
    private String id;
    private Name name;
    private String contact;
    private Address address;
    private Gender gender;
    @Column(name = "salary_no")
    private String salaryNo;
    private String image;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
