package lk.ins.library.dao;

import lk.ins.library.entity.Rack;
import lk.ins.library.entity.RackPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/** *@author:Tharanga Mahavila <tharangamahavila@gmail.com>
    *@since : 2021-02-26
**/
public interface RackDAO extends JpaRepository<Rack, Integer> {

    Rack findByRackPK(RackPK pk);
}
