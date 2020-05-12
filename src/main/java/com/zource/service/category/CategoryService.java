package com.zource.service.category;

import com.zource.model.Category;
import com.zource.model.Product;

import java.util.List;
import java.util.Set;

public interface CategoryService {

    List<Category> getAllCategories();

    List<Category> getChildCategories(Long categoryID);

    Set<Product> getAllProducts(Long categoryID);

    void updateCategoryImage(Long categoryId);

}
