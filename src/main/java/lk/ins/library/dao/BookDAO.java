package lk.ins.library.dao;

import lk.ins.library.entity.Book;
import lk.ins.library.entity.Category;
import lk.ins.library.entity.custom.BookCustomEntity;
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
public interface BookDAO extends JpaRepository<Book, String> {

    @Transactional
    @Modifying
    @Query("update Book set image=?2 where id=?1")
    void updateBookImage(String id, String imagePath);

    @Query("select new lk.ins.library.entity.custom.BookCustomEntity" +
            "(br.refNo,b.bookId,br.barcode,b.englishName,b.sinhalaName,b.year,b.price,b.medium,b.pages,b.note,b.image,b.author" +
            ",br.isReference,br.supplier,br.rack)" +
            " from Book b join b.bookReferences br " +
            "where b.englishName like %?1% or b.sinhalaName like %?1%")
    List<BookCustomEntity> findAllByName(String searchKeyword, Pageable page);

    @Query("select new lk.ins.library.entity.custom.BookCustomEntity" +
            "(br.refNo,b.bookId,br.barcode,b.englishName,b.sinhalaName,b.year,b.price,b.medium,b.pages,b.note,b.image,b.author" +
            ",br.isReference,br.supplier,br.rack)" +
            " from Book b join b.bookReferences br " +
            "where b.author.name like %?1%")
    List<BookCustomEntity> findAllByAuthor(String searchKeyword, Pageable page);

    @Query("select count(b) from Book b join b.bookReferences br where b.englishName like %?1% or b.sinhalaName like %?1%")
    int getSearchResultCountByName(String name);

    @Query("select count(b) from Book b join b.bookReferences br where b.author.name like %?1%")
    int getSearchResultCountByAuthor(String name);

    @Query("from Category c join c.books b where b.id = ?1")
    List<Category> findAllCategories(String bookId);

}
