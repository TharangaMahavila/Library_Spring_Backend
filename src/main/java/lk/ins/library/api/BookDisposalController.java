package lk.ins.library.api;

import lk.ins.library.business.custom.BookDisposalBO;
import lk.ins.library.dao.BookIssueDAO;
import lk.ins.library.dto.BookDisposalDTO;
import lk.ins.library.entity.BookReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @bookDisposal:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-25
 **/
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping("/api/v1/bookDisposal")
public class BookDisposalController {

    @Autowired
    private BookDisposalBO bookDisposalBO;
    @Autowired
    private BookIssueDAO bookIssueDAO;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<BookDisposalDTO>> getAllBookDisposals(Pageable page) throws Exception {
        return new ResponseEntity<List<BookDisposalDTO>>(bookDisposalBO.findAllBookDisposals(page),HttpStatus.OK);
    }

    @GetMapping(value = "/{bookDisposalId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Object> getBookDisposalById(@PathVariable String bookDisposalId) throws Exception {
        try{
            return new ResponseEntity<>(bookDisposalBO.findBookDisposal(bookDisposalId),HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No BookDisposal Found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> saveBookDisposal(@Valid @RequestBody BookDisposalDTO dto) throws Exception {
        try{
            if(bookIssueDAO.isAlreadyIssued(dto.getRefNo())>0){
                return new ResponseEntity<>("This book has been issued.cannot dispose",HttpStatus.UNAUTHORIZED);
            }
            bookDisposalBO.saveBookDisposal(dto);
            return new ResponseEntity<>(dto,HttpStatus.CREATED);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @DeleteMapping("/{bookDisposalId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> deleteBookDisposal(@PathVariable String bookDisposalId) throws Exception {
        try{
            bookDisposalBO.findBookDisposal(bookDisposalId);
            bookDisposalBO.deleteBookDisposal(bookDisposalId);
            return new ResponseEntity<>("Successfully deleted the bookDisposal", HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No Book disposal exist",HttpStatus.NOT_FOUND);
        } catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PutMapping(value = "/{bookDisposalId}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> updateBookDisposal(@Valid @RequestBody BookDisposalDTO dto, @PathVariable String bookDisposalId) throws Exception{
        if(!dto.getRefNo().equals(bookDisposalId)){
            return new ResponseEntity<>("Mismatched disposal Id",HttpStatus.BAD_REQUEST);
        }
        try{
            bookDisposalBO.findBookDisposal(bookDisposalId);
            bookDisposalBO.updateBookDisposal(dto);
            return new ResponseEntity<>(dto,HttpStatus.CREATED);
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>("No bookDisposal exist", HttpStatus.NOT_FOUND);
        } catch (Throwable e){
            throw  new Error(e);
        }
    }
}
