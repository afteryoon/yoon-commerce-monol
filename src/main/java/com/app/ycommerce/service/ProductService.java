package com.app.ycommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.ycommerce.dto.ProductDto;
import com.app.ycommerce.entity.LikeItem;
import com.app.ycommerce.entity.Member;
import com.app.ycommerce.entity.Product;
import com.app.ycommerce.repository.LikeItemRepository;
import com.app.ycommerce.repository.ProductRepository;

@Service
@Transactional
public class ProductService {

	private final ProductRepository productRepository;
	private final LikeItemRepository likeItemRepository;
	private final MemberService memberService;

	public ProductService(ProductRepository productRepository, MemberService memberService
		, LikeItemRepository likeItemRepository) {
		this.productRepository = productRepository;
		this.memberService = memberService;
		this.likeItemRepository = likeItemRepository;
	}

	//상품 좋아요
	public void likeProduct(Long productId) {

		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new IllegalArgumentException("준비되지 않은 상품입니다."));
		Member member = memberService.getCurrentMember();

		LikeItem existingLike = likeItemRepository.findByMemberAndProduct(member, product)
			.orElse(null);

		if (existingLike == null) {
			LikeItem likeItem = LikeItem.builder()
				.product(product)
				.member(member)
				.build();

			likeItemRepository.save(likeItem);
		} else {
			likeItemRepository.delete(existingLike);
		}
	}

	//유저가 좋아요한 목록
	public List<LikeItem> getLikedProducts() {
		Member member = memberService.getCurrentMember();
		return likeItemRepository.findByMember(member);
	}

	//최근 상품순
	public List<ProductDto> findAllByOrderByIdDesc() {
		List<Product> products = productRepository.findAllByOrderByIdDesc();
		return products.stream()
			.map(this::mapToProductDto)
			.collect(Collectors.toList());
	}

	//ProductDto 빌드 메서드
	private ProductDto mapToProductDto(Product product) {
		return ProductDto.builder()
			.productId(product.getId())
			.categoryId(product.getCategory().getId())
			.productName(product.getName())
			.productDescription(product.getDescription())
			.price(product.getPrice())
			.inventory(product.getInventory())
			.build();
	}

	//단품 조회
	public ProductDto findById(Long productId) {
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new IllegalArgumentException("준비되지 않은 상품입니다."));

		return mapToProductDto(product);
	}

	public Product getProduct(Long productId) {
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new IllegalArgumentException("준비되지 않은 상품입니다."));

		return product;
	}

}