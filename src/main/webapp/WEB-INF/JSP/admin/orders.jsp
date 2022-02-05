<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@  taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><fmt:message key="admin.order.management"/> | NOOR Accessories Administration</title>
<link rel="stylesheet" href="<c:url value="/css/style.css"/>" >  
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	<div align="center">
		<h2><fmt:message key="admin.order.management"/></h2>
	
	
	<c:if test="${message != null}">
		<div align="center">
			<h4 class="message">
				<i><fmt:message key="${message}">
				<fmt:param value="${messageParameter}"/>
				</fmt:message>
				</i>
			</h4>
		</div>
	</c:if>
	
	
	<table border="1" cellpadding="5">
			<tr>
				<th><fmt:message key="admin.table.index"/></th>
				<th><fmt:message key="admin.table.id"/></th>
				<th><fmt:message key="admin.table.ordered.by"/></th>
				<th><fmt:message key="admin.table.total.amount"/></th>
				<th><fmt:message key="admin.table.status"/></th>
				<th><fmt:message key="admin.table.order.date"/></th>
				<th><fmt:message key="admin.table.actions"/></th>
			</tr>
			<c:forEach var="order" items="${listOrders}" varStatus="status">
			<tr>
				<td>${status.index + 1}</td>
				<td>${order.id}</td>
				<td>${order.user.firstName} ${order.user.lastName}</td>
				<td><fmt:formatNumber value="${order.total}" type="currency" currencySymbol="₸" maxFractionDigits="0"/></td>
				<td><fmt:message key="order.status.${fn:toLowerCase(order.status)}"/></td>
				<td><fmt:formatDate type = "both" 
         dateStyle = "long" timeStyle = "short" value = "${order.orderDate}" /> </td>
				
				
				<td>
					<a href="<c:url value="/controller?command=view_order&id=${order.id}"/>"><fmt:message key="admin.action.view.details"/></a> &nbsp;
					
				</td>
			</tr>
			</c:forEach>
		</table>
	</div>

	<jsp:directive.include file="footer.jsp" />
	
	
</body>
</html>