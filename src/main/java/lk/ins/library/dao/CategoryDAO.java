package lk.ins.library.dao;

import lk.ins.library.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-11
 **/
public interface CategoryDAO extends JpaRepository<Category, Integer>{
}
