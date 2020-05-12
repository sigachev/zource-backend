package com.zource.service.category;

import com.zource.model.Category;
import com.zource.model.Product;
import com.zource.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> getChildCategories(Long categoryID) {

        Optional<Category> cat = categoryRepository.findById(categoryID);

        if (cat.isPresent())
            return (List<Category>) cat.get().getChildCategories();
        else {
            throw new ResourceNotFoundException("Record not found for category with id : " + categoryID);
        }
    }

    @Override
    public Set<Product> getAllProducts(Long categoryID) {
        Optional<Category> cat = categoryRepository.findById(categoryID);

        if (cat.isPresent())
            return cat.get().getProducts();
        else {
            throw new ResourceNotFoundException("Record not found for category with id : " + categoryID);
        }
    }

    @Override
    public void updateCategoryImage(Long categoryId) {

    }



}
