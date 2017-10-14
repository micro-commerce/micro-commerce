package org.micro.commerce.product.domain.converter;

import org.micro.commerce.product.domain.event.ProductCreationRequested;
import org.springframework.stereotype.Component;

@Component
public class ProductCreationRequestedConverter extends ProductEventConverter<ProductCreationRequested> {

}
