package org.micro.commerce.product.domain.converter;

import org.micro.commerce.product.domain.event.ProductCreationFailed;
import org.springframework.stereotype.Component;

@Component
public class ProductCreationFailedConverter extends ProductEventConverter<ProductCreationFailed> {
}
