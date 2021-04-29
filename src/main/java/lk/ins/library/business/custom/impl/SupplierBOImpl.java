package lk.ins.library.business.custom.impl;

import lk.ins.library.business.custom.SupplierBO;
import lk.ins.library.business.util.EntityDTOMapper;
import lk.ins.library.dao.SupplierDAO;
import lk.ins.library.dto.SupplierDTO;
import lk.ins.library.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-24
 **/
@Service
@Transactional
public class SupplierBOImpl implements SupplierBO {

    @Autowired
    private SupplierDAO supplierDAO;
    @Autowired
    private EntityDTOMapper mapper;

    @Override
    public void saveSupplier(SupplierDTO dto) throws Exception {
        Supplier supplier = mapper.getSupplier(dto);
        supplier.setCreatedAt(new Date(System.currentTimeMillis()));
        supplierDAO.save(supplier);
    }

    @Override
    public void updateSupplier(SupplierDTO dto) throws Exception {
        Supplier savedSupplier = supplierDAO.getOne(dto.getId());
        Supplier supplier = mapper.getSupplier(dto);
        supplier.setCreatedAt(savedSupplier.getCreatedAt());
        supplierDAO.save(supplier);
    }

    @Override
    public void deleteSupplier(int id) throws Exception {
        supplierDAO.deleteById(id);
    }

    @Override
    public List<SupplierDTO> findAllSuppliers() throws Exception {
        return supplierDAO.findAll().stream().map(supplier -> mapper.getSupplierDTO(supplier)).collect(Collectors.toList());
    }

    @Override
    public SupplierDTO findSupplier(int id) throws Exception {
        return supplierDAO.findById(id).map(supplier -> mapper.getSupplierDTO(supplier)).get();
    }
}
