package com.jinny.plancast.onehourproject.member.service;

import com.jinny.plancast.onehourproject.member.controller.dto.JoinRequest;

import com.jinny.plancast.onehourproject.member.repository.ProductRepository;
import com.jinny.plancast.onehourproject.member.repository.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // Lombok을 사용하여 final 필드의 생성자 주입
public class ProductService {

    private final ProductRepository productRepository;

    // C: 상품 생성 (Create)
    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // R: 모든 상품 조회 (Read All)
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // R: ID로 상품 조회 (Read One)
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // U: 상품 수정 (Update)
    @Transactional
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found for this id :: " + id));

        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());

        return productRepository.save(product);
    }

    // D: 상품 삭제 (Delete)
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found for this id :: " + id));

        productRepository.delete(product);
    }
}