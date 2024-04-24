package com.revature.shop.dtos.responses;

import com.revature.shop.models.Product;

public class GetProductsResponse {
    Product product;

    public GetProductsResponse() {
    }

    public GetProductsResponse(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
