package lk.ins.library.business.custom;

import lk.ins.library.business.SuperBO;
import lk.ins.library.dto.OrderCartDTO;
import lk.ins.library.entity.OrderCartPK;
import lk.ins.library.entity.custom.CartCustomEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-04-02
 **/
public interface OrderCartBO extends SuperBO {

    public void saveOrderCart(OrderCartDTO dto) throws Exception;
    public void updateOrderCart(OrderCartDTO dto) throws Exception;
    public void deleteOrderCart(OrderCartPK id) throws Exception;
    public List<OrderCartDTO> findAllOrderCarts(Pageable page) throws Exception;
    public OrderCartDTO findOrderCart(OrderCartPK id) throws Exception;

    public List<CartCustomEntity> findAllOrderCartsByUserId(String userId) throws Exception;
}
