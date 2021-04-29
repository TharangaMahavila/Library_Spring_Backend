package lk.ins.library.api;

import lk.ins.library.business.custom.AuthorBO;
import lk.ins.library.business.custom.RackBO;
import lk.ins.library.dto.AuthorDTO;
import lk.ins.library.dto.RackDTO;
import lk.ins.library.entity.RackPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-02-26
 **/
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping("/api/v1/racks")
public class RackController {
    @Autowired
    private RackBO rackBO;

    public RackController() throws SQLException {
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<RackDTO>> getAllRacks() throws Exception {
        return new ResponseEntity<List<RackDTO>>(rackBO.findAllRacks(),HttpStatus.OK);
    }

    @GetMapping(value = "/{rackId}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Object> getAuthorById(@PathVariable int rackId) throws Exception {
        try{
            return new ResponseEntity<>(rackBO.findRack(rackId),HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No Rack Found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> saveRack(@Valid @RequestBody RackDTO dto) throws Exception {
        try{
            RackPK pk = new RackPK(dto.getRackNo(), dto.getShellNo());
            RackDTO rackByRackPk = rackBO.getRackByRackPk(pk);
            if((rackByRackPk!=null) && rackByRackPk.getId()!= dto.getId()){
                return new ResponseEntity<>("Invalid Rack details",HttpStatus.BAD_REQUEST);
            }
            rackBO.saveRack(dto);
            return new ResponseEntity<>(dto,HttpStatus.CREATED);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @DeleteMapping("/{rackId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> deleteRack(@PathVariable int rackId) throws Exception {
        try{
            rackBO.findRack(rackId);
            rackBO.deleteRack(rackId);
            return new ResponseEntity<>("Successfully deleted the Rack", HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No Rack exist",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PutMapping(value = "/{rackId}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> updateRack(@Valid @RequestBody RackDTO dto, @PathVariable int rackId) throws Exception{
        try{
            if(rackId!= dto.getId()){
                return new ResponseEntity<>("Mismatched rack id",HttpStatus.BAD_REQUEST);
            }
            rackBO.findRack(rackId);
            RackPK pk = new RackPK(dto.getRackNo(), dto.getShellNo());
            RackDTO rackByRackPk = rackBO.getRackByRackPk(pk);
            if((rackByRackPk!=null) && rackByRackPk.getId()!= dto.getId()){
                return new ResponseEntity<>("Invalid Rack details",HttpStatus.BAD_REQUEST);
            }
            rackBO.updateRack(dto);
            return new ResponseEntity<>(dto,HttpStatus.CREATED);
        }catch (NoSuchElementException e) {
            return new ResponseEntity<>("No Rack exist", HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }
}
