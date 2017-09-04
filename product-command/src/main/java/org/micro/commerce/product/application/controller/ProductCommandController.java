package org.micro.commerce.product.application.controller;

import org.micro.commerce.product.domain.command.ProductCreateCommand;
import org.micro.commerce.product.application.request.ProductCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "product")
public class ProductCommandController {

    private ProductCreateCommand productCreateCommand;

    @Autowired
    public ProductCommandController(ProductCreateCommand productCreateCommand) {
        this.productCreateCommand = productCreateCommand;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void create(@RequestBody @Valid ProductCreationRequest request){
        productCreateCommand.requestProductCreation(request);
    }

}
