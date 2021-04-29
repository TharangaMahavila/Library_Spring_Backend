package lk.ins.library.business.custom;

import lk.ins.library.business.SuperBO;
import lk.ins.library.dto.StudentDTO;
import lk.ins.library.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-15
 **/
public interface StudentBO extends SuperBO {

    public void saveStudent(StudentDTO studentDTO,UserDTO userDTO) throws Exception;
    public void updateStudent(StudentDTO dto,UserDTO userDTO) throws Exception;
    public void deleteStudent(String regNo) throws Exception;
    public List<StudentDTO> findAllStudents(Pageable page) throws Exception;
    public List<StudentDTO> findAllStudentsByActive(boolean status, Pageable page) throws Exception;
    public StudentDTO findStudent(String regNo) throws Exception;

    public void updateStudentImage(String regNo, String imagePath) throws Exception;
    public void deleteStudentImage(String regNo) throws Exception;

    public long countAllStudents(boolean status) throws Exception;
    public int countStudentBetweenPeriod(Date startDate, Date endDate) throws Exception;

    public int countStudentByRegNo(String regNo) throws Exception;
    public int countStudentByName(String name) throws Exception;

    public List<StudentDTO> searchStudentByRegNo(String regNo, Pageable page) throws Exception;
    public List<StudentDTO> searchStudentByName(String name, Pageable page) throws Exception;
}
