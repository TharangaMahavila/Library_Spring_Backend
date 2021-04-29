package lk.ins.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-10
 **/
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "category")
public class Category implements SuperEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    @NotNull(message = "Category name is required")
    private String name;

    @Setter(AccessLevel.NONE)
    @JsonIgnore
    @JsonIgnoreProperties("categories")
    @ManyToMany(mappedBy = "categories")
    private List<Book> books;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Category(int id,String name) {
        this.id = id;
        this.name = name;
    }

}
