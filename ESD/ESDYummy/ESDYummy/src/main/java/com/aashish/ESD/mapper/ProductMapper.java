package com.aashish.ESD.mapper;

import com.aashish.ESD.dto.ProductRequest;
import com.aashish.ESD.dto.ProductResponse;
import com.aashish.ESD.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toEntity(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.name())
                .price(productRequest.price())
                .build();
    }
    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(product.getName(), product.getPrice());
    }
}
