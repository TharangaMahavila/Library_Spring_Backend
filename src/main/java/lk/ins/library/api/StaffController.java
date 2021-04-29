package lk.ins.library.api;

import lk.ins.library.business.custom.StaffBO;
import lk.ins.library.business.custom.StaffBO;
import lk.ins.library.business.custom.UserBO;
import lk.ins.library.dto.StaffDTO;
import lk.ins.library.dto.StudentDTO;
import lk.ins.library.dto.UserDTO;
import lk.ins.library.entity.Role;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
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

import javax.servlet.annotation.MultipartConfig;
import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-24
 **/
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@ControllerAdvice
@RequestMapping(value = "/api/v1/staffs")
public class StaffController {
    @Autowired
    private StaffBO staffBO;
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
    public ResponseEntity<List<StaffDTO>> getAllStaffs(Pageable page)throws Exception{
        return new ResponseEntity<>(staffBO.findAllStaffs(page),HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getStaff(@PathVariable String id)throws Exception{
        try{
            return new ResponseEntity<>(staffBO.findStaff(id),HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No staff found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @GetMapping(value = "/status",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getAllStaffByActiveStatus(@RequestParam(value = "status") boolean status, Pageable page)throws Exception{
        try{
            return new ResponseEntity<>(staffBO.findAllActiveStaff(status, page),HttpStatus.OK);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> saveStaff(@Valid @RequestBody StaffDTO staffDTO) throws Exception{
        try {
            staffBO.findStaff(staffDTO.getId());
            return new ResponseEntity<>("Staff already saved!",HttpStatus.BAD_REQUEST);
        }catch (NoSuchElementException e){
            if(staffDTO.getRole().toString().toLowerCase().equals("student")){
                return new ResponseEntity<>("Invalid Role authority.",HttpStatus.BAD_REQUEST);
            }
            String staffPassword = passwordEncoder.encode("staff");
            String adminPassword = passwordEncoder.encode("admin");
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(staffDTO.getId());
            userDTO.setActive(true);
            if(staffDTO.getRole().toString().toLowerCase().equals("admin")){
                userDTO.setRole(Role.ADMIN);
                userDTO.setPassword(adminPassword);
            }else{
                userDTO.setRole(Role.STAFF);
                userDTO.setPassword(staffPassword);
            }
            staffDTO.setImage(null);
            staffBO.saveStaff(staffDTO,userDTO);
            return new ResponseEntity<>(staffDTO,HttpStatus.CREATED);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<Object> deleteStaff(@PathVariable String id)throws Exception{
        try{
            if(id.equals("admin")){
                return new ResponseEntity<>("You cannot delete the admin",HttpStatus.FORBIDDEN);
            }
            staffBO.findStaff(id);
            staffBO.deleteStaff(id);
            return new ResponseEntity<>("Successfully deleted the staff",HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No Staff found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> updateStaff(@Valid @RequestBody StaffDTO staffDTO,@PathVariable String id) throws Exception{
        if(!id.equals(staffDTO.getId())){
            return new ResponseEntity<>("Mismatched id",HttpStatus.BAD_REQUEST);
        }
        try{
            staffBO.findStaff(id);
            UserDTO user = userBO.findUser(id);
            if(id.equals("admin") && !(staffDTO.getRole().toString().toLowerCase().equals("admin") && staffDTO.isActive()==true)){
                return new ResponseEntity<>("You cannot change admin role or active status",HttpStatus.BAD_REQUEST);
            }
            user.setActive(staffDTO.isActive());
            user.setRole(staffDTO.getRole());
            staffBO.updateStaff(staffDTO,user);
            return new ResponseEntity<>(staffDTO,HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No staff found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PostMapping(value = "/image/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> saveImage(@RequestParam("file") MultipartFile image, @PathVariable String id) throws Exception{
        try{
            StaffDTO staff = staffBO.findStaff(id);
            String filename = image.getOriginalFilename();
            String extentsion = FilenameUtils.getExtension(filename).toLowerCase();
            if(!(extentsion.equals("jpg") || extentsion.equals("jpeg") || extentsion.equals("png"))){
                return new ResponseEntity<>("File Format is not allowed",HttpStatus.BAD_REQUEST);
            }
            String savePath = imagePath+File.separator+"Staffs"+File.separator+ staff.getInitial()+"_"+staff.getFname()+"_"+staff.getLname()+"_"
                    +new Timestamp(System.currentTimeMillis()) +"."+FilenameUtils.getExtension(filename);
            Path path = Paths.get(imagePath+File.separator+"Staffs");
            if(!Files.exists(path)){
                Files.createDirectories(path);
            }
            File file = new File(savePath);
            FileUtils.writeByteArrayToFile(file,image.getBytes());
            staffBO.updateStaffImage(id,savePath);
            return new ResponseEntity<>("Successfully updated the staff profile image",HttpStatus.CREATED);
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
    public ResponseEntity<?> getStaffImage(@PathVariable String id)throws Exception{
        try {
            StaffDTO staff = staffBO.findStaff(id);
            String imagePath = staff.getImage();
            if(imagePath!=null && !imagePath.equals("")){
                File file = new File(imagePath);
                if(!file.exists()){
                    return new ResponseEntity<>("Staff image has been missed",HttpStatus.NOT_FOUND);
                }
                byte[] image = FileUtils.readFileToByteArray(file);

                return new ResponseEntity<>(image,HttpStatus.OK);
            }else {
                return new ResponseEntity<>("No staff image found",HttpStatus.NOT_FOUND);
            }
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No staff found",HttpStatus.NOT_FOUND);
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
            StaffDTO staff = staffBO.findStaff(id);
            if(staff.getImage() != null){
                staffBO.deleteStaffImage(id);
            }
            return new ResponseEntity<>("Successfully deleted the staff profile image",HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("No user found!",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @GetMapping(value = "/count")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllStaffCount(@RequestParam(required = false) boolean status
            ,@RequestParam(required = false) String regNo
            ,@RequestParam(required = false) String name) throws Exception{
        if(regNo != null){
            return new ResponseEntity<>(staffBO.countStaffByRegNo(regNo),HttpStatus.OK);
        }
        if(name != null){
            return new ResponseEntity<>(staffBO.countStaffByName(name),HttpStatus.OK);
        }
        return new ResponseEntity<>(staffBO.countAllStaffs(status),HttpStatus.OK);
    }

    @GetMapping(value = "/count/between")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllTodayAddedStaffs(@RequestParam(value = "startDate") long startDate
            ,@RequestParam(value = "endDate") long endDate) throws Exception{

        Date start = new Date(startDate);
        Date end = new Date(endDate);
        return new ResponseEntity<>(staffBO.countAllStaffsBetweenCreatedAtDates(start,end),HttpStatus.OK);
    }

    @GetMapping(value = "/validate/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> validateStaffRegNo(@PathVariable String id) throws Exception{
        int staffCount = staffBO.countStaffByRegNo(id);
        int userCount = userBO.countUserByUsername(id);
        if(staffCount == 0 && userCount == 0){
            return new ResponseEntity<>(0,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(1,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/search")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> searchStaffByRegNo(@RequestParam(required = false) String regNo
            ,@RequestParam(required = false) String name, Pageable page) throws Exception{
        if(regNo != null){
            return new ResponseEntity<>(staffBO.searchStaffByRegNo(regNo,page),HttpStatus.OK);
        }
        return new ResponseEntity<>(staffBO.searchStaffByName(name,page),HttpStatus.OK);
    }
}
