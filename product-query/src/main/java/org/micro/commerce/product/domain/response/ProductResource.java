package org.micro.commerce.product.domain.response;

import org.micro.commerce.product.domain.response.model.Product;

public class ProductResource {

    private Product product;

    public ProductResource(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
