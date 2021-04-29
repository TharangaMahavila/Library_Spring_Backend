package lk.ins.library.api;

import lk.ins.library.business.custom.AuthorBO;
import lk.ins.library.business.util.EntityDTOMapper;
import lk.ins.library.dto.AuthorDTO;
import lk.ins.library.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-02-23
 **/
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/api/v1/authors")
@RestController
public class AuthorController {

    @Autowired
    private AuthorBO authorBO;

    public AuthorController() throws SQLException {
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() throws Exception {
        return new ResponseEntity<List<AuthorDTO>>(authorBO.findAllAuthors(),HttpStatus.OK);
    }

    @GetMapping(value = "/{authorId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Object> getAuthorById(@PathVariable int authorId) throws Exception {
        try{
            return new ResponseEntity<>(authorBO.findAuthor(authorId),HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No Author Found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> saveAuthor(@Valid @RequestBody AuthorDTO dto) throws Exception {
        try{
            authorBO.findAuthorByName(dto.getName());
            return new ResponseEntity<>("Author is already saved",HttpStatus.BAD_REQUEST);
        }catch (NoSuchElementException e){
            authorBO.saveAuthor(dto);
            return new ResponseEntity<>(dto,HttpStatus.CREATED);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> deleteAuthor(@PathVariable int authorId) throws Exception {
        try{
            authorBO.findAuthor(authorId);
            authorBO.deleteAuthor(authorId);
            return new ResponseEntity<>("Successfully deleted the author",HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No user exist",HttpStatus.NOT_FOUND);
        } catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PutMapping(value = "/{authorId}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> updateAuthor( @Valid @RequestBody AuthorDTO dto, @PathVariable int authorId) throws Exception{
        if (dto.getId() != authorId){
            return new ResponseEntity<>("Mismatched Id",HttpStatus.BAD_REQUEST);
        }
        try{
            authorBO.findAuthor(authorId);
            authorBO.updateAuthor(dto);
            return new ResponseEntity<>(dto,HttpStatus.CREATED);
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>("No author exist", HttpStatus.NOT_FOUND);
        } catch (Throwable e){
            throw  new Error(e);
        }
    }
}
