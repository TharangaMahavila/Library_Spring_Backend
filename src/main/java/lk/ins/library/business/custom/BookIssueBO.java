package lk.ins.library.business.custom;

import lk.ins.library.business.SuperBO;
import lk.ins.library.dto.BookIssueDTO;
import lk.ins.library.entity.BookIssuePK;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-25
 **/
public interface BookIssueBO extends SuperBO {
    
    public void saveBookIssue(BookIssueDTO dto) throws Exception;
    public void updateBookIssue(BookIssueDTO dto) throws Exception;
    public void deleteBookIssue(BookIssuePK id) throws Exception;
    public List<BookIssueDTO> findAllBookIssues(Pageable page) throws Exception;
    public BookIssueDTO findBookIssue(BookIssuePK id) throws Exception;
}
