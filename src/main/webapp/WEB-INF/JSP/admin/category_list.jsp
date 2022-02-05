<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Manage Categories - NOOR Accessories Administration</title>
<link rel="stylesheet" href="<c:url value="/css/style.css"/>" >  
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	<div align="center">
		<h2 class="pageheading"><fmt:message key="admin.categories.management"/></h2>
		<h3>
			<a href="<c:url value="/controller?command=new_category"/>"><fmt:message key="admin.create.new.category"/></a>
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
			
				<th><fmt:message key="admin.table.id"/></th>
				<th><fmt:message key="admin.table.name"/></th>
			
				<th><fmt:message key="admin.table.actions"/></th>
				
			</tr>
			<c:forEach var="cat" items="${requestScope.categoriesTopMenu.get(0)}" varStatus="status">
			
					<!-- set up a link to edit a category -->
					<c:url var="editLink" value="controller">
						<c:param name="command" value="edit_category" />
						<c:param name="id" value="${cat.id}" />
					</c:url>
					
					<!-- set up a link to delete a category -->
					<c:url var="deleteLink" value="controller">
						<c:param name="command" value="delete_category" />
						<c:param name="id" value="${cat.id}" />
					</c:url>
						
				<tr>
					
					<td>${cat.id}</td>
					<td><b>${cat.name}</b></td>
					
					<td>
					<a href="${editLink}"><fmt:message key="admin.action.edit"/></a> &nbsp; 
					<a href="${deleteLink}" onclick="if (!(confirm('<fmt:message key="confirm.delete.category"/>'))) return false"><fmt:message key="admin.action.delete"/></a>
						</td>
				</tr>
				<c:if test="${not empty requestScope.categoriesTopMenu.get(cat.id)}">
				
				<c:forEach var="cat_child" items="${requestScope.categoriesTopMenu.get(cat.id)}" >
				
					<!-- set up a link to edit a category -->
					<c:url var="editLink" value="controller">
						<c:param name="command" value="edit_category" />
						<c:param name="id" value="${cat_child.id}" />
					</c:url>
					
					<!-- set up a link to delete a category -->
					<c:url var="deleteLink" value="controller">
						<c:param name="command" value="delete_category" />
						<c:param name="id" value="${cat_child.id}" />
					</c:url>
					
					<tr>
					
					<td>${cat_child.id}</td>
					<td>— ${cat_child.name}</td>
					
					<td>
					<a href="${editLink}"><fmt:message key="admin.action.edit"/></a> &nbsp; 
					<a href="${deleteLink}" onclick="if (!(confirm('<fmt:message key="confirm.delete.category"/>'))) return false"><fmt:message key="admin.action.delete"/></a>
						</td>
				</tr>
			 <c:if test="${not empty requestScope.categoriesTopMenu.get(cat_child.id)}">
				
				<c:forEach var="cat_grandchild" items="${requestScope.categoriesTopMenu.get(cat_child.id)}" varStatus="status">
				
			<!-- set up a link to edit a category -->
					<c:url var="editLink" value="controller">
						<c:param name="command" value="edit_category" />
						<c:param name="id" value="${cat_grandchild.id}" />
					</c:url>
					
					<!-- set up a link to delete a category -->
					<c:url var="deleteLink" value="controller">
						<c:param name="command" value="delete_category" />
						<c:param name="id" value="${cat_grandchild.id}" />
					</c:url>
				<tr>
					
					<td>${cat_grandchild.id}</td>
					<td><i>— — ${cat_grandchild.name}</i></td>
					
					<td>
					<a href="${editLink}"><fmt:message key="admin.action.edit"/></a> &nbsp; 
					<a href="${deleteLink}" onclick="if (!(confirm('<fmt:message key="confirm.delete.category"/>'))) return false"><fmt:message key="admin.action.delete"/></a>
						</td>
				</tr>
				</c:forEach>
				
			</c:if>
			
				</c:forEach>
				
			</c:if>
			</c:forEach>
		</table>
	</div>

	<jsp:directive.include file="footer.jsp" />
	
</body>
</html>