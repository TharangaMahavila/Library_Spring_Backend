package lk.ins.library.api;

import lk.ins.library.business.custom.BookIssueBO;
import lk.ins.library.business.util.BookEntityDTOMapper;
import lk.ins.library.dao.BookDisposalDAO;
import lk.ins.library.dao.BookIssueDAO;
import lk.ins.library.dao.BookReferenceDAO;
import lk.ins.library.dto.BookIssueDTO;
import lk.ins.library.entity.BookIssuePK;
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
 * @bookIssue:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-25
 **/
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping("/api/v1/bookIssue")
public class BookIssueController {
    
    @Autowired
    private BookIssueBO bookIssueBO;
    @Autowired
    private BookDisposalDAO bookDisposalDAO;
    @Autowired
    private BookIssueDAO bookIssueDAO;
    @Autowired
    private BookReferenceDAO bookReferenceDAO;
    @Autowired
    private BookEntityDTOMapper mapper;
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<BookIssueDTO>> getAllBookIssues(Pageable page) throws Exception {
        return new ResponseEntity<List<BookIssueDTO>>(bookIssueBO.findAllBookIssues(page),HttpStatus.OK);
    }

    @GetMapping(value = "/{studentId}/{refId}/{date}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Object> getBookIssueById(@PathVariable String studentId,@PathVariable String refId,@PathVariable Date date) throws Exception {
        try{
            BookIssueDTO bookIssueDTO = new BookIssueDTO();
            bookIssueDTO.setStudentId(studentId);
            bookIssueDTO.setBookRefId(refId);
            bookIssueDTO.setIssueDate(date);
            return new ResponseEntity<>(bookIssueBO.findBookIssue(mapper.getBookIssuePK(bookIssueDTO)),HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No BookIssue Found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> saveBookIssue(@Valid @RequestBody BookIssueDTO dto) throws Exception {
        try{
            if(bookDisposalDAO.countBookDisposalByRefNo(dto.getBookRefId())>0){
                return new ResponseEntity<>("This book has been already disposed",HttpStatus.BAD_REQUEST);
            }
            if(bookIssueDAO.isAlreadyIssued(dto.getBookRefId())>0){
                return new ResponseEntity<>("This book has been already issued",HttpStatus.BAD_REQUEST);
            }
            if(bookReferenceDAO.isOnlyReferenceBook(dto.getBookRefId())>0){
                return new ResponseEntity<>("This book is only for reference purpose",HttpStatus.BAD_REQUEST);
            }
            bookIssueBO.findBookIssue(mapper.getBookIssuePK(dto));
            return new ResponseEntity<>("This record already saved.try diffrent record",HttpStatus.BAD_REQUEST);
        }catch (NoSuchElementException e){
            dto.setSubmittedDate(null);
            dto.setSubmitStatus(false);
            bookIssueBO.saveBookIssue(dto);
            return new ResponseEntity<>(dto,HttpStatus.CREATED);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @DeleteMapping("/{studentId}/{refId}/{date}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> deleteBookIssue(@PathVariable String studentId,@PathVariable String refId,@PathVariable Date date) throws Exception {
        try{
            BookIssueDTO bookIssueDTO = new BookIssueDTO();
            bookIssueDTO.setStudentId(studentId);
            bookIssueDTO.setBookRefId(refId);
            bookIssueDTO.setIssueDate(date);
            bookIssueBO.findBookIssue(mapper.getBookIssuePK(bookIssueDTO));
            bookIssueBO.deleteBookIssue(mapper.getBookIssuePK(bookIssueDTO));
            return new ResponseEntity<>("Successfully deleted the bookIssue", HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No Book issue exist",HttpStatus.NOT_FOUND);
        } catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PutMapping(value = "/{studentId}/{refId}/{date}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> updateBookIssue(@PathVariable String studentId,@PathVariable String refId
            ,@PathVariable Date date, @Valid @RequestBody BookIssueDTO dto) throws Exception{
        try{
            if(!((studentId.equals(dto.getStudentId())) && (refId.equals(dto.getBookRefId())) && (date.toLocalDate().isEqual(dto.getIssueDate().toLocalDate())))){
                return new ResponseEntity<>("Mismatched Issue id",HttpStatus.BAD_REQUEST);
            }
            if(bookDisposalDAO.countBookDisposalByRefNo(dto.getBookRefId())>0){
                return new ResponseEntity<>("This book has been already disposed",HttpStatus.BAD_REQUEST);
            }
            BookIssueDTO bookIssue = bookIssueBO.findBookIssue(mapper.getBookIssuePK(dto));
            if(bookIssue.isSubmitStatus() == true){
                return new ResponseEntity<>("Cannot modify this operaion",HttpStatus.UNAUTHORIZED);
            }
            if(dto.isSubmitStatus() != true || dto.getSubmittedDate()==null){
                return new ResponseEntity<>("Submit states true and submit date should be provided",HttpStatus.BAD_REQUEST);
            }
            if(dto.getSubmittedDate().toLocalDate().isBefore(bookIssue.getIssueDate().toLocalDate())){
                return new ResponseEntity<>("Submitted date should be after the issued date",HttpStatus.BAD_REQUEST);
            }
            bookIssueBO.updateBookIssue(dto);
            return new ResponseEntity<>(dto,HttpStatus.CREATED);
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>("No bookIssue exist", HttpStatus.NOT_FOUND);
        } catch (Throwable e){
            throw  new Error(e);
        }
    }
}
