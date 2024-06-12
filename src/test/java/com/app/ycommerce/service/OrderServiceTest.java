package com.app.ycommerce.service;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.app.ycommerce.entity.Address;
import com.app.ycommerce.entity.Cart;
import com.app.ycommerce.entity.Member;
import com.app.ycommerce.entity.OrderItem;
import com.app.ycommerce.repository.CartRepository;
import com.app.ycommerce.repository.MemberRepository;
import com.app.ycommerce.repository.OrderRepository;
import com.app.ycommerce.repository.ProductRepository;
import com.app.ycommerce.request.CreateOrderRequest;
import com.app.ycommerce.request.ProductAndCount;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
class OrderServiceTest {

	@InjectMocks
	private OrderService orderService;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private EntityManager em;

	@Test
	@DisplayName("카트조회")
	void 카트조회() {

		//given

		Optional<Member> byEmail = memberRepository.findByEmail("je13@example.com");
		Member member = byEmail
			.orElse(null);
		//when
		Cart cart = cartRepository.findByMember(member)
			.orElse(Cart.createCart(member));
		//then
		assertThat(cart).isNotNull();

	}

	@Test
	public void 주문_생성() throws Exception {
		//given
		Address address = new Address("서울", "연남", "011111");
		//객체 생성
		Member member = Member.builder()
			.role("USER")
			.email("test@ex.com")
			.name("길동")
			.build();
		//( "test", "1234", "test@ex.com", "01012341234", address, LocalDate.now(), "USER");
		em.persist(member);

		List<ProductAndCount> productAndCounts = new ArrayList<>();
		productAndCounts.add(ProductAndCount.builder()
			.productId(1L)
			.quantity(2)
			.price(10000)
			.build());
		productAndCounts.add(ProductAndCount.builder()
			.productId(2L)
			.quantity(4)
			.price(20000)
			.build());
		Address deliveryAddress = new Address("서울", "강동구", "10000");

		CreateOrderRequest createOrderRequest = new CreateOrderRequest(member.getId(), deliveryAddress,
			productAndCounts);

		List<OrderItem> orderItems = new ArrayList<>();
		//orderItems.add(OrderItem.createOrderItem());
		//when
		Member getMember = memberRepository.findById(member.getId()).orElse(null);

		//then
		//assertEquals(100000, order.getAmount(), "주문 가격 비교");
		//assertEquals(order.getOrderItems().size(), 2, "주문 수량 확인");
	}

}