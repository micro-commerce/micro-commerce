package org.micro.commerce.product.application.controller;

import org.micro.commerce.product.application.converter.ProductConverter;
import org.micro.commerce.product.domain.response.ProductListResource;
import org.micro.commerce.product.domain.model.Product;
import org.micro.commerce.product.domain.query.ProductQuery;
import org.micro.commerce.product.domain.response.ProductResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "products")
public class ProductQueryController {

    private ProductConverter productConverter;

    private ProductQuery productQuery;

    public ProductQueryController(ProductConverter productConverter, ProductQuery productQuery) {
        this.productConverter = productConverter;
        this.productQuery = productQuery;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ProductListResource getAllProducts(){
        List<Product> products = productQuery.findAll();
        List<org.micro.commerce.product.domain.response.model.Product> productResults = productConverter.to(products);
        productResults.stream().forEach(product -> linkProduct(product));
        return new ProductListResource(productResults);
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public ResponseEntity<ProductResource> getProductById(@PathVariable("productId") UUID productId){
        Product product = productQuery.findById(productId);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        org.micro.commerce.product.domain.response.model.Product productResult = productConverter.to(product);
        linkProduct(productResult);
        return ResponseEntity.ok(new ProductResource(productResult));
    }

    private void linkProduct(org.micro.commerce.product.domain.response.model.Product product){
        product.add(linkTo(ProductQueryController.class).slash(product.getProductId()).withSelfRel());
    }

}
