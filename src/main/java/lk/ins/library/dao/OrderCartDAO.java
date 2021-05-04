package lk.ins.library.dao;

import lk.ins.library.entity.OrderCart;
import lk.ins.library.entity.OrderCartPK;
import lk.ins.library.entity.custom.BookCustomEntity;
import lk.ins.library.entity.custom.CartCustomEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-04-02
 **/
public interface OrderCartDAO extends JpaRepository<OrderCart, OrderCartPK> {

    @Query("select count(oc) from OrderCart oc where oc.orderCartPK.user.username = ?1")
    int cartCountPerUser(String userId);

    @Query("select oc from OrderCart oc where oc.orderCartPK.user.username = ?1")
    List<OrderCart> findAllByUserId(String userId);
}
