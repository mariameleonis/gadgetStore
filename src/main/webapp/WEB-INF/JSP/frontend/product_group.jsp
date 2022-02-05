<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@  taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="book">
	<div>
		<a
			href="<c:url value="/controller?command=view_product&id=${product.id}"/>">
			<img class="book-small"
			src="data:image/jpg;base64,${product.base64Image}" />
		</a>
	</div>
	<div>
		<a
			href="<c:url value="/controller?command=view_product&id=${product.id}"/>"><b>${product.name}</b></a>
	</div>
	
	<div class="product-info">
	${product.category.itemTitle} <br>
		<i>${product.brand.name}</i>
	</div>
	<div>
		<b><fmt:formatNumber value="${product.price}" type="currency"
				currencySymbol="₸" maxFractionDigits="0" /></b>
	</div>
</div>