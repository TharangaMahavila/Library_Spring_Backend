package lk.ins.library.business.custom;

import lk.ins.library.business.SuperBO;
import lk.ins.library.dto.BookDisposalDTO;
import lk.ins.library.entity.BookReference;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-25
 **/
public interface BookDisposalBO extends SuperBO {

    public void saveBookDisposal(BookDisposalDTO dto) throws Exception;
    public void updateBookDisposal(BookDisposalDTO dto) throws Exception;
    public void deleteBookDisposal(String id) throws Exception;
    public List<BookDisposalDTO> findAllBookDisposals(Pageable page) throws Exception;
    public BookDisposalDTO findBookDisposal(String id) throws Exception;
}
