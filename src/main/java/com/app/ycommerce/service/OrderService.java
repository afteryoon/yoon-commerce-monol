package com.app.ycommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.ycommerce.dto.CartItemDto;
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
import com.app.ycommerce.repository.ProductRepository;
import com.app.ycommerce.request.OrderItemRequest;
import com.app.ycommerce.request.OrderRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class OrderService {

	private final OrderRepository orderRepository;
	private final MemberService memberService;
	private final ProductRepository productRepository;
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;

	public OrderService(OrderRepository orderRepository, MemberService memberService,
		ProductRepository productRepository, CartRepository cartRepository, CartItemRepository cartItemRepository) {
		this.orderRepository = orderRepository;
		this.memberService = memberService;
		this.productRepository = productRepository;
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
	}

	//주문 생성
	public void createOrder(OrderRequest orderRequests) {
		Member currentMember = memberService.getCurrentMember();

		Order order = Order.createOrder(currentMember, orderRequests.getDeliveryAddress());

		List<OrderItem> orderItems = new ArrayList<>();

		for (CartItemDto cartItemDto : orderRequests.getCartItems()) {
			Product product = productRepository.findById(cartItemDto.getProductId())
				.orElseThrow(() -> new IllegalArgumentException("제품 정보가 없습니다."));
			//제품 수량 체크
			product.decreaseInventory(cartItemDto.getQuantity());
			OrderItem orderItem = OrderItem.createOrderItem(order, product, cartItemDto.getQuantity());
			orderItems.add(orderItem);
		}
		order.addOrderItems(orderItems);
		orderRepository.save(order);
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
		Cart cart = cartRepository.findByMember(member)
			.orElseGet(() -> cartRepository.save(Cart.createCart(member)));

		Product product = productRepository.findById(orderItemRequest.getProductId())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

		CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
			.orElse(null);

		if (cartItem == null) {
			cartItem = CartItem.createCartItem(cart, product, orderItemRequest.getQuantity());
			cartItemRepository.save(cartItem);
		} else {
			cartItem.addQuantity(orderItemRequest.getQuantity());
		}
	}
	//회원의 장바구니가 비었다면 장바구니 생성 아니라면 갖고 오기

	// 	Product product = orderItemRequest.getProduct();
	// 	log.info("product******{}", product);
	// 	//카트에 담긴 상품 조회
	// 	CartItem cartItem = cartItemRepository.findByCartIdAndProduct(cart.getId(), product)
	// 		.orElse(null);
	// 	//없다면 추가해주고, 있을땐 수량만큼 추가
	// 	if (cartItem == null) {
	// 		cartItem = CartItem.builder()
	// 			.cartId(cart.getId())
	// 			.product(product)
	// 			.quantity(orderItemRequest.getQuantity())
	// 			.build();
	// 		cartItemRepository.save(cartItem);
	// 	} else {
	// 		cartItem.setQuantity(cartItem.getQuantity() + orderItemRequest.getQuantity());
	// 		cartItemRepository.save(cartItem);
	// 	}
	// }

	//장바구니 리스트 조회
	public List<CartItem> getCart() {
		Member member = memberService.getCurrentMember();
		Cart cart = cartRepository.findByMember(member)
			.orElseGet(() -> cartRepository.save(Cart.createCart(member)));

		List<CartItem> cartItems = cartItemRepository.findByCart(cart)
			.orElse(new ArrayList<CartItem>());

		return cartItems;
	}

}
