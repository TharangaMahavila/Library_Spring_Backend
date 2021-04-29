package lk.ins.library.dao;

import lk.ins.library.entity.Staff;
import lk.ins.library.entity.Student;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-24
 **/
public interface StaffDAO extends JpaRepository<Staff, String> {

    @Transactional
    @Modifying
    @Query("update Staff set image=?2 where id=?1")
    void updateStaffImage(String id, String imagePath);

    @Transactional
    @Modifying
    @Query("update Staff set image=null where id=?1")
    void deleteStaffImage(String regNo);

    @Query("from Staff s where s.id in ?1")
    List<Staff> getAllByActiveStatus(List<String> regNumbers, Pageable page);

    @Query("from Staff s where s.id like ?1%")
    List<Staff> getAllByRegNo(String regNo, Pageable page);

    @Query("from Staff s where s.name.initial like ?1% or s.name.fname like ?1% or s.name.lname like ?1%")
    List<Staff> getAllByName(String name, Pageable page);

    @Query("select count(s) from Staff s where s.id like ?1%")
    int countAllByRegNo(String regNo);

    @Query("select count(s) from Staff s where s.name.initial like ?1% or s.name.fname like ?1% or s.name.lname like ?1%")
    int countAllByName(String regNo);

    @Query("select count(s) from Staff s where s.createdAt between ?1 and ?2")
    int countAllStaffsBetweenCreatedAtDates(Date startDate, Date endDate);
}
