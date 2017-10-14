package org.micro.commerce.product.domain.converter;

import org.micro.commerce.product.domain.event.ProductCreationValidated;
import org.springframework.stereotype.Component;

@Component
public class ProductCreationValidatedConverter extends ProductEventConverter<ProductCreationValidated> {

}
