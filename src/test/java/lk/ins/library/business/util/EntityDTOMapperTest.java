package lk.ins.library.business.util;

import lk.ins.library.dao.StudentDAO;
import lk.ins.library.dto.*;
import lk.ins.library.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-02-24
 **/

@SpringBootTest
class EntityDTOMapperTest {

    /*@Autowired
    private EntityDTOMapper mapper;
    @Autowired
    private StudentDAO studentDAO;

    @Test
    void getAuthor() {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(1);
        authorDTO.setName("Tharanga");
        Author author = mapper.getAuthor(authorDTO);
        assertEquals(author.getId(),1);
    }

    @Test
    public void getAuthorDTO() {
        Author author = new Author();
        author.setId(1);
        author.setName("Tharanga");
        AuthorDTO authorDTO = mapper.getAuthorDTO(author);
        assertEquals(authorDTO.getId(), 1);
    }

    @Test
    public void getCategory() {
        CategoryDTO dto = new CategoryDTO();
        dto.setName("Science");
        Category category = mapper.getCategory(dto);
        assertEquals(category.getName(),"Science");
    }

    @Test
    public void getCategoryDTO() {
        Category category = new Category();
        category.setId(1);
        category.setName("Science");
        CategoryDTO categoryDTO = mapper.getCategoryDTO(category);
        assertEquals(categoryDTO.getId(),1);
    }

    @Test
    public void getGrade() {
        GradeDTO dto = new GradeDTO();
        dto.setId(1);
        dto.setGrade(9);
        dto.setSection(Section.A);
        dto.setYear(2020);
        Grade grade = mapper.getGrade(dto);
        assertEquals(grade.getGradePK().getGrade(),9);
        System.out.println(grade);
    }

    @Test
    public void getGradeDTO() {
        Grade grade = new Grade(1,9,Section.A,2020);
        GradeDTO gradeDTO = mapper.getGradeDTO(grade);
        assertEquals(gradeDTO.getSection(), Section.A);
        System.out.println(gradeDTO);
    }

    @Test
    public void getRack() {
        RackDTO dto = new RackDTO();
        dto.setId(1);
        dto.setRackNo("1");
        dto.setShellNo("A");
        Rack rack = mapper.getRack(dto);
        assertEquals(rack.getRackPK().getShellNo(), "A");
    }

    @Test
    public void getRackDTO() {
        Rack rack = new Rack();
        rack.setId(1);
        rack.setRackPK(new RackPK("1","A"));
        RackDTO rackDTO = mapper.getRackDTO(rack);
        assertEquals(rackDTO.getShellNo(), "A");
    }

    @Test
    public void getStudent() {
        StudentDTO dto = new StudentDTO();
        dto.setRegNo("S001");
        dto.setInitial("KP");
        dto.setFname("Tharanga");
        dto.setLname("Mahavila");
        dto.setGuardianName("Kularathna");
        dto.setStreetNo("01");
        dto.setFirstStreet("Arachchihena");
        dto.setSecondStreet("Thiththagalla");
        dto.setTown("Ahangama");
        dto.setGender(Gender.MALE);
        dto.setContact("0777215112");
        dto.setImage("images/test.jpg");
        Set<GradeDTO> gradeDTOS = new HashSet<>();
        gradeDTOS.add(new GradeDTO(1,9,Section.A,2019));
        gradeDTOS.add(new GradeDTO(2,10,Section.A,2020));
        gradeDTOS.add(new GradeDTO(3,11,Section.A,2021));
        dto.setGrades(gradeDTOS);
        Student student = mapper.getStudent(dto);
        assertEquals(student.getName().getFname(),"Tharanga");
        System.out.println("-------------------------------");
        System.out.println(student);
    }

    @Test
    public void getStudentDTO() {
        Student student = new Student();
        student.setRegNo("S001");
        student.setName(new Name("KP","Tharanga","Mahavila"));
        student.setGuardianName("Kularathna");
        student.setAddress(new Address("01","Arachchihena","Thiththagalla","Ahangama"));
        student.setGender(Gender.MALE);
        student.setContact("0777215112");
        student.setImage("images/test.jpg");
        Set<Grade> grades = new HashSet<>();
        grades.add(new Grade(1,9,Section.A,2019));
        grades.add(new Grade(2,10,Section.A,2020));
        grades.add(new Grade(3,11,Section.A,2021));
        student.setGrades(grades);
        StudentDTO studentDTO = mapper.getStudentDTO(student);
        assertEquals(studentDTO.getFname(),"Tharanga");
        System.out.println("-------------------------------");
        System.out.println(studentDTO);
    }

    @Test
    public void getUser() {
        UserDTO dto = new UserDTO();
        dto.setUsername("S001");
        dto.setPassword("12345");
        dto.setActive(true);
        dto.setRole(Role.STUDENT);
        User user = mapper.getUser(dto);
        System.out.println("------------------------------");
        System.out.println(user);
    }

    @Test
    public void getUserDTO() {
        User user = new User();
        user.setUsername("S001");
        user.setPassword("12345");
        user.setActive(true);
        user.setRole(Role.STUDENT);
        UserDTO userDTO = mapper.getUserDTO(user);
        System.out.println("---------------------------");
        System.out.println(userDTO);
    }

    @Test
    public void getStaff() {
        StaffDTO dto = new StaffDTO();
        dto.setId("199533154962");
        dto.setInitial("KP");
        dto.setFname("Tharanga");
        dto.setLname("Mahavila");
        dto.setContact("0777215112");
        dto.setStreetNo("01");
        dto.setFirstStreet("Arachchihena");
        dto.setSecondStreet("Thiththagalla");
        dto.setTown("Ahangama");
        dto.setGender(Gender.MALE);
        dto.setSalaryNo("12345");
        dto.setActive(true);
        dto.setRole(Role.STAFF);
        dto.setImage("images/test.jpg");
        Staff staff = mapper.getStaff(dto);
        System.out.println("-------------------------------");
        System.out.println(staff);
    }

    @Test
    public void getStaffDTO() {
        Staff staff = new Staff();
        staff.setId("1995331645987");
        staff.setName(new Name("KP","Tharanga","Mahavila"));
        staff.setContact("0777215112");
        staff.setAddress(new Address("01","Arachchihena","Thiththagalla","Ahangama"));
        staff.setGender(Gender.MALE);
        staff.setSalaryNo("12345");
        staff.setImage("images/test.jpg");
        StaffDTO staffDTO = mapper.getStaffDTO(staff);
        assertEquals(staffDTO.getFname(),"Tharanga");
        System.out.println("-------------------------------");
        System.out.println(staffDTO);
    }*/
}
