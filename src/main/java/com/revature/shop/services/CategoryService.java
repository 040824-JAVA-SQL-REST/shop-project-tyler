package com.revature.shop.services;

import java.util.List;
import java.util.Optional;

import com.revature.shop.daos.CategoryDao;
import com.revature.shop.models.Category;

public class CategoryService {
    private final CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public Category save(Category category) {
        return categoryDao.save(category);
    }

    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    public Optional<Category> findCategoryByName(String name) {
        return categoryDao.findAll().stream()
                .filter(c -> c.getName().equals(name))
                .findFirst();
    }

    public boolean isUniqueName(String name) {
        Optional<Category> optProduct = categoryDao.findAll()
                .stream()
                .filter(p -> p.getName().equals(name))
                .findFirst();
        if (optProduct.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isValidName(String name) {
        if (name.isEmpty() || name == null || name.length() < 4) {
            return false;
        } else
            return true;
    }

    public boolean isValidId(String id) {
        Optional<Category> optCategory = categoryDao.findAll().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        return optCategory.isPresent();
    }
}
