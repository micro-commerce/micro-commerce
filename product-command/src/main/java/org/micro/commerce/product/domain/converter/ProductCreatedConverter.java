package org.micro.commerce.product.domain.converter;

import org.micro.commerce.product.domain.event.ProductCreated;
import org.springframework.stereotype.Component;

@Component
public class ProductCreatedConverter extends ProductEventConverter<ProductCreated> {
}
