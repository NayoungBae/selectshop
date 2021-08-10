package com.nao.selectshop.controller;

import com.nao.selectshop.models.Product;
import com.nao.selectshop.models.ProductMypriceRequestDto;
import com.nao.selectshop.models.ProductRepository;
import com.nao.selectshop.models.ProductRequestDto;
import com.nao.selectshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class ProductRestController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    @GetMapping("/api/products")
    public List<Product> readProducts() {
        return productRepository.findAll();
    }

    @PostMapping("/api/products")
    public Product createProduct(@RequestBody ProductRequestDto requestDto) {
        Product product = new Product(requestDto);
        return productRepository.save(product);
    }

    @PutMapping("/api/products/{id}")
    public Long updateProduct(@PathVariable Long id,
                              @RequestBody ProductMypriceRequestDto requestDto) {
        return productService.update(id, requestDto);
    }

}