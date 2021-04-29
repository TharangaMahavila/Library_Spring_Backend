package lk.ins.library.business.util;

import lk.ins.library.dto.GradeDTO;
import lk.ins.library.entity.Grade;
import lk.ins.library.entity.GradePK;
import lk.ins.library.entity.Section;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.boot.context.properties.bind.Name;

import java.util.Set;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-17
 **/
@Mapper(componentModel = "spring")
public interface GradeListMapper {

    Set<Grade> getGradeSet(Set<GradeDTO> gradeDTOSet);
    Set<GradeDTO> getGradeDTOSet(Set<Grade> gradeSet);

    default Grade getGradeSet(GradeDTO dto){
        Grade grade = new Grade();
        grade.setId(dto.getId());
        grade.setGradePK(new GradePK(dto.getGrade(),dto.getSection(),dto.getYear()));
        return grade;
    }
    default GradeDTO getGradeDTOSet(Grade grade){
        GradeDTO dto = new GradeDTO();
        dto.setId(grade.getId());
        dto.setGrade(grade.getGradePK().getGrade());
        dto.setSection(grade.getGradePK().getSection());
        dto.setYear(grade.getGradePK().getYear());
        return dto;
    }
}
