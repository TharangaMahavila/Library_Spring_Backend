package lk.ins.library.business.custom.impl;

import lk.ins.library.business.custom.RackBO;
import lk.ins.library.business.util.EntityDTOMapper;
import lk.ins.library.dao.RackDAO;
import lk.ins.library.dto.RackDTO;
import lk.ins.library.entity.Rack;
import lk.ins.library.entity.RackPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-02-26
 **/
@Transactional
@Service
public class RackBOImpl implements RackBO {

    @Autowired
    private RackDAO rackDAO;
    @Autowired
    private EntityDTOMapper mapper;

    @Override
    public void saveRack(RackDTO dto) throws Exception {
        rackDAO.save(mapper.getRack(dto));
    }

    @Override
    public void updateRack(RackDTO dto) throws Exception {
        rackDAO.save(mapper.getRack(dto));
    }

    @Override
    public void deleteRack(int rackId) throws Exception {
        rackDAO.deleteById(rackId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RackDTO> findAllRacks() throws Exception {
        return rackDAO.findAll().stream().map(rack -> mapper.getRackDTO(rack)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RackDTO findRack(int rackId) throws Exception {
        return rackDAO.findById(rackId).map(rack -> mapper.getRackDTO(rack)).get();
    }

    @Override
    public RackDTO getRackByRackPk(RackPK pk) throws Exception {
        return mapper.getRackDTO(rackDAO.findByRackPK(pk));

    }
}
