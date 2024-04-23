package com.app.ycommerce.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.ycommerce.dto.ProductDto;
import com.app.ycommerce.entity.LikeItem;
import com.app.ycommerce.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@ResponseBody
@RequestMapping("/api/product")
public class ProductController {

	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/list")
	public ResponseEntity<List<ProductDto>> findAllByOrderByIdDesc() {
		List<ProductDto> products = productService.findAllByOrderByIdDesc();

		return ResponseEntity.ok().body(products);
	}

	@GetMapping("/productDetail/{productId}")
	public ResponseEntity<ProductDto> findAllByOrderByIdDesc(@PathVariable int productId) {
		ProductDto product = productService.findById(productId);

		return ResponseEntity.ok().body(product);
	}

	//상품 좋아요
	@PostMapping("/like-product/{productId}")
	public ResponseEntity<?> likeProduct(@PathVariable int productId) {
		productService.likeProduct(productId);
		return ResponseEntity.ok().build();
	}

	//좋아요한 상품 목록
	@GetMapping("/like-productList")
	public ResponseEntity<List<LikeItem>> getLikedProducts() {
		List<LikeItem> product = productService.getLikedProducts();
		return ResponseEntity.ok(product);
	}

}
