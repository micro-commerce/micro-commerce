package org.micro.commerce.product.application.controller;

import org.micro.commerce.product.application.converter.ProductConverter;
import org.micro.commerce.product.domain.response.ListAllProductsResponse;
import org.micro.commerce.product.domain.model.Product;
import org.micro.commerce.product.domain.query.ProductQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "product")
public class ProductQueryController {

    private ProductConverter productConverter;

    private ProductQuery productQuery;

    public ProductQueryController(ProductConverter productConverter, ProductQuery productQuery) {
        this.productConverter = productConverter;
        this.productQuery = productQuery;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ListAllProductsResponse listAll(){
        List<Product> products = productQuery.listAll();
        List<org.micro.commerce.product.domain.response.model.Product> productResults = productConverter.to(products);
        return new ListAllProductsResponse(productResults);
    }

}
