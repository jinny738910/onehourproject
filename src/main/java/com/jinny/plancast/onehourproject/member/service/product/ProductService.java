package com.jinny.plancast.onehourproject.member.service.product;

import com.jinny.plancast.onehourproject.member.repository.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product saveProduct(Long id, Product product);

    List<Product> getAllProducts();

    Optional<Product> getProductById(Long id);

    Product updateProduct(Long id, Product productDetails);

    void deleteProduct(Long id);
}
