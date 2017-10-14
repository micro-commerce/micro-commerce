package org.micro.commerce.product.infrastructure.adapter.writer;

import org.micro.commerce.product.domain.event.ProductEvent;
import org.micro.commerce.product.infrastructure.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductCreatedWriter implements Writer<ProductEvent> {

    private ProductRepository productRepository;

    public ProductCreatedWriter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void persist(ProductEvent event) {
        productRepository.save(event.getModel());
    }

}
