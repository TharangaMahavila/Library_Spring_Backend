package lk.ins.library.dao;

import lk.ins.library.entity.BookIssue;
import lk.ins.library.entity.BookIssuePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-25
 **/
public interface BookIssueDAO extends JpaRepository<BookIssue, BookIssuePK> {

    @Query("select count(bi) from BookIssue bi where bi.bookIssuePK.bookReference.refNo = ?1 and bi.submittedDate is null")
    int isAlreadyIssued(String refNo);
}
