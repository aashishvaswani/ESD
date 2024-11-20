package com.aashish.ESD.Controller;

import com.aashish.ESD.dto.ProductRequest;
import com.aashish.ESD.dto.ProductResponse;
import com.aashish.ESD.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{name}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable String name) {
        return ResponseEntity.ok(productService.retrieveProduct(name));
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }

    @PatchMapping("/{name}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable String name, @RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.ok(productService.updateProduct(name, productRequest));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteProduct(@PathVariable String name) {
        return ResponseEntity.ok(productService.deleteProduct(name));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/top")
    public ResponseEntity<List<ProductResponse>> getTopTwoProducts() {
        return ResponseEntity.ok(productService.getTopTwoProducts());
    }
}
