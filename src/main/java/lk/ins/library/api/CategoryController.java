package lk.ins.library.api;

import lk.ins.library.business.custom.CategoryBO;
import lk.ins.library.dto.CategoryDTO;
import lk.ins.library.dto.RackDTO;
import lk.ins.library.entity.RackPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-11
 **/
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryBO categoryBO;

    public CategoryController() {
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<CategoryDTO>> getAllCategories() throws Exception {
        return new ResponseEntity<List<CategoryDTO>>(categoryBO.findAllCategories(), HttpStatus.OK);
    }

    @GetMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Object> getCategory(@PathVariable int categoryId) throws Exception {
        try {
            return new ResponseEntity<>(categoryBO.findCategory(categoryId), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("No Category found", HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> saveCategory(@Valid @RequestBody CategoryDTO dto) throws Exception {
        try {
            categoryBO.saveCategory(dto);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> deleteCategory(@PathVariable int categoryId) throws Exception {
        try{
            categoryBO.findCategory(categoryId);
            categoryBO.deleteCategory(categoryId);
            return new ResponseEntity<>("Successfully deleted the Category", HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No Category exist",HttpStatus.NOT_FOUND);
        } catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PutMapping(value = "/{categoryId}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> updateCategory(@Valid @RequestBody CategoryDTO dto,
                                                 @PathVariable int categoryId) throws Exception{
        try{
            categoryBO.findCategory(categoryId);
            categoryBO.updateCategory(dto);
            return new ResponseEntity<>(dto,HttpStatus.CREATED);
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>("No Category exist", HttpStatus.NOT_FOUND);
        } catch (Throwable e){
            throw  new Error(e);
        }
    }
}
