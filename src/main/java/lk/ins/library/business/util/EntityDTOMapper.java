package lk.ins.library.business.util;

import lk.ins.library.dto.*;
import lk.ins.library.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.sql.Date;
import java.util.Set;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-02-24
 **/
@Mapper(componentModel = "spring",uses = {GradeListMapper.class})
public interface EntityDTOMapper {

    Author getAuthor(AuthorDTO dto);
    AuthorDTO getAuthorDTO(Author author);

    Category getCategory(CategoryDTO dto);
    CategoryDTO getCategoryDTO(Category category);

    User getUser(UserDTO dto);
    UserDTO getUserDTO(User user);

    BookDisposal getBookDisposal(BookDisposalDTO dto);
    BookDisposalDTO getBookDisposalDTO(BookDisposal bookDisposal);

    Supplier getSupplier(SupplierDTO dto);
    SupplierDTO getSupplierDTO(Supplier supplier);

    @Mapping(source = "id",target = "id")
    @Mapping(source = ".",target = "rackPK")
    Rack getRack(RackDTO dto);

    @Mapping(source = "id",target = "id")
    @Mapping(source = ".",target = "rackNo",qualifiedByName = "RackNo")
    @Mapping(source = ".",target = "shellNo",qualifiedByName = "ShellNo")
    RackDTO getRackDTO(Rack rack);

    @Mapping(source = "id",target = "id")
    @Mapping(source = ".",target = "gradePK")
    Grade getGrade(GradeDTO dto);

    @Mapping(source = "id",target = "id")
    @Mapping(source = ".",target = "grade",qualifiedByName = "Grade")
    @Mapping(source = ".",target = "section",qualifiedByName = "Section")
    @Mapping(source = ".",target = "year",qualifiedByName = "Year")
    GradeDTO getGradeDTO(Grade grade);

    @Mapping(source = "regNo", target = "regNo")
    @Mapping(source = ".",target = "name")
    @Mapping(source = ".",target = "address")
    Student getStudent(StudentDTO dto);

    @Mapping(source = ".",target = "initial",qualifiedByName = "NameInitial")
    @Mapping(source = ".",target = "fname",qualifiedByName = "FirstName")
    @Mapping(source = ".",target = "lname",qualifiedByName = "LastName")
    @Mapping(source = ".",target = "streetNo",qualifiedByName = "StreetNumber")
    @Mapping(source = ".",target = "firstStreet",qualifiedByName = "FirstStreetName")
    @Mapping(source = ".",target = "secondStreet",qualifiedByName = "SecondStreetName")
    @Mapping(source = ".",target = "town",qualifiedByName = "Town")
    StudentDTO getStudentDTO(Student student);

    @Mapping(source = ".",target = "name")
    @Mapping(source = ".",target = "address")
    Staff getStaff(StaffDTO dto);

    @Mapping(source = ".",target = "initial",qualifiedByName = "StaffNameInitial")
    @Mapping(source = ".",target = "fname",qualifiedByName = "StaffFirstName")
    @Mapping(source = ".",target = "lname",qualifiedByName = "StaffLastName")
    @Mapping(source = ".",target = "streetNo",qualifiedByName = "StaffStreetNumber")
    @Mapping(source = ".",target = "firstStreet",qualifiedByName = "StaffFirstStreetName")
    @Mapping(source = ".",target = "secondStreet",qualifiedByName = "StaffSecondStreetName")
    @Mapping(source = ".",target = "town",qualifiedByName = "StaffTown")
    StaffDTO getStaffDTO(Staff staff);

    @Named("RackNo")
    default String getRackNumber(Rack rack){
        return rack.getRackPK().getRackNo();
    }
    @Named("ShellNo")
    default String getShellNumber(Rack rack){
        return rack.getRackPK().getShellNo();
    }
    @Named("Grade")
    default Integer getGrade(Grade grade){
        return grade.getGradePK().getGrade();
    }
    @Named("Section")
    default Section getSection(Grade grade){
        return grade.getGradePK().getSection();
    }
    @Named("Year")
    default Integer getYear(Grade grade){
        return grade.getGradePK().getYear();
    }
    @Named("NameInitial")
    default String getInitial(Student student){
        return student.getName().getInitial();
    }
    @Named("StaffNameInitial")
    default String getStaffNameInitial(Staff staff){
        return staff.getName().getInitial();
    }
    @Named("FirstName")
    default String getFName(Student student){
        return student.getName().getFname();
    }
    @Named("StaffFirstName")
    default String getStaffFName(Staff staff){
        return staff.getName().getFname();
    }
    @Named("LastName")
    default String getLName(Student student){
        return student.getName().getLname();
    }
    @Named("StaffLastName")
    default String getStaffLName(Staff staff){
        return staff.getName().getLname();
    }
    @Named("StreetNumber")
    default String getStreetNo(Student student){
        return student.getAddress().getStreetNo();
    }
    @Named("StaffStreetNumber")
    default String getStaffStreetNo(Staff staff){
        return staff.getAddress().getStreetNo();
    }
    @Named("FirstStreetName")
    default String getFirstStreet(Student student){
        return student.getAddress().getFirstStreet();
    }
    @Named("StaffFirstStreetName")
    default String getStaffFirstStreet(Staff staff){
        return staff.getAddress().getFirstStreet();
    }
    @Named("SecondStreetName")
    default String getSecondStreet(Student student){
        return student.getAddress().getSecondStreet();
    }
    @Named("StaffSecondStreetName")
    default String getStaffSecondStreet(Staff staff){
        return staff.getAddress().getSecondStreet();
    }
    @Named("Town")
    default String getTown(Student student){
        return student.getAddress().getTown();
    }
    @Named("StaffTown")
    default String getStaffTown(Staff staff){
        return staff.getAddress().getTown();
    }
}
