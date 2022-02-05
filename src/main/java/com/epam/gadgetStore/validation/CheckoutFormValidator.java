package com.epam.gadgetStore.validation;

import static com.epam.gadgetStore.constants.AttributeConstants.*;
import static com.epam.gadgetStore.constants.RegexConstants.*;

import java.time.YearMonth;
import java.util.HashSet;
import java.util.Set;

import com.epam.gadgetStore.dto.CheckoutDTO;

public class CheckoutFormValidator implements FormValidator<CheckoutDTO, String> {

	private static CheckoutFormValidator instance = new CheckoutFormValidator();

	private CheckoutFormValidator() {

	}

	@Override
	public Set<String> getErrorAttributes(CheckoutDTO checkoutDTO) {
		Set<String> errorAttributes = new HashSet<>();
		
		if (!checkoutDTO.getPhone().matches(PHONE_NUMBER_REGEX))
			errorAttributes.add(WRONG_PHONE_ATTRIBUTE);
		
		String address = checkoutDTO.getAddress();
		
		if (address.isEmpty() || address.length() > MAX_LENGTH_ADDRESS)
			errorAttributes.add(WRONG_ADDRESS_ATTRIBUTE);
		
		String cardHolderName = checkoutDTO.getCardHolder();

		if (cardHolderName.length() < MIN_LENGTH_CARD_HOLDER || cardHolderName.length() >MAX_LENGTH_CARD_HOLDER)
			errorAttributes.add(WRONG_CARD_HOLDER_ATTRIBUTE);

		if (!cardHolderName.matches(CARD_HOLDER_REGEX))
			errorAttributes.add(WRONG_CARD_HOLDER_ATTRIBUTE);

		if (!checkoutDTO.getCardNumber().matches(CARD_NUMBER_REGEX))
			errorAttributes.add(WRONG_CARD_NUMBER_ATTRIBUTE);

		if (!checkoutDTO.getSecurityCode().matches(SECURITY_CODE_REGEX))
			errorAttributes.add(WRONG_SECURITY_CODE_ATTRIBUTE);

		YearMonth yearMonth = YearMonth.parse(checkoutDTO.getExpirationDate());

		if (yearMonth.isBefore(YearMonth.now()))
			errorAttributes.add(WRONG_DATE_ATTRIBUTE);

		return errorAttributes;
	}

	public static CheckoutFormValidator getInstance() {
		if (instance == null) {
			instance = new CheckoutFormValidator();
		}
		return instance;
	}

}
