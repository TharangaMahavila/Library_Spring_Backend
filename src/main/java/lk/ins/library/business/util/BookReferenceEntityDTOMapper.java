package lk.ins.library.business.util;

import lk.ins.library.dao.BookDAO;
import lk.ins.library.dao.RackDAO;
import lk.ins.library.dao.SupplierDAO;
import lk.ins.library.dto.BookReferenceDTO;
import lk.ins.library.entity.Book;
import lk.ins.library.entity.BookReference;
import lk.ins.library.entity.Rack;
import lk.ins.library.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-26
 **/
@Mapper(componentModel = "spring")
public abstract class BookReferenceEntityDTOMapper {

    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private SupplierDAO supplierDAO;
    @Autowired
    private RackDAO rackDAO;

    @Mapping(source = ".",target = "book")
    @Mapping(source = ".",target = "supplier")
    @Mapping(source = ".",target = "rack")
    public abstract BookReference getBookReference(BookReferenceDTO dto);

    @Mapping(source = "book",target = "bookId",qualifiedByName = "Book")
    @Mapping(source = "supplier",target = "supplierId",qualifiedByName = "Supplier")
    @Mapping(source = "rack",target = "rackId",qualifiedByName = "Rack")
    public abstract BookReferenceDTO getBookReferenceDTO(BookReference bookReference);

    public Book getBook(BookReferenceDTO dto){
        return bookDAO.getOne(dto.getBookId());
    }
    public Supplier getSupplier(BookReferenceDTO dto){
        return supplierDAO.getOne(dto.getSupplierId());
    }
    public Rack getRack(BookReferenceDTO dto){
        return rackDAO.getOne(dto.getRackId());
    }

    @Named("Book")
    public String BookId(Book book){
        return book.getBookId();
    }
    @Named("Supplier")
    public int SupplierId(Supplier supplier){
        return supplier.getId();
    }
    @Named("Rack")
    public int RackId(Rack rack){
        return rack.getId();
    }
}
