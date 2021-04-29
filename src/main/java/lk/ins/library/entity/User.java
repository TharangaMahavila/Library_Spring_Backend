package lk.ins.library.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-19
 **/
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@DynamicInsert
@Table(name = "user")
public class User implements SuperEntity{
    @Id
    @Column(updatable = false)
    private String username;
    @ColumnDefault("'5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5'")
    private String password;
    private boolean isActive;
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

   /* @OneToOne(mappedBy = "user")
    private Student student;*/
}
