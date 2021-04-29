package lk.ins.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-02-26
 **/
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "rack")
public class Rack implements SuperEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Embedded
    @NotNull(message = "Rack number & Shell number is required")
    private RackPK rackPK;

    public Rack(int id, String rackNo, String shellNo) {
        this.id = id;
        this.rackPK = new RackPK(rackNo,shellNo);
    }
}
