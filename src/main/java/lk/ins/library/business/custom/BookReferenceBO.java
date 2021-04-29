package lk.ins.library.business.custom;

import lk.ins.library.business.SuperBO;
import lk.ins.library.dto.BookReferenceDTO;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-25
 **/
public interface BookReferenceBO extends SuperBO {

    public void saveBookReference(BookReferenceDTO bookReferenceDTO) throws Exception;
    public void updateBookReference(BookReferenceDTO bookReferenceDTO) throws Exception;
    public void deleteBookReference(String id) throws Exception;
    public List<BookReferenceDTO> findAllBookReferences(Pageable page) throws Exception;
    public BookReferenceDTO findBookReference(String id) throws Exception;

    public int countBooksBetweenPeriod(Date startDate, Date endDate) throws Exception;
}
