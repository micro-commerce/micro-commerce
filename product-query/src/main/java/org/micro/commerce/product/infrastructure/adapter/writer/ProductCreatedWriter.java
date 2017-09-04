package org.micro.commerce.product.infrastructure.adapter.writer;

import org.micro.commerce.product.domain.event.ProductCreated;
import org.micro.commerce.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductCreatedWriter implements Writer<ProductCreated> {

    private ProductRepository productRepository;

    public ProductCreatedWriter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void persist(ProductCreated event) {
        productRepository.save(event.getModel());
    }

}
