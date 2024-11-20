package com.aashish.ESD.service;

import com.aashish.ESD.dto.ProductRequest;
import com.aashish.ESD.dto.ProductResponse;
import com.aashish.ESD.entity.Product;
import com.aashish.ESD.mapper.ProductMapper;
import com.aashish.ESD.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final ProductMapper productMapper;

    public ProductResponse retrieveProduct(String name) {
        Optional<Product> productOptional = productRepo.findByName(name);
        if (productOptional.isPresent()) {
            return productMapper.toProductResponse(productOptional.get());
        }
        return null;
    }

    public String createProduct(ProductRequest productRequest) {
        Product product = productMapper.toEntity(productRequest);
        productRepo.save(product);
        return "Product created successfully";
    }

    public ProductResponse updateProduct(String name, ProductRequest productRequest) {
        Product oldProduct = productRepo.findByName(name).orElse(null);
        if(oldProduct != null) {
            oldProduct.setName(productRequest.name());
            oldProduct.setPrice(productRequest.price());
            productRepo.save(oldProduct);
            return productMapper.toProductResponse(oldProduct);
        }
        else return null;
    }

    public String deleteProduct(String name) {
        Optional<Product> productOptional = productRepo.findByName(name);
        if (productOptional.isPresent()) {
            productRepo.delete(productOptional.get());
            return "Product deleted successfully";
        }
        else return null;
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepo.findAll();
        List<ProductResponse> productResponses = products.stream().map(productMapper::toProductResponse).collect(Collectors.toList());
        return productResponses;
    }

    public List<ProductResponse> getTopTwoProducts() {
        List<Product> products = productRepo.findProductsByPriceBetween(15.0, 30.0);
        return products.stream().map(productMapper::toProductResponse).collect(Collectors.toList());
    }
}
