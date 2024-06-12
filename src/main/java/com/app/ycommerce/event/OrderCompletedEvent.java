package com.app.ycommerce.event;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderCompletedEvent {
	int cartId;
}
