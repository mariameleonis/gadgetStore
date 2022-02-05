package com.epam.gadgetStore.validation;

import static com.epam.gadgetStore.constants.AttributeConstants.*;
import static com.epam.gadgetStore.constants.RegexConstants.MAX_LENGTH_ADDRESS;
import static com.epam.gadgetStore.constants.RegexConstants.PHONE_NUMBER_REGEX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.epam.gadgetStore.entity.Order;
import com.epam.gadgetStore.enums.OrderStatus;

public class UpdateOrderFormValidator implements FormValidator<Order, String> {
	private static UpdateOrderFormValidator instance = new UpdateOrderFormValidator();
	private UpdateOrderFormValidator() {}

	@Override
	public Set<String> getErrorAttributes(Order order) {
		Set<String> errorAttributes = new HashSet<>();
		
		if (!order.getShippingPhone().matches(PHONE_NUMBER_REGEX))
			errorAttributes.add(WRONG_PHONE_ATTRIBUTE);
		
		String address = order.getShippingAddress();
		
		if (address.isEmpty() || address.length() > MAX_LENGTH_ADDRESS)
			errorAttributes.add(WRONG_ADDRESS_ATTRIBUTE);
		
		List<String> orderStatuses = new ArrayList<String>();
		Arrays.asList(OrderStatus.values()).forEach(el -> orderStatuses.add(el.name()));
		
		if (!orderStatuses.contains(order.getStatus()))
			errorAttributes.add(WRONG_ORDER_STATUS_ATTRIBUTE);
		
		return errorAttributes;
	}
	
	public static UpdateOrderFormValidator getInstance() {
		if (instance == null) {
			instance = new UpdateOrderFormValidator();
		}
		return instance;
	}
}
