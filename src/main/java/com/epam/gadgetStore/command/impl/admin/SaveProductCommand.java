package com.epam.gadgetStore.command.impl.admin;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.gadgetStore.command.Command;
import com.epam.gadgetStore.dao.ProductDAO;
import com.epam.gadgetStore.dao.factory.DAOFactory;
import com.epam.gadgetStore.dto.ProductDTO;
import com.epam.gadgetStore.entity.Brand;
import com.epam.gadgetStore.entity.Category;
import com.epam.gadgetStore.entity.Product;
import com.epam.gadgetStore.validation.ProductFormValidator;

import static com.epam.gadgetStore.constants.AttributeConstants.PRODUCT_ATTRIBUTE;
import static com.epam.gadgetStore.constants.MessagesConstants.PRODUCT_CREATED_MESSAGE;
import static com.epam.gadgetStore.constants.MessagesConstants.PRODUCT_UPDATED_MESSAGE;
import static com.epam.gadgetStore.constants.PageNameConstants.ADMIN_MESSAGE_JSP;
import static com.epam.gadgetStore.constants.PageNameConstants.PRODUCT_FORM_JSP;
import static com.epam.gadgetStore.constants.ParameterNamesConstants.*;
import static com.epam.gadgetStore.util.CommandUtils.forwardToPage;
import static com.epam.gadgetStore.util.CommandUtils.getDataForProductForm;

public class SaveProductCommand implements Command {
	ProductDAO productDAO = (ProductDAO) DAOFactory.PRODUCT_DAO.getDAO();
	private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		ProductDTO productDTO = createProductDTOFromRequest(request);
		Set<String> validationErrorAttributes = ProductFormValidator.getInstance().getErrorAttributes(productDTO);

		if (validationErrorAttributes.size() > 0) {
			validationErrorAttributes.forEach(attr -> request.setAttribute(attr, true));
			request.setAttribute(PRODUCT_ATTRIBUTE, mapProductDTOToProduct(productDTO));
			getDataForProductForm(request);
			LOGGER.debug("Values in one or more fields are invalid.");
			forwardToPage(PRODUCT_FORM_JSP, request, response);
			return;
		}

		String message = PRODUCT_CREATED_MESSAGE;

		if (productDTO.getId().isBlank()) {
			Long productId = productDAO.add(mapProductDTOToProduct(productDTO));
			LOGGER.debug("Product ID " + productId + " has been created");
		} else {
			productDAO.update(mapProductDTOToProduct(productDTO));
			LOGGER.debug("Product " + productDTO.getId() + " has been updated");
			message = PRODUCT_UPDATED_MESSAGE;
		}
		forwardToPage(ADMIN_MESSAGE_JSP, message, request, response);
	}
	
	private Product mapProductDTOToProduct (ProductDTO productDTO) throws IOException {
		Product product = new Product();
		if (!productDTO.getId().isBlank())
			product.setId(Long.parseLong(productDTO.getId()));
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setPrice(Float.parseFloat(productDTO.getPrice()));
		
		long size = productDTO.getImagePart().getSize();
		byte[] imageBytes = new byte[(int) size];

		InputStream inputStream = productDTO.getImagePart().getInputStream();
		inputStream.read(imageBytes);
		inputStream.close();

		product.setImage(imageBytes);
		
		product.setCategory(new Category(Long.parseLong(productDTO.getCategoryId())));
		product.setBrand(new Brand(Long.parseLong(productDTO.getBrandId())));
		
		return product;
	}
	
	private ProductDTO createProductDTOFromRequest(HttpServletRequest request) 
			throws IOException, ServletException {
		ProductDTO productDTO = new ProductDTO();
		if (request.getParameter(ID_PARAMETER) != null)
			productDTO.setId(request.getParameter(ID_PARAMETER));
		
		productDTO.setName(request.getParameter(NAME_PARAMETER));
		productDTO.setDescription(request.getParameter(DESCRIPTION_PARAMETER));
		productDTO.setPrice(request.getParameter(PRICE_PARAMETER));
		Part imagePart = request.getPart(PRODUCT_IMAGE_PARAMETER);

		if (imagePart != null && imagePart.getSize() > 0) {
			productDTO.setImagePart(imagePart);
		}
		
		productDTO.setCategoryId(request.getParameter(CATEGORY_PARAMETER));
		productDTO.setBrandId(request.getParameter(BRAND_PARAMETER));
		
		return productDTO;
	}
}
