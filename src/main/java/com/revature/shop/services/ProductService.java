package com.revature.shop.services;

import java.util.UUID;

import com.revature.shop.daos.ProductDao;
import com.revature.shop.models.Product;

public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void save(Product product) {
        product.setId(UUID.randomUUID().toString());
        productDao.save(product);
    }

    public Product getProductByName(String name) {
        return productDao.findAll().stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .get();
    }
}
