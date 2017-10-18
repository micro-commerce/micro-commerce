package org.micro.commerce.product.application.controller;

import org.micro.commerce.product.domain.command.ProductCreateCommand;
import org.micro.commerce.product.domain.request.ProductCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(value = "products")
public class ProductCommandController {

    private ProductCreateCommand productCreateCommand;

    @Autowired
    public ProductCommandController(ProductCreateCommand productCreateCommand) {
        this.productCreateCommand = productCreateCommand;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody @Valid ProductCreationRequest request){
        UUID productId = UUID.randomUUID();
        productCreateCommand.requestProductCreation(productId, request);

        return seeOtherResponse(productId);
    }

    private ResponseEntity seeOtherResponse(UUID id){
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        return new ResponseEntity(responseHeaders, HttpStatus.SEE_OTHER);
    }

}
