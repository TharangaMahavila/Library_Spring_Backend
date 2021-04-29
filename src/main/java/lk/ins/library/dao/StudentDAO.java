package lk.ins.library.dao;

import lk.ins.library.entity.Active;
import lk.ins.library.entity.Student;
import lk.ins.library.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-15
 **/
public interface StudentDAO extends JpaRepository<Student, String> {

    @Transactional
    @Modifying
    @Query("update Student set image=?2 where regNo=?1")
    void updateStudentImage(String regNo, String imagePath);

    @Transactional
    @Modifying
    @Query("update Student set image=null where regNo=?1")
    void deleteStudentImage(String regNo);

    @Query("from Student s where s.regNo in ?1")
    List<Student> getAllByActiveStatus(List<String> regNumbers, Pageable page);

    @Query("from Student s where s.regNo like ?1%")
    List<Student> getAllByRegNo(String regNo, Pageable page);

    @Query("from Student s where s.name.initial like ?1% or s.name.fname like ?1% or s.name.lname like ?1%")
    List<Student> getAllByName(String name, Pageable page);

    @Query("select count(s) from Student s where s.regNo like ?1%")
    int countAllByRegNo(String regNo);

    @Query("select count(s) from Student s where s.name.initial like ?1% or s.name.fname like ?1% or s.name.lname like ?1%")
    int countAllByName(String regNo);

    @Query("select count(s) from Student s where s.createdAt between ?1 and ?2")
    int countAllStudentsBetweenCreatedAtDates(Date startDate, Date endDate);
}
