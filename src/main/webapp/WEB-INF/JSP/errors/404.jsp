<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@  taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:bundle basename="language" prefix="error." >
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Page Not Found Error</title>
</head>
<body>
<div align="center">
	<div>
		<img src="${pageContext.request.contextPath}/images/StoreLogo.jpg" />
	</div>
			<div>
			<h1>
				<fmt:message key="oops" />
			</h1>
			<h2>
				<fmt:message key="404.title" />
			</h2>
			<div>
				<fmt:message key="404.message" />
			</div>
			<div>
				<a href="<c:url value="/"/>" >
                  <fmt:message key="button.takeMeHome"/>
                </a>
			</div>
		</div>
	
</div>
</body>
</html>
</fmt:bundle>