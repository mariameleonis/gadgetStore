<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@  taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>
<div align="center">
	<div><a href="<c:url value="/controller?command=get_admin_page"/>">
		<img src="<c:url value="/images/StoreLogo.jpg"/>" /> </a>
	</div>
	<div>
		<a href="<c:url value="/controller?command=show_admin_profile"/>"><fmt:message key="admin.welcome"/>, <c:out value="${sessionScope.loggedUser.email}" /></a> | 
		<c:if test="${sessionScope.language  ne 'en_US'}">
		<a href="<c:url value="/controller?command=change_language&languageToChange=en_US"/>">[EN]</a> 
		</c:if>
		<c:if test="${sessionScope.language  ne 'ru_RU'}">
		<a href="<c:url value="/controller?command=change_language&languageToChange=ru_RU"/>">[RU]</a></c:if> | 
		<a href="<c:url value="/controller?command=logout"/>"><fmt:message key="admin.logout"/></a>
	</div>
	<br />
	<div id="headermenu"> 
		<div>
			<a href="<c:url value="/controller?command=show_admin_profile"/>"> <img src="<c:url value="/images/users.png"/>" /><br><fmt:message key="admin.menu.profile"/>
			</a>
		</div>

		<div>
			<a href="<c:url value="/controller?command=list_category"/>"> <img src="<c:url value="/images/category.png" />" /><br><fmt:message key="admin.menu.categories"/>
			</a>
		</div>

		<div>
			<a href="<c:url value="/controller?command=list_products"/>"> <img src="<c:url value="/images/product.png" />" /><br><fmt:message key="admin.menu.products"/>
			</a>
		</div>

		<div>
			<a href="<c:url value="/controller?command=list_customers"/>"><img src="<c:url value="/images/customer.png" />" /><br><fmt:message key="admin.menu.customers"/></a>
		</div>

		<div>
			<a href="<c:url value="/controller?command=list_orders"/>"><img src="<c:url value="/images/order.png" />" /><br><fmt:message key="admin.menu.orders"/></a>
		</div>
	</div>
</div>
