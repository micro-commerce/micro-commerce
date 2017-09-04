package org.micro.commerce.product.domain.query;

import org.micro.commerce.product.domain.model.Product;
import org.micro.commerce.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductQuery {

    private ProductRepository productRepository;

    public ProductQuery(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> listAll(){
        return productRepository.findAll();
    }

}
