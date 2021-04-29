package lk.ins.library.api;

import lk.ins.library.business.custom.StaffBO;
import lk.ins.library.business.custom.StudentBO;
import lk.ins.library.business.custom.UserBO;
import lk.ins.library.business.util.MyUserDetailsService;
import lk.ins.library.dto.UpdatePasswordDTO;
import lk.ins.library.dto.UserDTO;
import lk.ins.library.entity.Role;
import lk.ins.library.util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-19
 **/
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

    @Autowired
    private UserBO userBO;
    @Autowired
    private StaffBO staffBO;
    @Autowired
    private StudentBO studentBO;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private MyUserDetailsService userDetailsService;

    @PutMapping(value = "/active/{username}/{status}")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> updateUserActiveStatus(@PathVariable String username,@PathVariable boolean status) throws Exception{
        try{
            if(username.equals("admin")){
                return new ResponseEntity<>("You cannot change the admin active status",HttpStatus.FORBIDDEN);
            }
            userBO.findUser(username);
            userBO.updateUserActiveStatus(username,status);
            return new ResponseEntity<>("Successfully changed the user active status",HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No user found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PreAuthorize("hasAnyAuthority('STUDENT','STAFF','ADMIN','OWNER')")
    @PutMapping(value = "/password/{username}",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> updateUserPassword(@Valid @RequestBody UpdatePasswordDTO dto, @PathVariable String username) throws Exception{
        if(!username.equals(dto.getUsername())){
            return new ResponseEntity<>("Mismatched username",HttpStatus.BAD_REQUEST);
        }
        try{
            userBO.findUser(dto.getUsername());
            return userBO.updatePassword(dto);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No User found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PreAuthorize("permitAll()")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getUserFromJWT(@RequestHeader("jwt") String jwt) throws Exception{
        try{
            String username = jwtUtil.extractUsername(jwt);
            UserDetails userDetails = null;
            if(username != null){
                userDetails = this.userDetailsService.loadUserByUsername(username);
            }
            UserDTO dto = new UserDTO();
            dto.setUsername(userDetails.getUsername());
            dto.setActive(userDetails.isEnabled());
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            for (GrantedAuthority grantedAuthority : authorities) {
                dto.setRole(Role.valueOf(grantedAuthority.getAuthority()));
            }
            return new ResponseEntity<>(dto,HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No User found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PreAuthorize("hasAuthority('STAFF')")
    @GetMapping(value = "/staffUser",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getStaffFromJWT(@RequestHeader("jwt") String jwt) throws Exception{
        try{
            String username = jwtUtil.extractUsername(jwt);
            return new ResponseEntity<>(staffBO.findStaff(username),HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No User found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/adminUser",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAdminFromJWT(@RequestHeader("jwt") String jwt) throws Exception{
        try{
            String username = jwtUtil.extractUsername(jwt);
            return new ResponseEntity<>(staffBO.findStaff(username),HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No User found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping(value = "/studentUser",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getStudentFromJWT(@RequestHeader("jwt") String jwt) throws Exception{
        try{
            String username = jwtUtil.extractUsername(jwt);
            return new ResponseEntity<>(studentBO.findStudent(username),HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>("No User found",HttpStatus.NOT_FOUND);
        }catch (Throwable e){
            throw  new Error(e);
        }
    }
}
