<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><fmt:message key="admin.users.management"/> | NOOR Accessories Administration</title>
<link rel="stylesheet" href="<c:url value="/css/style.css"/>" >  
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	<div align="center">
		<h2 class="pageheading" ><fmt:message key="admin.users.management"/></h2>
	
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
	<div align="center">
		<table border="1" cellpadding="5">
			<tr>
				<th><fmt:message key="admin.table.index"/></th>
				<th><fmt:message key="admin.table.id"/></th>
				<th><fmt:message key="admin.table.email"/></th>
				<th><fmt:message key="admin.table.fullname"/></th>
				<th><fmt:message key="form.user.phonenumber"/></th>
				<th><fmt:message key="admin.table.status"/></th>
				<th><fmt:message key="admin.table.actions"/></th>
			</tr>
			<c:forEach var="customer" items="${listCustomers}" varStatus="status">
			
			        <!-- set up a link to update customer status -->
					<c:url var="updateCustomerStatusLink" value="controller">
						<c:param name="command" value="update_customer_status" />
						<c:param name="id" value="${customer.id}" />
					</c:url>
					
					<!-- set up a link to view customer details -->
					<c:url var="viewCustomerDetailsLink" value="controller">
						<c:param name="command" value="view_customer_details" />
						<c:param name="id" value="${customer.id}" />
					</c:url>
					
				<tr>
					<td>${status.index + 1}</td>
					<td>${customer.id}</td>
					<td>${customer.email}</td>
					<td>${customer.firstName} ${customer.lastName}</td>
					<td>${customer.phoneNumber}</td>
					<td> <fmt:message key="user.isblocked.${customer.isBlocked}"/>
					
					</td>
					<td>
					<a href="${viewCustomerDetailsLink}"><fmt:message key="admin.action.view.details"/></a> &nbsp; 
					
					<a href="${updateCustomerStatusLink}" 
					<c:if test="${customer.isBlocked}">
					onclick="if (!(confirm('<fmt:message key="confirm.user.unblock"/>'))) return false"
					</c:if>
					<c:if test="${!customer.isBlocked}">
					onclick="if (!(confirm('<fmt:message key="confirm.user.block"/>'))) return false"
					</c:if>>
					<c:if test="${customer.isBlocked}"><fmt:message key="user.unblock"/></c:if>
					<c:if test="${!customer.isBlocked}"><fmt:message key="user.block"/></c:if>
					</a>
				
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
	<jsp:directive.include file="footer.jsp" />
	
</body>
</html>