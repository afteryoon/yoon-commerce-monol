package com.app.ycommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.ycommerce.entity.LikeItem;
import com.app.ycommerce.entity.Product;

public interface LikeItemRepository extends JpaRepository<LikeItem, Long> {
	Optional<LikeItem> findByMemberIdAndProduct(Long member, Product product);

	List<LikeItem> findByMemberId(Long member);
}
