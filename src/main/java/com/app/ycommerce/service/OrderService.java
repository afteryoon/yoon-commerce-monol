package com.app.ycommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.ycommerce.entity.Cart;
import com.app.ycommerce.entity.CartItem;
import com.app.ycommerce.entity.Member;
import com.app.ycommerce.entity.Order;
import com.app.ycommerce.entity.OrderItem;
import com.app.ycommerce.entity.OrderStatus;
import com.app.ycommerce.entity.Product;
import com.app.ycommerce.repository.CartItemRepository;
import com.app.ycommerce.repository.CartRepository;
import com.app.ycommerce.repository.OrderRepository;
import com.app.ycommerce.request.OrderItemRequest;

@Service
@Transactional
public class OrderService {

	private final OrderRepository orderRepository;
	private final MemberService memberService;
	private final ProductService productService;
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;

	public OrderService(OrderRepository orderRepository, MemberService memberService,
		ProductService productService, CartRepository cartRepository, CartItemRepository cartItemRepository) {
		this.orderRepository = orderRepository;
		this.memberService = memberService;
		this.productService = productService;
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
	}

	//주문 생성
	public Order createOrder(List<OrderItemRequest> orderItemRequests) {
		Member currentMember = memberService.getCurrentMember();
		Order order = Order.builder()
			.member(currentMember)
			.orderStatus(OrderStatus.PLACED)
			.build();

		List<OrderItem> orderItems = new ArrayList<>();

		for (OrderItemRequest orderItemRequest : orderItemRequests) {
			Product product = orderItemRequest.getProduct();
			//제품 수량 체크
			product.decreaseInventory(orderItemRequest.getQuantity());

			OrderItem orderItem = OrderItem.builder()
				.order(order)
				.product(product)
				.quantity(orderItemRequest.getQuantity())
				.build();

			orderItems.add(orderItem);
		}

		order.setOrderItems(orderItems);
		return orderRepository.save(order);
	}

	//주문 취소
	public void cancelOrder(Order order) {
		for (OrderItem orderItem : order.getOrderItems()) {
			Product product = orderItem.getProduct();
			product.increaseInventory(orderItem.getQuantity());
		}
		order.setOrderStatus(OrderStatus.CANCELED);
		orderRepository.save(order);
	}

	//장바구니 상품 추가
	public void addToCart(OrderItemRequest orderItemRequest) {
		Member member = memberService.getCurrentMember();
		Cart cart;

		if (member.getCart() == null) {
			cart = Cart.builder()
				.member(member)
				.build();
			member.setCart(cartRepository.save(cart));
		} else {
			cart = member.getCart();
		}
		Product product = orderItemRequest.getProduct();

		CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
			.orElse(null);

		if (cartItem == null) {
			cartItem = CartItem.builder()
				.cart(cart)
				.product(product)
				.quantity(orderItemRequest.getQuantity())
				.build();
		} else {
			cartItem.setQuantity(cartItem.getQuantity() + orderItemRequest.getQuantity());
			cartItemRepository.save(cartItem);
		}
	}

	//장바구니 리스트 조회
}