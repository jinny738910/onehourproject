package com.jinny.plancast.onehourproject.member.service.product;

import com.jinny.plancast.onehourproject.member.repository.ProductRepository;
import com.jinny.plancast.onehourproject.member.repository.entity.Product;
import lombok.RequiredArgsConstructor;
import org.hibernate.StaleObjectStateException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
//import org.springframework.retry.annotation.Retryable;

@Service
@RequiredArgsConstructor // Lombok을 사용하여 final 필드의 생성자 주입
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    // C: 상품 생성 (Create)
    // Optimistic Locking 관련 예외 발생 시 최대 3번 재시도
    @Retryable(
            value = {ObjectOptimisticLockingFailureException.class, StaleObjectStateException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 100) // 100ms 지연 후 재시도
    )
    @Transactional
    public Product saveProduct(Long id, Product product) {
        // 1. DB에서 기존 엔티티를 ID로 조회 (영속 상태로 만듦)
        //    이때, 기존 엔티티는 DB에 저장된 @Version 값을 가지고 옵니다.
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
    // Optimistic Locking 관련 예외 발생 시 최대 3번 재시도
    @Retryable(
            value = {ObjectOptimisticLockingFailureException.class, StaleObjectStateException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 100) // 100ms 지연 후 재시도
    )
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