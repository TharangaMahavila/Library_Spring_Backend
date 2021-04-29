package lk.ins.library.business.custom.impl;

import lk.ins.library.business.custom.StudentBO;
import lk.ins.library.business.util.EntityDTOMapper;
import lk.ins.library.dao.StudentDAO;
import lk.ins.library.dao.UserDAO;
import lk.ins.library.dto.StudentDTO;
import lk.ins.library.dto.UserDTO;
import lk.ins.library.entity.Role;
import lk.ins.library.entity.Student;
import lk.ins.library.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-16
 **/
@Service
@Transactional
public class StudentBOImpl implements StudentBO {

    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private EntityDTOMapper mapper;

    @Transactional
    @Override
    public void saveStudent(StudentDTO studentDTO,UserDTO userDTO) throws Exception {
        Student student = mapper.getStudent(studentDTO);
        student.setCreatedAt(new Date(System.currentTimeMillis()));
        studentDAO.save(student);

        User user = mapper.getUser(userDTO);
        user.setCreatedAt(new Date(System.currentTimeMillis()));
        userDAO.save(user);
    }

    @Transactional
    @Override
    public void updateStudent(StudentDTO studentDTO,UserDTO userDTO) throws Exception {
        Student savedStudent = studentDAO.getOne(studentDTO.getRegNo());
        Student student = mapper.getStudent(studentDTO);
        student.setCreatedAt(savedStudent.getCreatedAt());
        studentDAO.save(student);

        User savedUser = userDAO.getOne(userDTO.getUsername());
        User user = mapper.getUser(userDTO);
        user.setCreatedAt(savedUser.getCreatedAt());
        user.setPassword(savedUser.getPassword());
        userDAO.save(user);
    }

    @Transactional
    @Override
    public void deleteStudent(String regNo) throws Exception {
        studentDAO.deleteById(regNo);
        userDAO.deleteById(regNo);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentDTO> findAllStudents(Pageable page) throws Exception {
        List<StudentDTO> studentDTOS = studentDAO.findAll(page).stream().map(student -> mapper.getStudentDTO(student)).collect(Collectors.toList());
        for (StudentDTO studentDTO : studentDTOS) {
            User studentuser = userDAO.getOne(studentDTO.getRegNo());
            studentDTO.setActive(studentuser.isActive());
            studentDTO.setRole(studentuser.getRole());
        }
        return studentDTOS;
    }

    @Transactional(readOnly = true)
    @Override
    public List<StudentDTO> findAllStudentsByActive(boolean status, Pageable page) throws Exception {
        List<User> users = userDAO.findStudentByActive(status).stream().collect(Collectors.toList());
        List<String> regNumbers = new ArrayList<>();
        for (User user : users) {
            regNumbers.add(user.getUsername());
        }
        List<StudentDTO> studentDTOS = studentDAO.getAllByActiveStatus(regNumbers, page).stream().map(student -> mapper.getStudentDTO(student)).collect(Collectors.toList());
        for (StudentDTO studentDTO : studentDTOS) {
            studentDTO.setActive(status);
            studentDTO.setRole(Role.STUDENT);
        }
        return studentDTOS;
    }

    @Override
    public StudentDTO findStudent(String regNo) throws Exception {
        StudentDTO studentDTO = studentDAO.findById(regNo).map(student -> mapper.getStudentDTO(student)).get();
        User studentuser = userDAO.getOne(regNo);
        studentDTO.setRole(studentuser.getRole());
        studentDTO.setActive(studentuser.isActive());
        return studentDTO;
    }

    @Override
    public void updateStudentImage(String regNo, String imagePath) throws Exception {
        studentDAO.updateStudentImage(regNo,imagePath);
    }

    @Override
    public void deleteStudentImage(String regNo) throws Exception {
        studentDAO.deleteStudentImage(regNo);
    }

    @Override
    public long countAllStudents(boolean status) throws Exception {
        return userDAO.countAllStudentsByActiveStatus(status);
    }

    @Override
    public int countStudentBetweenPeriod(Date start, Date end) throws Exception {

        return studentDAO.countAllStudentsBetweenCreatedAtDates(start,end);
    }

    @Override
    public int countStudentByRegNo(String regNo) throws Exception {
        return studentDAO.countAllByRegNo(regNo);
    }

    @Override
    public int countStudentByName(String name) throws Exception {
        return studentDAO.countAllByName(name);
    }

    @Override
    public List<StudentDTO> searchStudentByRegNo(String regNo, Pageable page) throws Exception {
        return studentDAO.getAllByRegNo(regNo,page).stream().map(student -> mapper.getStudentDTO(student)).collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> searchStudentByName(String name, Pageable page) throws Exception {
        return studentDAO.getAllByName(name,page).stream().map(student -> mapper.getStudentDTO(student)).collect(Collectors.toList());
    }
}
