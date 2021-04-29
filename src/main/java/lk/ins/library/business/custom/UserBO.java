package lk.ins.library.business.custom;

import lk.ins.library.business.SuperBO;
import lk.ins.library.dto.StudentDTO;
import lk.ins.library.dto.UpdatePasswordDTO;
import lk.ins.library.dto.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-19
 **/
public interface UserBO extends SuperBO {
    public void saveUser(UserDTO dto) throws Exception;
    public void updateUser(UserDTO dto) throws Exception;
    public void deleteUser(String username) throws Exception;
    public List<UserDTO> findAllUsers() throws Exception;
    public UserDTO findUser(String username) throws Exception;

    public ResponseEntity<String> updatePassword(UpdatePasswordDTO dto) throws Exception;
    public void updateUserActiveStatus(String username,boolean status) throws Exception;

    public int countUserByUsername(String username) throws Exception;
}
