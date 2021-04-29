package lk.ins.library.business.custom;

import lk.ins.library.business.SuperBO;
import lk.ins.library.dto.CategoryDTO;

import java.util.List;

/**
 * @author:Tharanga Mahavila <tharangamahavila@gmail.com>
 * @since : 2021-03-11
 **/
public interface CategoryBO extends SuperBO {

    public void saveCategory(CategoryDTO dto) throws Exception;
    public void updateCategory(CategoryDTO dto) throws Exception;
    public void deleteCategory(int categoryId) throws Exception;
    public List<CategoryDTO> findAllCategories() throws Exception;
    public CategoryDTO findCategory(int categoryId) throws Exception;
}
