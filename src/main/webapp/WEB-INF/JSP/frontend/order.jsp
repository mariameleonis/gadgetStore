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
<title><fmt:message key="your.order.id" />: ${order.id} | NOOR
	Accessories</title>
<link rel="stylesheet" href="<c:url value="/css/style.css"/>">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Open+Sans+Condensed:wght@700&family=Roboto:wght@400;700&display=swap"
	rel="stylesheet">
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<c:if test="${order == null}">
		<div align="center">
			<h2 class="pageheading">
				<fmt:message key="not.authorized.view.order" />
			</h2>
		</div>>
	</c:if>

	<c:if test="${order != null}">
		<div align="center">

			<c:if test="${message != null}">
				<h4 class="message">
					<fmt:message key="${message}" />
				</h4>
			</c:if>

			<h2 class="pageheading">
				<fmt:message key="your.order.id" />
				: ${order.id}
			</h2>


			<div align="center">
				<table class="normal">
					<tr>
						<td><b><fmt:message key="admin.table.status" />: </b></td>
						<td><fmt:message
								key="order.status.${fn:toLowerCase(order.status)}" /></td>
					</tr>
					<tr>
						<td><b><fmt:message key="admin.table.order.date" />: </b></td>
						<td><fmt:formatDate type="both" dateStyle="long"
								timeStyle="short" value="${order.orderDate}" /></td>
					</tr>
					<tr>
						<td><b><fmt:message key="admin.table.total.amount" />: </b></td>
						<td><fmt:formatNumber value="${order.total}" type="currency"
								currencySymbol="₸" maxFractionDigits="0" /></td>
					</tr>
					<tr>
						<td><b><fmt:message key="admin.table.contact.phone" />: </b></td>
						<td>${order.shippingPhone}</td>
					</tr>
					<tr>
						<td><b><fmt:message key="admin.table.ship.to" /></b></td>
						<td>${order.shippingAddress}</td>
					</tr>


				</table>
			</div>
			<div align="center">
				<h2>
					<fmt:message key="ordered.items" />
				</h2>
				<table border="1">
					<tr>
						<th><fmt:message key="admin.table.index" /></th>
						<th><fmt:message key="admin.table.name" /></th>
						<th><fmt:message key="admin.table.brand" /></th>
						<th><fmt:message key="admin.table.price" /></th>
						<th><fmt:message key="admin.table.quantity" /></th>
						<th><fmt:message key="admin.table.subtotal" /></th>
					</tr>
					<c:forEach items="${orderItems}" var="orderItem" varStatus="status">
						<tr>
							<td>${status.index + 1}</td>
							<td><img style="vertical-align: middle;"
								src="data:image/jpg;base64,${orderItem.product.base64Image}"
								width="48" /> ${orderItem.product.name}</td>
							<td>${orderItem.product.brand.name}</td>
							<td><fmt:formatNumber value="${orderItem.product.price}"
									type="currency" currencySymbol="₸" maxFractionDigits="0" /></td>
							<td>${orderItem.quantity}</td>
							<td><fmt:formatNumber value="${orderItem.subtotal}"
									type="currency" currencySymbol="₸" maxFractionDigits="0" /></td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="4" align="right"><b><i><fmt:message
										key="admin.table.total" />:</i></b></td>
						<td><b><c:set var="total" value="${0}" /> <c:forEach
									var="orderItem" items="${orderItems}">
									<c:set var="total" value="${total + orderItem.quantity }" />
								</c:forEach> <c:out value="${total}" /> <fmt:message
									key="admin.table.items" /> </b></td>
						<td><b><fmt:formatNumber value="${order.total}"
									type="currency" currencySymbol="₸" maxFractionDigits="0" /></b></td>
					</tr>
				</table>
			</div>
		</div>
	</c:if>
	<br />
	<br />
	<jsp:directive.include file="footer.jsp" />
</body>
</html>