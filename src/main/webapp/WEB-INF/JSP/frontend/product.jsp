<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${product.name}| NOOR Accessories</title>
<link rel="stylesheet" href="<c:url value="/css/style.css"/>">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Open+Sans+Condensed:wght@700&family=Roboto:wght@400;700&display=swap"
	rel="stylesheet">
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	<div class="center">
		<table class="book">
			<tr>
				<td colspan="3" align="left">
					<p id="book-title">${product.name}</p>
					${product.category.itemTitle} <fmt:message key="by" /> <span
					id="author">${product.brand.name}</span>
				</td>
			</tr>
			<tr>
				<td width="20%"><img class="book-large"
					src="data:image/jpg;base64,${product.base64Image}" /></td>
				<td id="description">${product.description}</td>
				<td valign="top" rowspan="2" width="20%">
					<h3>
						<fmt:formatNumber value="${product.price}" type="currency"
							currencySymbol="₸" maxFractionDigits="0" />
					</h3>

					<button
						onclick="window.location.href = '<c:url value="/controller?command=add_to_cart&id=${product.id}"/>';">
						<fmt:message key="add.to.cart" />
					</button>
				</td>
			</tr>
		</table>
	</div>
	<jsp:directive.include file="footer.jsp" />
</body>
</html>