package lk.ins.library.business.util;

import lk.ins.library.dto.BookDTO;
import lk.ins.library.dto.CategoryDTO;
import lk.ins.library.entity.Book;
import lk.ins.library.entity.Medium;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-26
 **/
@SpringBootTest
public class BookEntityDTOMapperTest {

  /*  @Autowired
    private BookEntityDTOMapper mapper;

    @Test
    public void getBook() {
        BookDTO dto = new BookDTO();
        dto.setBookId("S001");
        dto.setEnglishName("Clean Code");
        dto.setSinhalaName("Sinhala name");
        dto.setYear(1995);
        dto.setPrice(new BigDecimal(1800.25));
        dto.setMedium(Medium.ENGLISH);
        dto.setPages(800);
        dto.setNote("This is a sample note");
        dto.setAuthor(1);
        Set<CategoryDTO> set = new HashSet<>();
        set.add(new CategoryDTO(1,"Science"));
        dto.setCategories(set);
        Book book = mapper.getBook(dto);
        System.out.println("------------------------------");
        System.out.println(book);
        System.out.println("------------------------------");
    }*/
}
