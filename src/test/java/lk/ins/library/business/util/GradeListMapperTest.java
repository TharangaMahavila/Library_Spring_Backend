package lk.ins.library.business.util;

import lk.ins.library.dto.GradeDTO;
import lk.ins.library.entity.Grade;
import lk.ins.library.entity.Section;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-17
 **/
@SpringBootTest
public class GradeListMapperTest {
/*
    @Autowired
    private GradeListMapper mapper;

    @Test
    public void getGradeSet() {
        Set<GradeDTO> gradeDTOSet = new HashSet<>();
        gradeDTOSet.add(new GradeDTO(1,10, Section.A,2020));
        Set<Grade> gradeSet = mapper.getGradeSet(gradeDTOSet);
        System.out.println(gradeSet);
    }

    @Test
    public void getGradeDTOSet() {
        Set<Grade> gradeSet = new HashSet<>();
        gradeSet.add(new Grade(1,10,Section.A,2020));
        Set<GradeDTO> gradeDTOSet = mapper.getGradeDTOSet(gradeSet);
        System.out.println(gradeDTOSet);
    }*/
}
