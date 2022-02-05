package com.epam.gadgetStore.validation;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.Part;

import com.epam.gadgetStore.dto.ProductDTO;

public class ProductFormValidator implements FormValidator<ProductDTO, String> {
	private static final int MAX_LENGTH_PRODUCT_NAME = 100;
	private static final int MAX_LENGTH_DESCRIPTION = 1500;
	private static final int MIN_LENGTH_PRODUCT_NAME = 8;
	private static final int MIN_LENGTH_DESCRIPTION = 30;
	private static final float MIN_PRICE = 1;
	private static final float MAX_PRICE = 99999;
	private static final int MAX_IMAGE_SIZE = 1024 * 1024 * 5;
	private static final String JPEG_CONTENT_TYPE = "image/jpeg";
	private static final String JPG_CONTENT_TYPE = "image/jpg";
	private static final String PNG_CONTENT_TYPE = "image/png";
	public static final String WRONG_PRODUCT_NAME_ATTRIBUTE = "isWrongProductName";
	public static final String WRONG_DESCRIPTION_ATTRIBUTE = "isWrongDescription";
	public static final String WRONG_PRICE_ATTRIBUTE = "isWrongPrice";
	public static final String WRONG_IMAGE_ATTRIBUTE = "isWrongImage";

	private static ProductFormValidator instance = new ProductFormValidator();

	private ProductFormValidator() {
	}

	@Override
	public Set<String> getErrorAttributes(ProductDTO productDTO) throws IOException, ServletException {
		Set<String> errorAttributes = new HashSet<>();

		final String PRODUCT_NAME = productDTO.getName();
		final String DESCRIPTION = productDTO.getDescription();
		final String PRICE = productDTO.getPrice();
		Part IMAGE_PART = productDTO.getImagePart();

		if (PRODUCT_NAME.isBlank() || PRODUCT_NAME.length() < MIN_LENGTH_PRODUCT_NAME
				|| PRODUCT_NAME.length() > MAX_LENGTH_PRODUCT_NAME)
			errorAttributes.add(WRONG_PRODUCT_NAME_ATTRIBUTE);

		if (DESCRIPTION.isBlank() || DESCRIPTION.length() < MIN_LENGTH_DESCRIPTION
				|| DESCRIPTION.length() > MAX_LENGTH_DESCRIPTION)
			errorAttributes.add(WRONG_DESCRIPTION_ATTRIBUTE);

		try {
			float price = Float.parseFloat(PRICE);
			if (price < MIN_PRICE || price > MAX_PRICE)
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			errorAttributes.add(WRONG_PRICE_ATTRIBUTE);
		}

		if (IMAGE_PART != null) {
		if (!(IMAGE_PART.getSize() < MAX_IMAGE_SIZE
				&& (IMAGE_PART.getContentType().equals(JPEG_CONTENT_TYPE) || IMAGE_PART.getContentType().equals(JPG_CONTENT_TYPE)
						|| IMAGE_PART.getContentType().equals(PNG_CONTENT_TYPE)))) {
			errorAttributes.add(WRONG_IMAGE_ATTRIBUTE);
		}
		}

		return errorAttributes;
	}

	public static ProductFormValidator getInstance() {
		if (instance == null) {
			instance = new ProductFormValidator();
		}
		return instance;
	}
}
