package lk.ins.library.dto;

import lk.ins.library.entity.Active;
import lk.ins.library.entity.Gender;
import lk.ins.library.entity.Name;
import lk.ins.library.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-12
 **/
@Data @AllArgsConstructor @NoArgsConstructor
public class StudentDTO implements Serializable {
    @NotNull(message = "Student register number should be provided")
    private String regNo;
    private String initial;
    @NotNull(message = "Student first name is required")
    private String fname;
    private String lname;
    @NotNull(message = "student's guardian name is required")
    private String guardianName;
    private String streetNo;
    @NotNull(message = "Address first street is required")
    private String firstStreet;
    private String secondStreet;
    private String town;
    @NotNull(message = "Gender is required")
    private Gender gender;
    private String contact;
    @NotNull(message = "Active status is required")
    private boolean active;
    @NotNull(message = "Role authority is required")
    private Role role;
    private String image;
    @NotNull(message = "Grade is required")
    private Set<GradeDTO> grades = new HashSet<>();
}
