package lk.ins.library.dao;

import lk.ins.library.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-24
 **/
public interface SupplierDAO extends JpaRepository<Supplier, Integer> {
}
