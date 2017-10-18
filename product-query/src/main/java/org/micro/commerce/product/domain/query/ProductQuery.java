package org.micro.commerce.product.domain.query;

import org.micro.commerce.product.domain.model.Product;
import org.micro.commerce.product.infrastructure.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductQuery {

    private ProductRepository productRepository;

    public ProductQuery(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findById(UUID productId) {
        return productRepository.findById(productId);
    }

}
