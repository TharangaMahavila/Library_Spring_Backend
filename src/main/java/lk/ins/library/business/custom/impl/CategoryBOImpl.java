package lk.ins.library.business.custom.impl;

import lk.ins.library.business.custom.CategoryBO;
import lk.ins.library.business.util.EntityDTOMapper;
import lk.ins.library.dao.CategoryDAO;
import lk.ins.library.dto.CategoryDTO;
import lk.ins.library.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-11
 **/
@Transactional
@Service
public class CategoryBOImpl implements CategoryBO {

    @Autowired
    private CategoryDAO categoryDAO;
    @Autowired
    private EntityDTOMapper mapper;

    public CategoryBOImpl() {
    }

    @Override
    public void saveCategory(CategoryDTO dto) throws Exception {
        Category category = mapper.getCategory(dto);
        category.setCreatedAt(new Date(System.currentTimeMillis()));
        categoryDAO.save(category);
    }

    @Override
    public void updateCategory(CategoryDTO dto) throws Exception {
        Category savedCategory = categoryDAO.getOne(dto.getId());
        Category category = mapper.getCategory(dto);
        category.setCreatedAt(savedCategory.getCreatedAt());
        categoryDAO.save(category);
    }

    @Override
    public void deleteCategory(int categoryId) throws Exception {
        categoryDAO.deleteById(categoryId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDTO> findAllCategories() throws Exception {
        return categoryDAO.findAll().stream().map(category -> mapper.getCategoryDTO(category)).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO findCategory(int categoryId) throws Exception {
        return categoryDAO.findById(categoryId).map(category -> mapper.getCategoryDTO(category)).get();
    }
}
