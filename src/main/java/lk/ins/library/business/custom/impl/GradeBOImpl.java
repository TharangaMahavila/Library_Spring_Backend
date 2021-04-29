package lk.ins.library.business.custom.impl;

import lk.ins.library.business.custom.GradeBO;
import lk.ins.library.business.util.EntityDTOMapper;
import lk.ins.library.dao.GradeDAO;
import lk.ins.library.dto.GradeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-15
 **/
@Service
@Transactional
public class GradeBOImpl implements GradeBO {

    @Autowired
    private GradeDAO gradeDAO;
    @Autowired
    private EntityDTOMapper mapper;

    @Override
    public void saveGrade(GradeDTO dto) throws Exception {
        gradeDAO.save(mapper.getGrade(dto));
    }

    @Override
    public void updateGrade(GradeDTO dto) throws Exception {
        gradeDAO.save(mapper.getGrade(dto));
    }

    @Override
    public void deleteGrade(Integer gradeId) throws Exception {
        gradeDAO.deleteById(gradeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GradeDTO> findAllGrades() throws Exception {
        return gradeDAO.findAll().stream().map(grade -> mapper.getGradeDTO(grade)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public GradeDTO findGrade(Integer gradeId) throws Exception {
        return gradeDAO.findById(gradeId).map(grade -> mapper.getGradeDTO(grade)).get();
    }
}
