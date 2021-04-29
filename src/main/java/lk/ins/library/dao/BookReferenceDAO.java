package lk.ins.library.dao;

import lk.ins.library.entity.BookReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-25
 **/
public interface BookReferenceDAO extends JpaRepository<BookReference, String> {

    @Query("select count(br) from BookReference br where br.refNo = ?1 and br.isReference=true")
    int isOnlyReferenceBook(String refId);

    @Query("select count(BookReference) from BookReference")
    int countAll();

    @Query("select count(br) from BookReference br where br.createdAt between ?1 and ?2")
    int countAllBooksBetweenCreatedAtDates(Date startDate, Date endDate);
}
