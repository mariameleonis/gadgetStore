<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><fmt:message key="customer.details" /> | NOOR Accessories Administration</title>
<link rel="stylesheet" href="<c:url value="/css/style.css"/>">
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	<div align="center">
		<h2><fmt:message key="customer.details" /></h2>

		<c:if test="${message != null}">
			
				<h4 class="message">
					<i><fmt:message key="${message}">
							<fmt:param value="${messageParameter}" />
						</fmt:message> </i>
				</h4>
		
		</c:if>

		<table class="normal">
			<tr>
				<td><b><fmt:message key="admin.table.id"/>:</b></td>
				<td>${customer.id}</td>
			</tr>
			<tr>
				<td><b><fmt:message key="form.user.email" />:</b></td>
				<td>${customer.email}</td>
			</tr>
			<tr>
				<td><b><fmt:message key="form.user.firstname" />:</b></td>
				<td>${customer.firstName}</td>
			</tr>
			<tr>
				<td><b><fmt:message key="form.user.lastname" />:</b></td>
				<td>${customer.lastName}</td>
			</tr>
			<tr>
				<td><b><fmt:message key="form.user.phonenumber" />:</b></td>
				<td>${customer.phoneNumber}</td>
			</tr>
			<tr>
				<td><b><fmt:message key="admin.table.status"/>:</b></td>
				<td><fmt:message key="user.isblocked.${customer.isBlocked}" /></td>
			</tr>
			<tr>
				<td colspan="2" align="left"><b><a
						href="<c:url value="/controller?command=update_customer_status&id=${customer.id}"/>">
							<c:if test="${customer.isBlocked}"><fmt:message key="user.unblock"/></c:if>
					<c:if test="${!customer.isBlocked}"><fmt:message key="user.block"/></c:if>
					</a></b></td>
			</tr>
		</table>
		<h2><fmt:message key="orders.history"/></h2>

	
		
	

	<c:if test="${fn:length(listOrders) == 0}">
		
			<h3><fmt:message key="no.orders.placed"/></h3>
		
	</c:if>

	<c:if test="${fn:length(listOrders) > 0}">
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
						<td><fmt:formatNumber value="${order.total}" type="currency"
								currencySymbol="₸" maxFractionDigits="0" /></td>
						<td><fmt:formatDate type="both" dateStyle="long"
								timeStyle="short" value="${order.orderDate}" /></td>
						<td><fmt:message key="order.status.${fn:toLowerCase(order.status)}"/></td>
						<td><a
							href="<c:url value="/controller?command=view_order&id=${order.id}"/>"><fmt:message key="admin.action.view.details"/></a> &nbsp;</td>
					</tr>
				</c:forEach>
			</table>
		
	</c:if>
		</div>
	<jsp:directive.include file="footer.jsp" />
</body>
</html>