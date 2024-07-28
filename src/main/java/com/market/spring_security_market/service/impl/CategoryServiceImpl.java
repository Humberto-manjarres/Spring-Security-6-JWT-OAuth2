package com.market.spring_security_market.service.impl;

import com.market.spring_security_market.dto.SaveCategory;
import com.market.spring_security_market.exception.ObjectNotFoundException;
import com.market.spring_security_market.persistence.entity.Category;
import com.market.spring_security_market.persistence.repository.CategoryRepository;
import com.market.spring_security_market.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> findOneById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Category createOne(SaveCategory saveCategory) {
        Category category = new Category();
        category.setName(saveCategory.getName());
        category.setStatus(Category.CategoryStatus.ENABLED);
        return categoryRepository.save(category);
    }

    @Override
    public Category updateOneById(Long categoryId, SaveCategory saveCategory) {
        Category categoryFromDB = this.categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ObjectNotFoundException("Product Not found whit id" + categoryId));
        categoryFromDB.setName(saveCategory.getName());

        return categoryRepository.save(categoryFromDB);
    }

    @Override
    public Category disabledOneById(Long categoryId) {
        Category categoryFromDB = this.categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ObjectNotFoundException("Product Not found whit id" + categoryId));
        categoryFromDB.setStatus(Category.CategoryStatus.DISABLED);
        return categoryRepository.save(categoryFromDB);
    }
}
