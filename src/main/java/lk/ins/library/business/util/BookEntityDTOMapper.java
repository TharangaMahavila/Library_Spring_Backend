package lk.ins.library.business.util;

import lk.ins.library.dao.AuthorDAO;
import lk.ins.library.dao.BookReferenceDAO;
import lk.ins.library.dao.StudentDAO;
import lk.ins.library.dao.UserDAO;
import lk.ins.library.dto.*;
import lk.ins.library.dto.custom.BookCustomDTO;
import lk.ins.library.entity.*;
import lk.ins.library.entity.custom.BookCustomEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-26
 **/
@Mapper(componentModel = "spring",uses = {EntityDTOMapper.class})
public abstract class BookEntityDTOMapper {

    @Autowired
    private AuthorDAO authorDAO;
    @Autowired
    private BookReferenceDAO bookReferenceDAO;
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private UserDAO userDAO;

    @Mapping(source = ".",target = "author")
    public abstract Book getBook(BookDTO dto);

    @Mapping(source = "author",target = "author",qualifiedByName = "Author")
    public abstract BookDTO getBookDTO(Book book);

    @Mapping(source = "bookId",target = "bookId")
    public abstract BookCustomEntity getBookCustomEntity(BookCustomDTO dto);

    @Mapping(source = "bookId",target = "bookId")
    @Mapping(source = "rackNo",target = "rackNo",qualifiedByName = "RackDTO")
    public abstract BookCustomDTO getBookCustomDTO(BookCustomEntity customEntity);

    public Author getAuthor(BookDTO dto) throws Exception{
        return authorDAO.getOne(dto.getAuthor());
    }

    @Named("Author")
    public int AuthorId(Author author){
        return author.getId();
    }

    @Named("RackDTO")
    public RackDTO getRackDTO(Rack rack){
        return new RackDTO(rack.getId(),rack.getRackPK().getRackNo(),rack.getRackPK().getShellNo());
    }

    @Mapping(source = ".",target = "bookIssuePK")
    public abstract BookIssue getBookIssue(BookIssueDTO dto);

    public BookIssuePK getBookIssuePK(BookIssueDTO dto){
        Student student = studentDAO.getOne(dto.getStudentId());
        BookReference bookReference = bookReferenceDAO.getOne(dto.getBookRefId());
        return new BookIssuePK(student,bookReference,dto.getIssueDate());
    }

    @Mapping(source = ".",target = "studentId",qualifiedByName = "Student")
    @Mapping(source = ".",target = "bookRefId",qualifiedByName = "BookReference")
    @Mapping(source = ".",target = "issueDate",qualifiedByName = "IssueDate")
    public abstract BookIssueDTO getBookIssueDTO(BookIssue bookIssue);

    @Named("Student")
    public String getStudent(BookIssue bookIssue){
        return bookIssue.getBookIssuePK().getStudent().getRegNo();
    }
    @Named("BookReference")
    public String getBookReference(BookIssue bookIssue){
        return bookIssue.getBookIssuePK().getBookReference().getRefNo();
    }
    @Named("IssueDate")
    public Date getIssueDate(BookIssue bookIssue){
        return bookIssue.getBookIssuePK().getIssueDate();
    }

    @Mapping(source = ".",target = "orderCartPK")
    public abstract OrderCart getOrderCart(OrderCartDTO dto);

    @Mapping(source = "orderCartPK", target = "userId",qualifiedByName = "UserId")
    @Mapping(source = "orderCartPK", target = "refId",qualifiedByName = "RefId")
    public abstract OrderCartDTO getOrderCartDTO(OrderCart orderCart);

    public OrderCartPK getOrderCartPk(OrderCartDTO dto){
        User user = userDAO.getOne(dto.getUserId());
        BookReference bookReference = bookReferenceDAO.getOne(dto.getRefId());
        return new OrderCartPK(user,bookReference);
    }

    @Named("UserId")
    public String getUserId(OrderCartPK pk){
        return pk.getUser().getUsername();
    }

    @Named("RefId")
    public String getRefId(OrderCartPK pk){
        return pk.getBookReference().getRefNo();
    }
}
