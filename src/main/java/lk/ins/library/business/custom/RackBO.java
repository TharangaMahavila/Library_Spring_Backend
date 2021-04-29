package lk.ins.library.business.custom;

import lk.ins.library.business.SuperBO;
import lk.ins.library.dto.AuthorDTO;
import lk.ins.library.dto.RackDTO;
import lk.ins.library.entity.RackPK;

import java.util.List;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-02-26
 **/
public interface RackBO extends SuperBO {

    public void saveRack(RackDTO dto) throws Exception;
    public void updateRack(RackDTO dto) throws Exception;
    public void deleteRack(int rackPK) throws Exception;
    public List<RackDTO> findAllRacks() throws Exception;
    public RackDTO findRack(int rackPK) throws Exception;

    public RackDTO getRackByRackPk(RackPK pk) throws Exception;

}
