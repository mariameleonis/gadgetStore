<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@  taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><fmt:message key="orders.history"/> | NOOR Accessories</title>
<link rel="stylesheet" href="<c:url value="/css/style.css"/>">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Open+Sans+Condensed:wght@700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	
	<div align="center">
		<h2 class="pageheading"><fmt:message key="orders.history"/></h2>		
	
	
	<c:if test="${fn:length(listOrders) == 0}">
		<div align="center">
			<h3><fmt:message key="no.orders.placed"/></h3>
		</div>
	</c:if>
	
	<c:if test="${fn:length(listOrders) > 0}">
	<div align="center">
		<table border="1" cellpadding="5">
			<tr>
				<th><fmt:message key="admin.table.index"/></th>
				<th><fmt:message key="admin.table.id"/></th>
				<th><fmt:message key="admin.table.total.amount"/></th>
				<th><fmt:message key="admin.table.order.date"/></th>
				<th><fmt:message key="admin.table.status"/></th>
				<th><fmt:message key="admin.table.actions"/></th>
			</tr>
			<c:forEach var="order" items="${listOrders}" varStatus="status">
			<tr>
				<td>${status.index + 1}</td>
				<td>${order.id}</td>				
				<td><fmt:formatNumber value="${order.total}" type="currency" currencySymbol="₸" maxFractionDigits="0" /></td>				
				<td><fmt:formatDate type = "both" 
         dateStyle = "long" timeStyle = "short" value = "${order.orderDate}" /> </td>
				<td><fmt:message key="order.status.${fn:toLowerCase(order.status)}"/></td>
				<td>
					<a href="<c:url value="/controller?command=view_order_details&id=${order.id}"/>"><fmt:message key="admin.action.view.details"/></a> &nbsp;  
				</td>
			</tr>
			</c:forEach>
		</table>
	</div>
	</c:if>
</div>
<jsp:directive.include file="footer.jsp" />
</body>
</html>