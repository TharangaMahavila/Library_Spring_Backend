package lk.ins.library.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-24
 **/
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "book")
public class Book implements SuperEntity {
    @Id
    @Column(name = "id")
    private String bookId;
    @Column(unique = true)
    private String englishName;
    @Column(unique = true)
    private String sinhalaName;
    private Integer year;
    @Column(precision = 10, scale = 2)
    @Type(type = "big_decimal")
    private BigDecimal price;
    private Medium medium;
    private Integer pages;
    private String image;
    @Column(columnDefinition = "TEXT")
    private String note;

    @JsonIgnoreProperties("books")
    @ManyToOne
    @JoinColumn(name = "author_id",referencedColumnName = "id",nullable = false)
    private Author author;

    @JsonIgnore
    @JsonIgnoreProperties("books")
    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "book_category",
    joinColumns = {
            @JoinColumn(name = "book_id",referencedColumnName = "id",nullable = false,updatable = false)},
    inverseJoinColumns = {
            @JoinColumn(name = "category_id",referencedColumnName = "id",nullable = false,updatable = false)
    })
    private Set<Category> categories;

    @Setter(AccessLevel.NONE)
    @JsonIgnore
    @OneToMany(mappedBy = "book")
    private List<BookReference> bookReferences;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Book(String bookId, String englishName, String sinhalaName, Integer year, BigDecimal price, Medium medium, Integer pages, String image, String note, Author author, Set<Category> categories) {
        this.bookId = bookId;
        this.englishName = englishName;
        this.sinhalaName = sinhalaName;
        this.year = year;
        this.price = price;
        this.medium = medium;
        this.pages = pages;
        this.image = image;
        this.note = note;
        this.author = author;
        this.categories = categories;
    }
}
