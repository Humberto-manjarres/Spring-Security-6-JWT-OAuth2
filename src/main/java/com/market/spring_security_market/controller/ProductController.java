package com.market.spring_security_market.controller;

import com.market.spring_security_market.dto.SaveProduct;
import com.market.spring_security_market.persistence.entity.Product;
import com.market.spring_security_market.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> findAll(Pageable pageable){
        Page<Product> productPage = productService.findAll(pageable);
        if (productPage.hasContent()){
            return ResponseEntity.ok(productPage);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> findOneById(@PathVariable Long productId){
        Optional<Product> product = productService.findOneById(productId);
        if (product.isPresent()){
            return ResponseEntity.ok(product.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Product> createOne(@RequestBody @Valid SaveProduct saveProduct){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createOne(saveProduct));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateOneById(@PathVariable Long productId,
                                                 @RequestBody @Valid SaveProduct saveProduct){
        return ResponseEntity.ok(productService.updateOneById(productId,saveProduct));
    }

    @PutMapping("/{productId}/disabled")
    public ResponseEntity<Product> disabledOneById(@PathVariable Long productId){
        return ResponseEntity.ok(productService.disabledOneById(productId));
    }


}
