package lk.ins.library.api;

import lk.ins.library.business.custom.GradeBO;
import lk.ins.library.dto.GradeDTO;
import lk.ins.library.entity.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.json.KotlinSerializationJsonEncoder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-16
 **/
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping("/api/v1/grades")
public class GradeController {
    @Autowired
    private GradeBO gradeBO;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<GradeDTO>> getAllGrades() throws Exception{
        return new ResponseEntity<List<GradeDTO>>(gradeBO.findAllGrades(),HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Object> getGrade(@PathVariable int id) throws Exception{
        try {
            return new ResponseEntity<>(gradeBO.findGrade(id),HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No grade found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> saveGrade(@Valid @RequestBody GradeDTO dto) throws Exception{
        try {
            List<GradeDTO> allGrades = gradeBO.findAllGrades();
            for (GradeDTO grade : allGrades) {
                if(grade.getGrade().intValue()==dto.getGrade().intValue() && grade.getSection().equals(dto.getSection())
                        && grade.getYear().intValue()==dto.getYear().intValue()){
                    return new ResponseEntity<>("This grade already saved!",HttpStatus.BAD_REQUEST);
                }
            }
            gradeBO.saveGrade(dto);
            return new ResponseEntity<>(dto,HttpStatus.CREATED);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> deleteGrade(@PathVariable int id) throws Exception{
        try {
            gradeBO.findGrade(id);
            gradeBO.deleteGrade(id);
            return new ResponseEntity<>("Successfully deleted the grade",HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No grade found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> updateGrade(@Valid @RequestBody GradeDTO dto, @PathVariable int id) throws Exception{
        if(dto.getId() != id){
            return new ResponseEntity<>("Mismatched Id",HttpStatus.BAD_REQUEST);
        }
        try {
            gradeBO.findGrade(id);
            gradeBO.updateGrade(dto);
            return new ResponseEntity<>(dto,HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No grade found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

}
