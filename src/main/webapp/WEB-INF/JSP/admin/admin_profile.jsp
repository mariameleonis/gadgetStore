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
<title>NOOR Accessories Administration</title>
<link rel="stylesheet" href="<c:url value="/css/style.css"/>">
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	<div align="center">
	
	<h2><fmt:message key="admin.menu.profile"/></h2>
	
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
	
	<table class="normal">
			<tr>
				<td><b><fmt:message key="form.user.email"/>:</b></td>
				<td>${loggedUser.email}</td>
			</tr>
			<tr>
				<td><b><fmt:message key="form.user.firstname"/>:</b></td>
				<td>${loggedUser.firstName}</td>
			</tr>	
			<tr>
				<td><b><fmt:message key="form.user.lastname"/>:</b></td>
				<td>${loggedUser.lastName}</td>
			</tr>		
			<tr>
				<td><b><fmt:message key="form.user.phonenumber"/>:</b></td>
				<td>${loggedUser.phoneNumber}</td>
			</tr>					
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td colspan="2" align="center"><b><a href="<c:url value="/controller?command=edit_admin_profile"/>"><fmt:message key="action.edit.profile"/></a></b></td>
			</tr>		
		</table>
		</div>
	<jsp:directive.include file="footer.jsp" />
</body>
</html>