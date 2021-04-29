package lk.ins.library.business.custom.impl;

import lk.ins.library.business.custom.BookDisposalBO;
import lk.ins.library.business.util.BookEntityDTOMapper;
import lk.ins.library.business.util.EntityDTOMapper;
import lk.ins.library.dao.BookDisposalDAO;
import lk.ins.library.dto.BookDisposalDTO;
import lk.ins.library.entity.BookDisposal;
import lk.ins.library.entity.BookReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-25
 **/
@Service
@Transactional
public class BookDisposalBOImpl implements BookDisposalBO {

    @Autowired
    private BookDisposalDAO bookDisposalDAO;
    @Autowired
    private EntityDTOMapper mapper;

    @Override
    public void saveBookDisposal(BookDisposalDTO dto) throws Exception {
        BookDisposal bookDisposal = mapper.getBookDisposal(dto);
        bookDisposal.setCreatedAt(new Date(System.currentTimeMillis()));
        bookDisposalDAO.save(bookDisposal);
    }

    @Override
    public void updateBookDisposal(BookDisposalDTO dto) throws Exception {
        BookDisposal savedBookDisposal = bookDisposalDAO.getOne(dto.getRefNo());
        BookDisposal bookDisposal = mapper.getBookDisposal(dto);
        bookDisposal.setCreatedAt(savedBookDisposal.getCreatedAt());
        bookDisposalDAO.save(bookDisposal);
    }

    @Override
    public void deleteBookDisposal(String id) throws Exception {
        bookDisposalDAO.deleteById(id);
    }

    @Override
    public List<BookDisposalDTO> findAllBookDisposals(Pageable page) throws Exception {
        return bookDisposalDAO.findAll(page).stream().map(bookDisposal -> mapper.getBookDisposalDTO(bookDisposal))
                .collect(Collectors.toList());
    }

    @Override
    public BookDisposalDTO findBookDisposal(String id) throws Exception {
        return bookDisposalDAO.findById(id).map(bookDisposal -> mapper.getBookDisposalDTO(bookDisposal)).get();
    }
}
