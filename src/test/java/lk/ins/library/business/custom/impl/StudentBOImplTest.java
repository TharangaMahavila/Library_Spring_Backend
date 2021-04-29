package lk.ins.library.business.custom.impl;

import lk.ins.library.dao.StudentDAO;
import lk.ins.library.dto.StudentDTO;
import lk.ins.library.dto.UserDTO;
import lk.ins.library.entity.Active;
import lk.ins.library.entity.Student;
import lk.ins.library.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-19
 **/
@SpringBootTest
public class StudentBOImplTest {

   /* @Autowired
    StudentBOImpl studentBO;

    @Test
    public void findAllActiveStudent(Pageable page) throws Exception {
        List<StudentDTO> students = studentBO.findAllStudentsByActive(true, page);
        System.out.println("----------------------------------");
        for (StudentDTO student : students) {
            System.out.println(student);
        }
        System.out.println("----------------------------------");
    }*/
}
