package com.jinny.plancast.onehourproject.member.repository;

import com.jinny.plancast.onehourproject.member.repository.entity.Member;
import com.jinny.plancast.onehourproject.member.repository.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// <엔티티 클래스, ID 타입>
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // JpaRepository를 상속하면 save(), findById(), findAll(), deleteById() 등의 기본 CRUD 메서드가 자동으로 제공됩니다.
    // 필요하다면 findByName(String name)과 같은 커스텀 메서드를 추가할 수 있습니다.
}
