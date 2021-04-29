package lk.ins.library.api;

import lk.ins.library.business.custom.StudentBO;
import lk.ins.library.business.custom.UserBO;
import lk.ins.library.dto.StudentDTO;
import lk.ins.library.dto.UserDTO;
import lk.ins.library.entity.Role;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-17
 **/
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@ControllerAdvice
@RequestMapping("/api/v1/students")
public class StudentController {
    @Autowired
    private StudentBO studentBO;
    @Autowired
    private UserBO userBO;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${path.images}")
    private String imagePath;
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<StudentDTO>> getAllStudents(Pageable page)throws Exception{
        return new ResponseEntity<>(studentBO.findAllStudents(page),HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getStudent(@PathVariable String id)throws Exception{
        try{
            return new ResponseEntity<>(studentBO.findStudent(id),HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No student found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @GetMapping(value = "/status",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getAllStudentByActiveStatus(@RequestParam(value = "status") boolean status, Pageable page)throws Exception{
        try{
            return new ResponseEntity<>(studentBO.findAllStudentsByActive(status, page),HttpStatus.OK);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> saveStudent(@Valid @RequestBody StudentDTO studentDTO) throws Exception{
        try {
            studentBO.findStudent(studentDTO.getRegNo());
            return new ResponseEntity<>("Student already saved!",HttpStatus.BAD_REQUEST);
        }catch (NoSuchElementException e){
            String password = passwordEncoder.encode("12345");
            UserDTO userDTO = new UserDTO(studentDTO.getRegNo(), password, true, Role.STUDENT);
            studentDTO.setImage(null);
            studentDTO.setActive(true);
            studentBO.saveStudent(studentDTO,userDTO);
            return new ResponseEntity<>(studentDTO,HttpStatus.CREATED);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> deleteStudent(@PathVariable String id)throws Exception{
        try{
            studentBO.findStudent(id);
            studentBO.deleteStudent(id);
            return new ResponseEntity<>("Successfully deleted the student",HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No Student found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> updateStudent(@Valid @RequestBody StudentDTO studentDTO,@PathVariable String id) throws Exception{
        if(!id.equals(studentDTO.getRegNo())){
            return new ResponseEntity<>("Mismatched id",HttpStatus.BAD_REQUEST);
        }
        try{
            studentBO.findStudent(id);
            UserDTO user = userBO.findUser(id);
            user.setActive(studentDTO.isActive());
            user.setRole(studentDTO.getRole());
            studentBO.updateStudent(studentDTO,user);
            return new ResponseEntity<>(studentDTO,HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No student found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PreAuthorize("permitAll()")
    @PostMapping(value = "/image/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveImage(@RequestParam("file") MultipartFile image,@PathVariable String id) throws Exception{
        try{
            StudentDTO student = studentBO.findStudent(id);
            String filename = image.getOriginalFilename();
            String extentsion = FilenameUtils.getExtension(filename).toLowerCase();
            if(!(extentsion.equals("jpg") || extentsion.equals("jpeg") || extentsion.equals("png"))){
                return new ResponseEntity<>("File Format is not allowed",HttpStatus.BAD_REQUEST);
            }
            String savePath = imagePath+File.separator+"Students"+File.separator+ student.getInitial()+"_"+student.getFname()+"_"+student.getLname()+"_"
                    +new Timestamp(System.currentTimeMillis()) +"."+FilenameUtils.getExtension(filename);
            Path path = Paths.get(imagePath+File.separator+"Students");
            if(!Files.exists(path)){
                Files.createDirectories(path);
            }
            File file = new File(savePath);
            FileUtils.writeByteArrayToFile(file,image.getBytes());
            studentBO.updateStudentImage(id,savePath);
            return new ResponseEntity<>("Successfully updated the student profile image",HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("No user found!",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleFileUploadError(RedirectAttributes ra){
        ra.addFlashAttribute("error","Image size should be less than "+maxFileSize);
        return ra.getFlashAttributes().get("error").toString();
    }

    @PreAuthorize("permitAll()")
    @GetMapping(value = "/image/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getStudentImage(@PathVariable String id)throws Exception{
        try {
            StudentDTO student = studentBO.findStudent(id);
            String imagePath = student.getImage();
            if(imagePath!=null && !imagePath.equals("")){
                File file = new File(imagePath);
                if(!file.exists()){
                    return new ResponseEntity<>("Student image has been missed",HttpStatus.NOT_FOUND);
                }
                byte[] image = FileUtils.readFileToByteArray(file);

                return new ResponseEntity<>(image,HttpStatus.OK);
            }else {
                return new ResponseEntity<>("No student image found",HttpStatus.NOT_FOUND);
            }
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No student found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PreAuthorize("permitAll()")
    @DeleteMapping(value = "/image/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> deleteImage(@PathVariable String id) throws Exception{
        try{
            StudentDTO student = studentBO.findStudent(id);
            if(student.getImage() != null){
                studentBO.deleteStudentImage(id);
            }
            return new ResponseEntity<>("Successfully deleted the student profile image",HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("No user found!",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @GetMapping(value = "/count")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllStudentCount(@RequestParam(required = false) boolean status
            ,@RequestParam(required = false) String regNo
            ,@RequestParam(required = false) String name) throws Exception{
        if(regNo != null){
            return new ResponseEntity<>(studentBO.countStudentByRegNo(regNo),HttpStatus.OK);
        }
        if(name != null){
            return new ResponseEntity<>(studentBO.countStudentByName(name),HttpStatus.OK);
        }
        return new ResponseEntity<>(studentBO.countAllStudents(status),HttpStatus.OK);
    }

    @GetMapping(value = "/count/between")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllTodayAddedStudentCount(@RequestParam("startDate") long startDate
            ,@RequestParam("endDate") long endDate) throws Exception{

        Date start = new Date(startDate);
        Date end = new Date(endDate);
        return new ResponseEntity<>(studentBO.countStudentBetweenPeriod(start,end),HttpStatus.OK);
    }

    @GetMapping(value = "/validate/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> validateStudentRegNo(@PathVariable String id) throws Exception{
        int studentCount = studentBO.countStudentByRegNo(id);
        int userCount = userBO.countUserByUsername(id);
        if(studentCount == 0 && userCount == 0){
            return new ResponseEntity<>(0,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(1,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/search")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> searchStudentByRegNo(@RequestParam(required = false) String regNo
            ,@RequestParam(required = false) String name, Pageable page) throws Exception{
        if(regNo != null){
            return new ResponseEntity<>(studentBO.searchStudentByRegNo(regNo,page),HttpStatus.OK);
        }
        return new ResponseEntity<>(studentBO.searchStudentByName(name,page),HttpStatus.OK);
    }
}
