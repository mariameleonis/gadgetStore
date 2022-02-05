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
<title><fmt:message key="top.menu.profile" /> | NOOR Accessories
	Online Shop</title>
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

		<h2>
			<fmt:message key="user.profile" />
		</h2>

		<c:if test="${message != null}">
			<div align="center">
				<h4 class="message">
					<i><fmt:message key="${message}">
							<fmt:param value="${messageParameter}" />
						</fmt:message> </i>
				</h4>
			</div>
		</c:if>

		<table class="normal">
			<tr>
				<td align="right"><b><fmt:message key="form.user.email" />:</b></td>
				<td align="left">${loggedUser.email}</td>
			</tr>
			<tr>
				<td align="right"><b><fmt:message key="form.user.firstname" />:</b></td>
				<td align="left">${loggedUser.firstName}</td>
			</tr>
			<tr>
				<td align="right"><b><fmt:message key="form.user.lastname" />:</b></td>
				<td align="left">${loggedUser.lastName}</td>
			</tr>
			<tr>
				<td align="right"><b><fmt:message
							key="form.user.phonenumber" />:</b></td>
				<td align="left">${loggedUser.phoneNumber}</td>
			</tr>
			<tr>
				<td align="right"><b><fmt:message key="form.user.orders" /></b></td>
				<td align="left"><a
					href="<c:url value="/controller?command=view_orders_history"/>"><fmt:message
							key="form.user.orders.history" /></a></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><b><a
						href="<c:url value="/controller?command=edit_customer_profile"/>"><fmt:message
								key="action.edit.profile" /></a></b></td>
			</tr>
		</table>
	</div>
	<jsp:directive.include file="footer.jsp" />
</body>
</html>