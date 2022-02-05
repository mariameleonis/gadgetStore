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
		<h1 class="pageheading"><fmt:message key="admin.label.dashboard"/></h1>
	
	<hr width="60%" />
	
		<h2 class="pageheading"><fmt:message key="admin.label.actions"/></h2>
		<b> <a href="<c:url value="/controller?command=new_product"/>"><fmt:message key="admin.action.new.product"/></a>  &nbsp; 
			<a href="<c:url value="/controller?command=new_category"/>"><fmt:message key="admin.action.new.category"/></a> 
		</b>
	
	</div>
	
	<jsp:directive.include file="footer.jsp" />
</body>
</html>