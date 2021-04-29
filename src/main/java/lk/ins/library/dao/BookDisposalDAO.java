package lk.ins.library.dao;

import lk.ins.library.entity.BookDisposal;
import lk.ins.library.entity.BookReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-25
 **/
public interface BookDisposalDAO extends JpaRepository<BookDisposal,String>{

    int countBookDisposalByRefNo(String refNo);

    @Query("select count(BookDisposal) from BookDisposal")
    int countAll();
}
