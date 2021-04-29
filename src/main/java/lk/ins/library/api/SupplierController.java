package lk.ins.library.api;

import lk.ins.library.business.custom.SupplierBO;
import lk.ins.library.dto.SupplierDTO;
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
 * @supplier:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-24
 **/
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {

    @Autowired
    private SupplierBO supplierBO;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() throws Exception {
        return new ResponseEntity<List<SupplierDTO>>(supplierBO.findAllSuppliers(),HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Object> getSupplierById(@PathVariable int id) throws Exception {
        try{
            return new ResponseEntity<>(supplierBO.findSupplier(id),HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No Supplier Found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> saveSupplier(@Valid @RequestBody SupplierDTO dto) throws Exception {
        try{
            supplierBO.saveSupplier(dto);
            return new ResponseEntity<>(dto,HttpStatus.CREATED);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> deleteSupplier(@PathVariable int id) throws Exception {
        try{
            supplierBO.findSupplier(id);
            supplierBO.deleteSupplier(id);
            return new ResponseEntity<>("Successfully deleted the supplier", HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No user exist",HttpStatus.NOT_FOUND);
        } catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> updateSupplier(@Valid @RequestBody SupplierDTO dto, @PathVariable int id) throws Exception{
        if (dto.getId()!=id){
            return new ResponseEntity<>("Mismatched Id",HttpStatus.BAD_REQUEST);
        }
        try{
            supplierBO.findSupplier(id);
            supplierBO.updateSupplier(dto);
            return new ResponseEntity<>(dto,HttpStatus.CREATED);
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>("No supplier exist", HttpStatus.NOT_FOUND);
        } catch (Throwable e){
            throw  new Error(e);
        }
    }
}
