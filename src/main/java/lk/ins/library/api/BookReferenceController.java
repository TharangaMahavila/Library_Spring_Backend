package lk.ins.library.api;

import lk.ins.library.business.custom.BookReferenceBO;
import lk.ins.library.dto.BookReferenceDTO;
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
 * @bookReference:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-25
 **/
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping("/api/v1/bookReferences")
public class BookReferenceController {

    @Autowired
    private BookReferenceBO bookReferenceBO;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<BookReferenceDTO>> getAllBookReferences(Pageable page) throws Exception {
        return new ResponseEntity<List<BookReferenceDTO>>(bookReferenceBO.findAllBookReferences(page),HttpStatus.OK);
    }

    @GetMapping(value = "/{bookReferenceId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Object> getBookReferenceById(@PathVariable String bookReferenceId) throws Exception {
        try{
            return new ResponseEntity<>(bookReferenceBO.findBookReference(bookReferenceId),HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No BookReference Found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> saveBookReference(@Valid @RequestBody BookReferenceDTO dto) throws Exception {
        try{
            bookReferenceBO.saveBookReference(dto);
            return new ResponseEntity<>(dto,HttpStatus.CREATED);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @DeleteMapping("/{bookReferenceId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> deleteBookReference(@PathVariable String bookReferenceId) throws Exception {
        try{
            bookReferenceBO.findBookReference(bookReferenceId);
            bookReferenceBO.deleteBookReference(bookReferenceId);
            return new ResponseEntity<>("Successfully deleted the bookReference", HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No Book reference exist",HttpStatus.NOT_FOUND);
        } catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PutMapping(value = "/{bookReferenceId}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> updateBookReference(@Valid @RequestBody BookReferenceDTO dto, @PathVariable String bookReferenceId) throws Exception{
        if (!dto.getRefNo().equals(bookReferenceId)){
            return new ResponseEntity<>("Mismatched Id",HttpStatus.BAD_REQUEST);
        }
        try{
            bookReferenceBO.findBookReference(bookReferenceId);
            bookReferenceBO.updateBookReference(dto);
            return new ResponseEntity<>(dto,HttpStatus.CREATED);
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>("No book reference exist", HttpStatus.NOT_FOUND);
        } catch (Throwable e){
            throw  new Error(e);
        }
    }
}
