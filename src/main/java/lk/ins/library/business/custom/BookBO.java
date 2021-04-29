package lk.ins.library.business.custom;

import lk.ins.library.business.SuperBO;
import lk.ins.library.dto.BookDTO;
import lk.ins.library.dto.CategoryDTO;
import lk.ins.library.dto.UserDTO;
import lk.ins.library.dto.custom.BookCustomDTO;
import lk.ins.library.entity.Book;
import lk.ins.library.entity.custom.BookCustomEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-24
 **/
public interface BookBO extends SuperBO{

    public void saveBook(BookDTO bookDTO) throws Exception;
    public void updateBook(BookDTO dto) throws Exception;
    public void deleteBook(String id) throws Exception;
    public List<BookDTO> findAllBooks(Pageable page) throws Exception;
    public BookDTO findBook(String id) throws Exception;

    public void updateBookImage(String id, String imagePath) throws Exception;

    public List<BookCustomDTO> getBookByName(String name, Pageable page) throws Exception;
    public List<BookCustomDTO> getBookByAuthor(String name, Pageable page) throws Exception;
    public List<CategoryDTO> getBookCategories(String bookId) throws Exception;

    public int getAllBookCountByStatus(boolean status) throws Exception;
    public int getBookCountByName(String name) throws Exception;
    public int getBookCountByAuthor(String name) throws Exception;
}
