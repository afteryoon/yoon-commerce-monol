package com.app.ycommerce.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.app.ycommerce.entity.Cart;
import com.app.ycommerce.entity.Member;
import com.app.ycommerce.repository.CartRepository;
import com.app.ycommerce.repository.MemberRepository;

@SpringBootTest
@Transactional
class OrderServiceTest {

	@Autowired
	private OrderService orderService;
	@Autowired
	private MemberRepository memberService;
	@Autowired
	private CartRepository cartRepository;

	@Test
	@DisplayName("카트조회")
	void 카트조회() {

		//given

		Member member = memberService.findByEmail("je12@example.com")
			.orElse(null);
		//when
		Cart cart = cartRepository.findByMember(member)
			.orElse(Cart.createCart(member));
		//then
		assertThat(cart).isNotNull();

	}

}