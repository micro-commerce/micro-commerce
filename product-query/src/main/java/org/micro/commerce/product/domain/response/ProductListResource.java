package org.micro.commerce.product.domain.response;

import org.micro.commerce.product.domain.response.model.Product;

import java.util.List;

public class ProductListResource {

    private List<Product> products;

    public ProductListResource(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
