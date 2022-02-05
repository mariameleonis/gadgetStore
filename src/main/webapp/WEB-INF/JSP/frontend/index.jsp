<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@  taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NOOR Accessories Online Shop</title>
<link rel="stylesheet" href="<c:url value="/css/style.css"/>">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Open+Sans+Condensed:wght@700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	
	<div class="center">
		<div>
			<h2><fmt:message key="new.products"/>:</h2>
			<div class="book_group">
		<c:forEach items="${listNewProducts}" var="product">
				<jsp:directive.include file="product_group.jsp" />
			</c:forEach> 	
		</div></div>
		<div class="next-row">
			<h2><fmt:message key="bestselling.products"/>:</h2>
			<div class="book_group">
			<c:forEach items="${listBestSellingProducts}" var="product">
				<jsp:directive.include file="product_group.jsp" />
			</c:forEach>
		</div></div>
	</div>
<jsp:directive.include file="footer.jsp" />
</body>
</html>