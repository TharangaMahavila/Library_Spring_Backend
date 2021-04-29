package lk.ins.library.business.custom.impl;

import lk.ins.library.business.custom.BookIssueBO;
import lk.ins.library.business.util.BookEntityDTOMapper;
import lk.ins.library.business.util.EntityDTOMapper;
import lk.ins.library.dao.BookDisposalDAO;
import lk.ins.library.dao.BookIssueDAO;
import lk.ins.library.dao.BookReferenceDAO;
import lk.ins.library.dao.StudentDAO;
import lk.ins.library.dto.BookIssueDTO;
import lk.ins.library.entity.BookIssue;
import lk.ins.library.entity.BookIssuePK;
import lk.ins.library.entity.BookReference;
import lk.ins.library.entity.Student;
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
public class BookIssueBOImpl implements BookIssueBO {

    @Autowired
    private BookIssueDAO bookIssueDAO;
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private BookReferenceDAO bookReferenceDAO;
    @Autowired
    private BookEntityDTOMapper mapper;

    @Override
    public void saveBookIssue(BookIssueDTO dto) throws Exception {
        BookIssue bookIssue = mapper.getBookIssue(dto);
        bookIssue.setCreatedAt(new Date(System.currentTimeMillis()));
        bookIssueDAO.save(bookIssue);
    }

    @Override
    public void updateBookIssue(BookIssueDTO dto) throws Exception {
        Student student = studentDAO.getOne(dto.getStudentId());
        BookReference bookReference = bookReferenceDAO.getOne(dto.getBookRefId());
        BookIssue savedBookIssue = bookIssueDAO.getOne(new BookIssuePK(student, bookReference, dto.getIssueDate()));
        BookIssue bookIssue = mapper.getBookIssue(dto);
        bookIssue.setCreatedAt(savedBookIssue.getCreatedAt());
        bookIssueDAO.save(bookIssue);
    }

    @Override
    public void deleteBookIssue(BookIssuePK id) throws Exception {
        bookIssueDAO.deleteById(id);
    }

    @Override
    public List<BookIssueDTO> findAllBookIssues(Pageable page) throws Exception {
        return bookIssueDAO.findAll(page).stream().map(bookIssue -> mapper.getBookIssueDTO(bookIssue)).collect(Collectors.toList());
    }

    @Override
    public BookIssueDTO findBookIssue(BookIssuePK id) throws Exception {
        return bookIssueDAO.findById(id).map(bookIssue -> mapper.getBookIssueDTO(bookIssue)).get();
    }
}
