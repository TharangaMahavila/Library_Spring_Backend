package lk.ins.library.dao;

import lk.ins.library.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-15
 **/
public interface GradeDAO extends JpaRepository<Grade, Integer> {
}
