package com.market.spring_security_market.service.impl;

import com.market.spring_security_market.dto.SaveProduct;
import com.market.spring_security_market.exception.ObjectNotFoundException;
import com.market.spring_security_market.persistence.entity.Category;
import com.market.spring_security_market.persistence.entity.Product;
import com.market.spring_security_market.persistence.repository.ProductRepository;
import com.market.spring_security_market.service.ProductService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> findOneById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Product createOne(SaveProduct saveProduct) {
        Product product = new Product();
        product.setName(saveProduct.getName());
        product.setPrice(saveProduct.getPrice());
        product.setStatus(Product.ProductStatus.ENABLED);

        Category category = new Category();
        category.setId(saveProduct.getCategoryId());

        product.setCategory(category);

        return productRepository.save(product);
    }

    @Override
    public Product updateOneById(Long productId, SaveProduct saveProduct) {
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(()-> new ObjectNotFoundException("Product Not found whit id" + productId));

        productFromDB.setName(saveProduct.getName());
        productFromDB.setPrice(saveProduct.getPrice());

        Category category = new Category();
        category.setId(saveProduct.getCategoryId());

        productFromDB.setCategory(category);

        return productRepository.save(productFromDB);
    }

    @Override
    public Product disabledOneById(Long productId) {
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(()-> new ObjectNotFoundException("Product Not found whit id" + productId));
        productFromDB.setStatus(Product.ProductStatus.DISABLED);
        return productRepository.save(productFromDB);
    }
}
