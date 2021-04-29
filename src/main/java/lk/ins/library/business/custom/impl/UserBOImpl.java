package lk.ins.library.business.custom.impl;

import lk.ins.library.business.custom.StudentBO;
import lk.ins.library.business.custom.UserBO;
import lk.ins.library.business.util.EntityDTOMapper;
import lk.ins.library.dao.StudentDAO;
import lk.ins.library.dao.UserDAO;
import lk.ins.library.dto.StudentDTO;
import lk.ins.library.dto.UpdatePasswordDTO;
import lk.ins.library.dto.UserDTO;
import lk.ins.library.entity.Student;
import lk.ins.library.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-19
 **/
@Transactional
@Service
public class UserBOImpl implements UserBO {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EntityDTOMapper mapper;

    @Override
    public void saveUser(UserDTO dto) throws Exception {
        User user = mapper.getUser(dto);
        user.setCreatedAt(new Date(System.currentTimeMillis()));
        userDAO.save(user);
    }

    @Override
    public void updateUser(UserDTO dto) throws Exception {
        User savedUser = userDAO.getOne(dto.getUsername());
        User user = mapper.getUser(dto);
        user.setCreatedAt(savedUser.getCreatedAt());
        userDAO.save(user);
    }

    @Override
    public void deleteUser(String username) throws Exception {
        userDAO.deleteById(username);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDTO> findAllUsers() throws Exception {
        return userDAO.findAll().stream().map(user -> mapper.getUserDTO(user)).collect(Collectors.toList());
    }

    @Override
    public UserDTO findUser(String username) throws Exception {
        UserDTO userDTO = userDAO.findById(username).map(user -> mapper.getUserDTO(user)).get();
        userDTO.setPassword(null);
        return userDTO;
    }

    @Override
    public ResponseEntity<String> updatePassword(UpdatePasswordDTO dto) throws Exception {
        User user = userDAO.findById(dto.getUsername()).get();
        if(!passwordEncoder.matches(dto.getCurrentPassword(),user.getPassword())){
            return new ResponseEntity<>("Unauthorized access", HttpStatus.UNAUTHORIZED);
        }
        if(!dto.getNewPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")){
            return new ResponseEntity<>("Password must contain at least one lowercase,uppercase,number ans symbol", HttpStatus.BAD_REQUEST);
        }
        String password = passwordEncoder.encode(dto.getNewPassword());
        user.setPassword(password);
        userDAO.save(user);
        return new ResponseEntity<>("Successfully updated the password", HttpStatus.CREATED);
    }

    @Override
    public void updateUserActiveStatus(String username,boolean status) throws Exception {
        userDAO.updateUserActiveStatus(username,status);
    }

    @Override
    public int countUserByUsername(String username) throws Exception {
        return userDAO.countUsersByUsername(username);
    }
}
