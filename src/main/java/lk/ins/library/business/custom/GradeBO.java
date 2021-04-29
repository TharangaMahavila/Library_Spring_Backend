package lk.ins.library.business.custom;

import lk.ins.library.business.SuperBO;
import lk.ins.library.dto.AuthorDTO;
import lk.ins.library.dto.GradeDTO;

import java.util.List;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-15
 **/
public interface GradeBO extends SuperBO {

    public void saveGrade(GradeDTO dto) throws Exception;
    public void updateGrade(GradeDTO dto) throws Exception;
    public void deleteGrade(Integer gradeId) throws Exception;
    public List<GradeDTO> findAllGrades() throws Exception;
    public GradeDTO findGrade(Integer gradeId) throws Exception;
}
