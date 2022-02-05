<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NOOR Accessories Administration</title>
<link rel="stylesheet" href="<c:url value="/css/style.css"/>" >  
</head>
<body>
<jsp:directive.include file="header.jsp" />
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
<jsp:directive.include file="footer.jsp" />
</body>
</html>