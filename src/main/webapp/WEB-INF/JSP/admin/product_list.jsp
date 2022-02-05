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
<title>Manage Books - NOOR Accessories Administration</title>
<link rel="stylesheet" href="<c:url value="/css/style.css"/>" >  
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	<div align="center">
		<h2 class="pageheading" ><fmt:message key="admin.products.management"/></h2>
		<h3>
			<a href="<c:url value="/controller?command=new_product"/>"><fmt:message key="admin.create.new.product"/></a>
		</h3>
	</div>
	
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
				<th><fmt:message key="admin.table.image"/></th>
				<th><fmt:message key="admin.table.name"/></th>
				<th><fmt:message key="admin.table.category"/></th>
				<th><fmt:message key="admin.table.brand"/></th>
				
				<th><fmt:message key="admin.table.price"/></th>
				
				<th><fmt:message key="admin.table.actions"/></th>
			</tr>
			<c:forEach var="product" items="${listProduct}" varStatus="status">
			
			<!-- set up a link to edit a product -->
					<c:url var="editLink" value="controller">
						<c:param name="command" value="edit_product" />
						<c:param name="id" value="${product.id}" />
					</c:url>
					
					<!-- set up a link to delete a product -->
					<c:url var="deleteLink" value="controller">
						<c:param name="command" value="delete_product" />
						<c:param name="id" value="${product.id}" />
					</c:url>
					
				<tr>
					<td>${status.index + 1}</td>
					<td>${product.id}</td>
					<td>
					<img src="data:image/jpg;base64,${product.base64Image}" height="50" />
					</td>
					<td>${product.name}</td>
					<td>${product.category.name}</td>
					<td>${product.brand.name}</td>
					
					<td><fmt:formatNumber value="${product.price}"
										type="currency" currencySymbol="₸" maxFractionDigits="0"/></td>
					
					<td>
					<a href="${editLink}"><fmt:message key="admin.action.edit"/></a> &nbsp; 
					<a href="${deleteLink}" onclick="if (!(confirm('<fmt:message key="confirm.delete.product"/>'))) return false"><fmt:message key="admin.action.delete"/></a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<jsp:directive.include file="footer.jsp" />
	
	<script>
		$(document).ready(function() {
			$(".deleteLink").each(function() {
				$(this).on("click", function() {
					userId = $(this).attr("id");
					if (confirm('Are you sure you want to delete the book with ID ' +  bookId + '?')) {
						window.location = 'delete_book?id=' + bookId;
					}					
				});
			});
		});
	</script>
</body>
</html>