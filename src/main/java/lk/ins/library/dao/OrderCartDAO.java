package lk.ins.library.dao;

import lk.ins.library.entity.OrderCart;
import lk.ins.library.entity.OrderCartPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-04-02
 **/
public interface OrderCartDAO extends JpaRepository<OrderCart, OrderCartPK> {

    @Query("select count(oc) from OrderCart oc where oc.orderCartPK.student.regNo = ?1")
    int cartCountPerStudent(String studentId);
}
