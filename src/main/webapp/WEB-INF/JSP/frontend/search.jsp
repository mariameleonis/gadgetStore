<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@  taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><fmt:message key="search.results.for"/> "${keyword}"</title>
<link rel="stylesheet" href="<c:url value="/css/style.css"/>">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Open+Sans+Condensed:wght@700&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Open+Sans+Condensed:wght@700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	
	<div class="center">
		<c:if test="${fn:length(searchResults) == 0}">
			<h2><fmt:message key="no.search.results.for"/> "${keyword}"</h2>
		</c:if>
		<c:if test="${fn:length(searchResults) > 0}">
			<div class="book-group">
			<h2><fmt:message key="search.results.for"/> "${keyword}":</h2>
				<c:forEach items="${searchResults}" var="product">
					<div>
						<div id="search-image">
							<div>
								<a href="<c:url value="/controller?command=view_product&id=${product.id}"/>"> 
								<img class="book-small" src="data:image/jpg;base64,${product.base64Image}" />
								</a>
							</div>
						</div>
						<div id="search-description">
							<div>
								<h2><a href="<c:url value="/controller?command=view_product&id=${product.id}"/>"> <b>${product.name}</b></a></h2>
							</div>
							
							<div>
								<i>${product.category.itemTitle} <fmt:message key="by"/> ${product.brand.name}</i>
							</div>
							<div>
								<p>${fn:substring(product.description, 0, 100)}...</p>
							</div>					
						</div>
						<div id="search-price">
							<h3><fmt:formatNumber value="${product.price}" type="currency"
				currencySymbol="₸" maxFractionDigits="0" /></h3>
							<h3><a href="<c:url value="/controller?command=add_to_cart&id=${product.id}"/>"><fmt:message key="add.to.cart"/> </a></h3>
						</div>
					</div>
				</c:forEach>
			</div>
		</c:if>
	</div>
<jsp:directive.include file="footer.jsp" />
</body>
</html>