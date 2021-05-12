package lk.ins.library.api;

import lk.ins.library.business.custom.OrderCartBO;
import lk.ins.library.business.custom.impl.WSService;
import lk.ins.library.business.util.BookEntityDTOMapper;
import lk.ins.library.dao.BookDisposalDAO;
import lk.ins.library.dao.OrderCartDAO;
import lk.ins.library.dao.BookReferenceDAO;
import lk.ins.library.dto.OrderCartDTO;
import lk.ins.library.entity.OrderCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @orderCart:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-25
 **/
@PreAuthorize("permitAll()")
@RestController
@RequestMapping("/api/v1/cart")
public class OrderCartController {
    
    @Autowired
    private OrderCartBO orderCartBO;
    @Autowired
    private BookDisposalDAO bookDisposalDAO;
    @Autowired
    private OrderCartDAO orderCartDAO;
    @Autowired
    private BookReferenceDAO bookReferenceDAO;
    @Autowired
    private BookEntityDTOMapper mapper;
    @Autowired
    private WSService wsService;
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<OrderCartDTO>> getAllOrderCarts(Pageable page) throws Exception {
        return new ResponseEntity<List<OrderCartDTO>>(orderCartBO.findAllOrderCarts(page),HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Object> getOrderCartByUserId(@PathVariable String userId) throws Exception {
        try{
            return new ResponseEntity<>(orderCartBO.findAllOrderCartsByUserId(userId),HttpStatus.OK);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @GetMapping(value = "/{userId}/{refId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Object> getOrderCartById(@PathVariable String userId,@PathVariable String refId) throws Exception {
        try{
            OrderCartDTO orderCartDTO = new OrderCartDTO();
            orderCartDTO.setUserId(userId);
            orderCartDTO.setRefId(refId);
            return new ResponseEntity<>(orderCartBO.findOrderCart(mapper.getOrderCartPk(orderCartDTO)),HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No OrderCart Found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> saveOrderCart(@Valid @RequestBody OrderCartDTO dto) throws Exception {
        try{
            if(bookDisposalDAO.countBookDisposalByRefNo(dto.getRefId())>0){
                return new ResponseEntity<>("This book has been already disposed",HttpStatus.BAD_REQUEST);
            }
            if(orderCartDAO.cartCountPerUser(dto.getUserId())>10){
                return new ResponseEntity<>("Cart is full for this user",HttpStatus.BAD_REQUEST);
            }
            if(bookReferenceDAO.isOnlyReferenceBook(dto.getRefId())>0){
                return new ResponseEntity<>("This book is only for reference purpose",HttpStatus.BAD_REQUEST);
            }
            orderCartBO.findOrderCart(mapper.getOrderCartPk(dto));
            return new ResponseEntity<>("This record already saved.try diffrent record",HttpStatus.BAD_REQUEST);
        }catch (NoSuchElementException e){
            dto.setRequestedAt(null);
            dto.setRequestStatus(false);
            orderCartBO.saveOrderCart(dto);
            wsService.notifyFrontend("/api/v1/cart");
            return new ResponseEntity<>(dto,HttpStatus.CREATED);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @DeleteMapping("/{userId}/{refId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> deleteOrderCart(@PathVariable String userId,@PathVariable String refId) throws Exception {
        try{
            OrderCartDTO orderCartDTO = new OrderCartDTO();
            orderCartDTO.setUserId(userId);
            orderCartDTO.setRefId(refId);
            orderCartBO.findOrderCart(mapper.getOrderCartPk(orderCartDTO));
            orderCartBO.deleteOrderCart(mapper.getOrderCartPk(orderCartDTO));
            wsService.notifyFrontend("/api/v1/cart");
            return new ResponseEntity<>("Successfully deleted the orderCart", HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No Order Cart exist",HttpStatus.NOT_FOUND);
        } catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PutMapping(value = "/{userId}/{refId}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> updateOrderCart(@PathVariable String userId,@PathVariable String refId
            , @Valid @RequestBody OrderCartDTO dto) throws Exception{
        try{
            if(!((userId.equals(dto.getUserId())) && (refId.equals(dto.getRefId())) )){
                return new ResponseEntity<>("Mismatched Order Cart id",HttpStatus.BAD_REQUEST);
            }
            if(bookDisposalDAO.countBookDisposalByRefNo(dto.getRefId())>0){
                return new ResponseEntity<>("This book has been already disposed",HttpStatus.BAD_REQUEST);
            }
            if(!dto.isRequestStatus()){
                return new ResponseEntity<>("Request states should be true",HttpStatus.BAD_REQUEST);
            }
            orderCartBO.findOrderCart(mapper.getOrderCartPk(dto));
            orderCartBO.updateOrderCart(dto);
            wsService.notifyFrontend("/api/v1/cart");
            return new ResponseEntity<>(dto,HttpStatus.CREATED);
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>("No orderCart exist", HttpStatus.NOT_FOUND);
        } catch (Throwable e){
            throw  new Error(e);
        }
    }
}
