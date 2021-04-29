package lk.ins.library.business.custom.impl;

import lk.ins.library.business.custom.BookBO;
import lk.ins.library.business.custom.BookReferenceBO;
import lk.ins.library.business.util.BookReferenceEntityDTOMapper;
import lk.ins.library.business.util.EntityDTOMapper;
import lk.ins.library.dao.BookReferenceDAO;
import lk.ins.library.dto.BookDTO;
import lk.ins.library.dto.BookReferenceDTO;
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
public class BookReferenceImpl implements BookReferenceBO {

    @Autowired
    private BookReferenceDAO bookReferenceDAO;
    @Autowired
    private BookReferenceEntityDTOMapper mapper;

    @Override
    public void saveBookReference(BookReferenceDTO bookReferenceDTO) throws Exception {
        BookReference bookReference = mapper.getBookReference(bookReferenceDTO);
        bookReference.setCreatedAt(new Date(System.currentTimeMillis()));
        bookReferenceDAO.save(bookReference);
    }

    @Override
    public void updateBookReference(BookReferenceDTO bookReferenceDTO) throws Exception {
        BookReference savedBookReference = bookReferenceDAO.getOne(bookReferenceDTO.getRefNo());
        BookReference bookReference = mapper.getBookReference(bookReferenceDTO);
        bookReference.setCreatedAt(savedBookReference.getCreatedAt());
        bookReferenceDAO.save(bookReference);
    }

    @Override
    public void deleteBookReference(String id) throws Exception {
        bookReferenceDAO.deleteById(id);
    }

    @Override
    public List<BookReferenceDTO> findAllBookReferences(Pageable page) throws Exception {
        return bookReferenceDAO.findAll(page).stream().map(bookReference -> mapper.getBookReferenceDTO(bookReference))
                .collect(Collectors.toList());
    }

    @Override
    public BookReferenceDTO findBookReference(String id) throws Exception {
        return bookReferenceDAO.findById(id).map(bookReference -> mapper.getBookReferenceDTO(bookReference)).get();
    }

    @Override
    public int countBooksBetweenPeriod(Date startDate, Date endDate) throws Exception {
        return bookReferenceDAO.countAllBooksBetweenCreatedAtDates(startDate, endDate);
    }
}
