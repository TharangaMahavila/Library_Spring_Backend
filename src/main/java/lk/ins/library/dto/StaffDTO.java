package lk.ins.library.dto;

import lk.ins.library.entity.Address;
import lk.ins.library.entity.Gender;
import lk.ins.library.entity.Name;
import lk.ins.library.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-24
 **/
@Data @AllArgsConstructor @NoArgsConstructor
public class StaffDTO implements Serializable {
    @NotNull(message = "Staff member NIC is required")
    private String id;
    private String initial;
    @NotNull(message = "Staff member's first name is required")
    private String fname;
    private String lname;
    private String contact;
    private String streetNo;
    @NotNull(message = "Address first street is required")
    private String firstStreet;
    private String secondStreet;
    private String town;
    @NotNull(message = "Gender is required")
    private Gender gender;
    private String salaryNo;
    @NotNull(message = "Active status is required")
    private boolean active;
    @NotNull(message = "Role authority is required")
    private Role role;
    private String image;
}
