package org.micro.commerce.product.application.converter;

import org.micro.commerce.product.domain.response.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductConverter {

    public Product to(org.micro.commerce.product.domain.model.Product product){
        Product productResult = new Product();
        productResult.setId(product.getId());
        productResult.setName(product.getName());
        productResult.setDescription(product.getDescription());
        productResult.setPrice(product.getPrice());

        return productResult;
    }

    public List<Product> to(List<org.micro.commerce.product.domain.model.Product> products){
        return products
                .stream()
                .map(product -> to(product))
                .collect(Collectors.toList());
    }

}
