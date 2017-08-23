package org.micro.commerce.product.controller;

import org.micro.commerce.product.command.ProductCreateCommand;
import org.micro.commerce.product.controller.request.ProductCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "product")
public class ProductController {

    private ProductCreateCommand productCreateCommand;

    @Autowired
    public ProductController(ProductCreateCommand productCreateCommand) {
        this.productCreateCommand = productCreateCommand;
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public void create(@RequestBody @Valid ProductCreationRequest request){
        productCreateCommand.requestProductCreation(request);
    }

}
