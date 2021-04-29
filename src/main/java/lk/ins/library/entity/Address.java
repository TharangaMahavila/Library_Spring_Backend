package lk.ins.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-16
 **/
@Embeddable
@Data @AllArgsConstructor @NoArgsConstructor
public class Address {
    @Column(name = "street_no")
    private String streetNo;
    @Column(name = "first_street")
    private String firstStreet;
    @Column(name = "second_street")
    private String secondStreet;
    private String town;
}
