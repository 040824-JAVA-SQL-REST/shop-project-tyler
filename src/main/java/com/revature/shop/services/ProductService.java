package com.revature.shop.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.revature.shop.daos.ProductDao;
import com.revature.shop.models.Product;

public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Product save(Product product) {
        product.setId(UUID.randomUUID().toString());
        return productDao.save(product);
    }

    public Product update(Product product) {
        return productDao.update(product);
    }

    public boolean delete(String id) {
        return productDao.delete(id);
    }

    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return productDao.findAll().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public boolean isUniqueName(String name) {
        Optional<Product> optProduct = productDao.findAll()
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

    public boolean isValidPrice(float price) {
        return price > 0.00f;
    }

    public String getNameById(String id) {
        return productDao.findAll()
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .get()
                .getName();
    }
}
