package lk.ins.library.api;

import lk.ins.library.business.custom.OrderCartBO;
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
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<OrderCartDTO>> getAllOrderCarts(Pageable page) throws Exception {
        return new ResponseEntity<List<OrderCartDTO>>(orderCartBO.findAllOrderCarts(page),HttpStatus.OK);
    }

    @GetMapping(value = "/{studentId}/{refId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Object> getOrderCartById(@PathVariable String studentId,@PathVariable String refId) throws Exception {
        try{
            OrderCartDTO orderCartDTO = new OrderCartDTO();
            orderCartDTO.setStudentId(studentId);
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
            if(orderCartDAO.cartCountPerStudent(dto.getStudentId())>10){
                return new ResponseEntity<>("Cart is full for this student",HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<>(dto,HttpStatus.CREATED);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @DeleteMapping("/{studentId}/{refId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> deleteOrderCart(@PathVariable String studentId,@PathVariable String refId) throws Exception {
        try{
            OrderCartDTO orderCartDTO = new OrderCartDTO();
            orderCartDTO.setStudentId(studentId);
            orderCartDTO.setRefId(refId);
            orderCartBO.findOrderCart(mapper.getOrderCartPk(orderCartDTO));
            orderCartBO.deleteOrderCart(mapper.getOrderCartPk(orderCartDTO));
            return new ResponseEntity<>("Successfully deleted the orderCart", HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No Order Cart exist",HttpStatus.NOT_FOUND);
        } catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PutMapping(value = "/{studentId}/{refId}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> updateOrderCart(@PathVariable String studentId,@PathVariable String refId
            , @Valid @RequestBody OrderCartDTO dto) throws Exception{
        try{
            if(!((studentId.equals(dto.getStudentId())) && (refId.equals(dto.getRefId())) )){
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
            return new ResponseEntity<>(dto,HttpStatus.CREATED);
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>("No orderCart exist", HttpStatus.NOT_FOUND);
        } catch (Throwable e){
            throw  new Error(e);
        }
    }
}
