package lk.ins.library.business.custom;

import lk.ins.library.business.SuperBO;
import lk.ins.library.dto.SupplierDTO;

import java.util.List;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-24
 **/
public interface SupplierBO extends SuperBO {

    public void saveSupplier(SupplierDTO dto) throws Exception;
    public void updateSupplier(SupplierDTO dto) throws Exception;
    public void deleteSupplier(int id) throws Exception;
    public List<SupplierDTO> findAllSuppliers() throws Exception;
    public SupplierDTO findSupplier(int id) throws Exception;
}
