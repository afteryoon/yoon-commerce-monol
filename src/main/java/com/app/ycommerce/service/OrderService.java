package com.app.ycommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.ycommerce.dto.CartItemDto;
import com.app.ycommerce.dto.OrderDto;
import com.app.ycommerce.entity.Address;
import com.app.ycommerce.entity.Cart;
import com.app.ycommerce.entity.CartItem;
import com.app.ycommerce.entity.Member;
import com.app.ycommerce.entity.Order;
import com.app.ycommerce.entity.OrderItem;
import com.app.ycommerce.entity.OrderStatus;
import com.app.ycommerce.entity.Product;
import com.app.ycommerce.repository.CartItemRepository;
import com.app.ycommerce.repository.CartRepository;
import com.app.ycommerce.repository.OrderItemRepository;
import com.app.ycommerce.repository.OrderRepository;
import com.app.ycommerce.repository.ProductRepository;
import com.app.ycommerce.request.CreateOrderRequest;
import com.app.ycommerce.request.OrderItemRequest;
import com.app.ycommerce.request.ProductAndCount;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class OrderService {

	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final MemberService memberService;
	private final ProductRepository productRepository;
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;

	public OrderService(OrderRepository orderRepository, MemberService memberService,
		ProductRepository productRepository, CartRepository cartRepository, CartItemRepository cartItemRepository,
		OrderItemRepository orderItemRepository) {
		this.orderRepository = orderRepository;
		this.memberService = memberService;
		this.productRepository = productRepository;
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
		this.orderItemRepository = orderItemRepository;
	}

	public List<Order> getOders() {
		Member member = memberService.getCurrentMember();
		List<Order> orders = orderRepository.findAllByMember(member);

		log.debug(" debug orders = {}", orders);

		return orders;
	}

	//주문 생성
	public Order addOrder(CreateOrderRequest createOrderRequest) {
		//객체 생성
		Member member = memberService.findById(createOrderRequest.getMemberId());
		List<OrderItem> orderItems = new ArrayList<>();

		for (ProductAndCount productAndCount : createOrderRequest.getProductAndCounts()) {
			Product product = productRepository.findById(productAndCount.getProductId()).orElseThrow();
			//재고 확인
			product.decreaseInventory(productAndCount.getQuantity());
			//주문 아이템 생성 및 저장
			OrderItem orderItem = OrderItem.createOrderItem(product, productAndCount.getQuantity(),
				productAndCount.getPrice());
			orderItems.add(orderItem);
			log.debug("orderItem = {}", orderItem);
			//주문에 추가
			//order.addOrderItem(orderItem);
			//가격 추가
			//order.sumPrice(productAndCount.getPrice() * productAndCount.getQuantity());
		}
		Order order = Order.createOrder(member, createOrderRequest.getAddress(), orderItems);
		log.debug("order = {}", order);

		return orderRepository.save(order);
	}

	public Order createOrder(OrderDto orderDto) {
		log.debug("orderDto = {}", orderDto);
		Member currentMember = memberService.getCurrentMember();
		Address deliveryAddress = orderDto.getAddress();
		//주문 아이템 만들기
		List<OrderItem> orderItems = new ArrayList<>();
		for (ProductAndCount productAndCount : orderDto.getProductAndCounts()) {
			Product product = productRepository.findById(productAndCount.getProductId())
				.orElseThrow(() -> new IllegalArgumentException("Product not found"));

			OrderItem orderItem = OrderItem.createOrderItem(product, productAndCount.getQuantity(), product.getPrice());
			orderItems.add(orderItem);
		}
		Order order = Order.createOrder(currentMember, deliveryAddress, orderItems);
		log.debug("order = {}", order);
		return orderRepository.save(order);
		//delCartItem(orderDto);
	}

	//장바구니 아이템 삭제
	public void delCartItem(OrderDto orderDto) {

		Member currentMember = memberService.getCurrentMember();
		//카트 갖고오기
		Cart cart = cartRepository.findByMember(currentMember).orElseThrow(() -> new RuntimeException("장바구니가 비었습니다."));
		//선택된 카트 상품 만큼 반복
		for (ProductAndCount productAndCount : orderDto.getProductAndCounts()) {

			Product product = productRepository.findById(productAndCount.getProductId()).orElse(null);
			CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
				.orElse(null);
			cartItemRepository.delete(cartItem);
		}

	}

	//장바구니 삭제
	@EventListener
	public void delCart(OrderDto orderDto) {
		Member currentMember = memberService.getCurrentMember();
		//카트 갖고오기
		Cart cart = cartRepository.findByMember(currentMember).orElseThrow(() -> new RuntimeException("장바구니가 비었습니다."));

		List<CartItem> orderItems = cartItemRepository.findByCart(cart).orElse(new ArrayList<CartItem>());

		for (CartItem cartItem : orderItems) {
			Product product = cartItem.getProduct();
			//제품 수량 체크
			product.decreaseInventory(cartItem.getQuantity());
		}

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
		//회원의 카트 갖고오기 없다면 만들기
		Cart cart = cartRepository.findByMember(member).orElseGet(() -> cartRepository.save(Cart.createCart(member)));
		//request 로 부터 받은 상품번호로 상품 정보 갖고오기
		Product product = productRepository.findById(orderItemRequest.getProductId())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
		//카트아이템에 해당 상품이 있는지 확인
		CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product).orElse(null);
		//없다면 상품 추가 있다면 원래 상품에서 수량 추가
		if (cartItem == null) {
			cartItem = CartItem.createCartItem(cart, product, orderItemRequest.getQuantity());
			cartItemRepository.save(cartItem);
		} else {
			cartItem.addQuantity(orderItemRequest.getQuantity());
		}
	}

	//장바구니 리스트 조회
	public List<CartItemDto> getCart() {
		Member member = memberService.getCurrentMember();
		//카트 불러오기
		Cart cart = cartRepository.findByMember(member).orElseGet(() -> cartRepository.save(Cart.createCart(member)));
		//카트에 담긴 제품 갖고오기
		List<CartItem> cartItems = cartItemRepository.findByCart(cart).orElse(new ArrayList<CartItem>());
		//카트 정보 파싱
		List<CartItemDto> CartItemDtos = cartItems.stream()
			.map(CartItemDto::mapToCartItemDto)
			.collect(Collectors.toList());

		return CartItemDtos;
	}

}
