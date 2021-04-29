package lk.ins.library.dao;

import lk.ins.library.entity.Active;
import lk.ins.library.entity.Student;
import lk.ins.library.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-19
 **/
public interface UserDAO extends JpaRepository<User,String> {

    @Query("from User u where u.isActive in ?1 and u.role = 3")
    List<User> findStudentByActive(boolean status);

    @Query("from User u where u.isActive in ?1 and u.role = 2")
    List<User> findStaffByActive(boolean status);

    @Transactional
    @Modifying
    @Query("update User u set u.isActive=?2 where u.username=?1")
    void updateUserActiveStatus(String username,boolean status);

    @Query("select count(u) from User u where u.isActive=?1 and u.role=3")
    int countAllStudentsByActiveStatus(boolean status);

    @Query("select count(u) from User u where u.isActive=?1 and u.role=2")
    int countAllStaffsByActiveStatus(boolean status);

    int countUsersByUsername(String username);
}
