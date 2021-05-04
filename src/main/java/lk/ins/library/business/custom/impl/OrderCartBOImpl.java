package lk.ins.library.business.custom.impl;

import lk.ins.library.business.custom.OrderCartBO;
import lk.ins.library.business.util.BookEntityDTOMapper;
import lk.ins.library.dao.BookDAO;
import lk.ins.library.dao.OrderCartDAO;
import lk.ins.library.dto.OrderCartDTO;
import lk.ins.library.entity.OrderCart;
import lk.ins.library.entity.OrderCartPK;
import lk.ins.library.entity.custom.BookCustomEntity;
import lk.ins.library.entity.custom.CartCustomEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-04-02
 **/
@Service
@Transactional
public class OrderCartBOImpl implements OrderCartBO {

    @Autowired
    private OrderCartDAO orderCartDAO;
    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private BookEntityDTOMapper mapper;

    @Override
    public void saveOrderCart(OrderCartDTO dto) throws Exception {
        OrderCart orderCart = mapper.getOrderCart(dto);
        orderCart.setCreatedAt(new Date(System.currentTimeMillis()));
        orderCart.setRequestStatus(false);
        orderCartDAO.save(orderCart);
    }

    @Override
    public void updateOrderCart(OrderCartDTO dto) throws Exception {
        OrderCart savedOrderCart = orderCartDAO.getOne(mapper.getOrderCartPk(dto));
        OrderCart orderCart = mapper.getOrderCart(dto);
        orderCart.setCreatedAt(savedOrderCart.getCreatedAt());
        orderCartDAO.save(orderCart);
    }

    @Override
    public void deleteOrderCart(OrderCartPK id) throws Exception {
        orderCartDAO.deleteById(id);
    }

    @Override
    public List<OrderCartDTO> findAllOrderCarts(Pageable page) throws Exception {
        return orderCartDAO.findAll(page).stream().map(orderCart -> mapper.getOrderCartDTO(orderCart)).collect(Collectors.toList());
    }

    @Override
    public OrderCartDTO findOrderCart(OrderCartPK id) throws Exception {
        return orderCartDAO.findById(id).map(orderCart -> mapper.getOrderCartDTO(orderCart)).get();
    }

    @Override
    public List<CartCustomEntity> findAllOrderCartsByUserId(String userId) throws Exception {

        ArrayList<CartCustomEntity> cartCustomEntities = new ArrayList<>();

        List<OrderCart> cartList = orderCartDAO.findAllByUserId(userId);
        for (OrderCart orderCart : cartList) {
            CartCustomEntity cartItem = new CartCustomEntity();
            cartItem.setUserId(orderCart.getOrderCartPK().getUser().getUsername());
            BookCustomEntity oneByRefNo = bookDAO.findOneByRefNo(orderCart.getOrderCartPK().getBookReference().getRefNo());
            cartItem.setBookCustomEntity(oneByRefNo);
            cartItem.setRequestedAt(orderCart.getRequestedAt());
            cartItem.setRequestStatus(orderCart.isRequestStatus());

            cartCustomEntities.add(cartItem);
        }
        return cartCustomEntities;
    }
}
