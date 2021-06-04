package lk.ins.library.business.custom.impl;

import lk.ins.library.business.custom.BookBO;
import lk.ins.library.business.util.BookEntityDTOMapper;
import lk.ins.library.business.util.EntityDTOMapper;
import lk.ins.library.dao.BookDAO;
import lk.ins.library.dao.BookDisposalDAO;
import lk.ins.library.dao.BookIssueDAO;
import lk.ins.library.dao.BookReferenceDAO;
import lk.ins.library.dto.BookDTO;
import lk.ins.library.dto.CategoryDTO;
import lk.ins.library.dto.custom.BookCustomDTO;
import lk.ins.library.entity.Book;
import lk.ins.library.entity.Category;
import lk.ins.library.entity.custom.BookCustomEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-24
 **/
@Service
@Transactional
public class BookBOImpl implements BookBO {

    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private BookEntityDTOMapper mapper;
    @Autowired
    private EntityDTOMapper entityDTOMapper;
    @Autowired
    private BookDisposalDAO bookDisposalDAO;
    @Autowired
    private BookIssueDAO bookIssueDAO;
    @Autowired
    private BookReferenceDAO bookReferenceDAO;

    @Override
    public void saveBook(BookDTO bookDTO) throws Exception {
        Book book = mapper.getBook(bookDTO);
        book.setCreatedAt(new Date(System.currentTimeMillis()));
        bookDAO.save(book);
    }

    @Override
    public void updateBook(BookDTO dto) throws Exception {
        Book savedBook = bookDAO.getOne(dto.getBookId());
        Book book = mapper.getBook(dto);
        book.setCreatedAt(savedBook.getCreatedAt());
        bookDAO.save(book);
    }

    @Override
    public void deleteBook(String id) throws Exception {
        bookDAO.deleteById(id);
    }

    @Override
    public List<BookDTO> findAllBooks(Pageable page) throws Exception {
        return bookDAO.findAll(page).stream().map(book -> mapper.getBookDTO(book)).collect(Collectors.toList());
    }

    @Override
    public BookDTO findBook(String id) throws Exception {
        return bookDAO.findById(id).map(book -> mapper.getBookDTO(book)).get();
    }

    @Override
    public void updateBookImage(String id, String imagePath) throws Exception {
        bookDAO.updateBookImage(id,imagePath);
    }

    @Override
    public void deleteBookImage(String id) throws Exception {
        bookDAO.deleteBookImage(id);
    }

    @Override
    public List<BookCustomDTO> getBooksByStatus(boolean status,Pageable page) throws Exception {
        List<BookCustomDTO> collect = bookDAO.findAllBooks(page).stream().map(customEntity -> mapper.getBookCustomDTO(customEntity))
                .collect(Collectors.toList());
        ArrayList<BookCustomDTO> disposaledDTOList = new ArrayList<>();
        for (BookCustomDTO bookCustomDTO : collect) {
            List<CategoryDTO> allCategories = bookDAO.findAllCategories(bookCustomDTO.getBookId()).stream()
                    .map(category -> entityDTOMapper.getCategoryDTO(category)).collect(Collectors.toList());
            bookCustomDTO.setCategories(allCategories);
            if(bookDisposalDAO.countBookDisposalByRefNo(bookCustomDTO.getRefNo())>0){
                disposaledDTOList.add(bookCustomDTO);
            }
            if (bookIssueDAO.isAlreadyIssued(bookCustomDTO.getRefNo())>0){
                bookCustomDTO.setAvailable(false);
            }else {
                bookCustomDTO.setAvailable(true);
            }
        }
        if(status == true){
            for (BookCustomDTO bookCustomDTO : disposaledDTOList) {
                collect.remove(bookCustomDTO);
            }
            return collect;
        }else {
            return disposaledDTOList;
        }
    }

    @Override
    public List<BookCustomDTO> getBookByRefNo(String refNo, Pageable page) throws Exception {
        List<BookCustomDTO> collect = bookDAO.findAllByRefNo(refNo,page).stream().map(customEntity -> mapper.getBookCustomDTO(customEntity))
                .collect(Collectors.toList());
        ArrayList<BookCustomDTO> disposaledDTOList = new ArrayList<>();
        for (BookCustomDTO bookCustomDTO : collect) {
            List<CategoryDTO> allCategories = bookDAO.findAllCategories(bookCustomDTO.getBookId()).stream()
                    .map(category -> entityDTOMapper.getCategoryDTO(category)).collect(Collectors.toList());
            bookCustomDTO.setCategories(allCategories);
            if(bookDisposalDAO.countBookDisposalByRefNo(bookCustomDTO.getRefNo())>0){
                disposaledDTOList.add(bookCustomDTO);
            }
            if (bookIssueDAO.isAlreadyIssued(bookCustomDTO.getRefNo())>0){
                bookCustomDTO.setAvailable(false);
            }else {
                bookCustomDTO.setAvailable(true);
            }
        }
        for (BookCustomDTO bookCustomDTO : disposaledDTOList) {
            collect.remove(bookCustomDTO);
        }
        return collect;
    }

    @Override
    public List<BookCustomDTO> getBookByName(String name, Pageable page) throws Exception {
        List<BookCustomDTO> collect = bookDAO.findAllByName(name,page).stream().map(customEntity -> mapper.getBookCustomDTO(customEntity))
                .collect(Collectors.toList());
        ArrayList<BookCustomDTO> disposaledDTOList = new ArrayList<>();
        for (BookCustomDTO bookCustomDTO : collect) {
            List<CategoryDTO> allCategories = bookDAO.findAllCategories(bookCustomDTO.getBookId()).stream()
                    .map(category -> entityDTOMapper.getCategoryDTO(category)).collect(Collectors.toList());
            bookCustomDTO.setCategories(allCategories);
            if(bookDisposalDAO.countBookDisposalByRefNo(bookCustomDTO.getRefNo())>0){
                disposaledDTOList.add(bookCustomDTO);
            }
            if (bookIssueDAO.isAlreadyIssued(bookCustomDTO.getRefNo())>0){
                bookCustomDTO.setAvailable(false);
            }else {
                bookCustomDTO.setAvailable(true);
            }
        }
        for (BookCustomDTO bookCustomDTO : disposaledDTOList) {
            collect.remove(bookCustomDTO);
        }
        return collect;
    }

    @Override
    public List<BookCustomDTO> getBookByAuthor(String name, Pageable page) throws Exception {
        List<BookCustomDTO> collect = bookDAO.findAllByAuthor(name,page).stream().map(customEntity -> mapper.getBookCustomDTO(customEntity))
                .collect(Collectors.toList());
        ArrayList<BookCustomDTO> disposaledDTOList = new ArrayList<>();
        for (BookCustomDTO bookCustomDTO : collect) {
            List<CategoryDTO> allCategories = bookDAO.findAllCategories(bookCustomDTO.getBookId()).stream()
                    .map(category -> entityDTOMapper.getCategoryDTO(category)).collect(Collectors.toList());
            bookCustomDTO.setCategories(allCategories);
            if(bookDisposalDAO.countBookDisposalByRefNo(bookCustomDTO.getRefNo())>0){
                disposaledDTOList.add(bookCustomDTO);
            }
            if (bookIssueDAO.isAlreadyIssued(bookCustomDTO.getRefNo())>0){
                bookCustomDTO.setAvailable(false);
            }else {
                bookCustomDTO.setAvailable(true);
            }
        }
        for (BookCustomDTO bookCustomDTO : disposaledDTOList) {
            collect.remove(bookCustomDTO);
        }
        return collect;
    }

    @Override
    public List<CategoryDTO> getBookCategories(String bookId) throws Exception {
        return bookDAO.findAllCategories(bookId).stream().map(category -> entityDTOMapper.getCategoryDTO(category))
                .collect(Collectors.toList());
    }

    @Override
    public int getAllBookCountByStatus(boolean status) throws Exception {
        if(status == true){
            int allBookReferences = bookReferenceDAO.countAll();
            int allDisposals = bookDisposalDAO.countAll();
            return allBookReferences-allDisposals;
        }else {
            return bookDisposalDAO.countAll();
        }
    }

    @Override
    public int getBookCountByRefname(String refNo) throws Exception {
        return bookDAO.getSearchResultCountByRefNo(refNo);
    }

    @Override
    public int getBookCountByName(String name) throws Exception {
        return bookDAO.getSearchResultCountByName(name);
    }

    @Override
    public int getBookCountByAuthor(String name) throws Exception {
        return bookDAO.getSearchResultCountByAuthor(name);
    }

    @Override
    public int countBookByBookId(String id) throws Exception {
        return bookDAO.countAllByBookId(id);
    }
}
