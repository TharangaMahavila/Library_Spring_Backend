package lk.ins.library.entity;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-12
 **/
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "student")
public class Student implements SuperEntity{
    @Id
    @Column(name = "reg_no")
    private String regNo;
    @Embedded
    private Name name;
    @Column(name = "guardian_name")
    private String guardianName;
    @Embedded
    private Address address;
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;
    private String contact;
    private String image;

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "student_grades",
            joinColumns = {
            @JoinColumn(name = "student_id",referencedColumnName = "reg_no",nullable = false,updatable = false)},
            inverseJoinColumns = {
            @JoinColumn(name = "grade_id",referencedColumnName = "id",nullable = false,updatable = false)
    })
    private Set<Grade> grades = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

  /*  @OneToOne(mappedBy = "student")
    private User user;*/
}
