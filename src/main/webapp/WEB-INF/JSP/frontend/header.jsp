<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@  taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>
<div class="center">
	<div>
		<a href="${pageContext.request.contextPath}/">
			<img src="<c:url value="/images/StoreLogo.jpg"/>" />
		</a>
	</div>
	
	<div >
		<form action="<c:url value="/controller?command=search"/>" method="post" id="searchForm">
			<input type="text" name="keyword" id="keyword" size="50" />
			<input type="button" onclick="if (document.getElementById('keyword').value.length != 0) document.getElementById('searchForm').submit()" value="<fmt:message key="top.menu.search"/>" />		
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<c:if test="${sessionScope.language != 'en_US'}">
		<a href="<c:url value="/controller?command=change_language&languageToChange=en_US"/>">[EN] </a> 
		</c:if>
		<c:if test="${sessionScope.language != 'ru_RU'}">
		<a href="<c:url value="/controller?command=change_language&languageToChange=ru_RU"/>">[RU] </a></c:if>
		|
			<c:if test="${loggedUser == null}">
				<a href="<c:url value="/controller?command=show_customer_login_form"/>"><fmt:message key="top.menu.sign.in"/></a> |
				<a href="<c:url value="/controller?command=show_register_form"/>"><fmt:message key="top.menu.register"/></a> |	
				<a href="<c:url value="/controller?command=view_shopping_cart"/>"><fmt:message key="top.menu.cart"/></a>		
			</c:if>
			
			<c:if test="${loggedUser != null}">
				<fmt:message key="admin.welcome"/>, ${loggedUser.email}! |
				<c:if test="${!loggedUser.isAdmin}"><a href="<c:url value="/controller?command=show_customer_profile"/>"><fmt:message key="top.menu.profile"/></a> |
				<a href="<c:url value="/controller?command=view_shopping_cart"/>"><fmt:message key="top.menu.cart"/></a> |
				</c:if>
				<c:if test="${loggedUser.isAdmin}"><a href="<c:url value="/controller?command=get_admin_page"/>"><fmt:message key="top.menu.admin.dashboard"/></a> |</c:if>
				<a href="<c:url value="/controller?command=logout"/>"><fmt:message key="admin.logout"/></a> 
			</c:if>
			
		</form>
	</div>
	<div>&nbsp;</div>
	<div class="nav">
	<ul class="topmenu">
		<c:forEach var="category" items="${requestScope.categoriesTopMenu.get(0)}" varStatus="status">
			
			<li><a href="<c:url value="/controller?command=view_products_by_category&id=${category.id}"/>">
				<c:out value="${category.name}" />
			</a>
				<c:if test="${not empty requestScope.categoriesTopMenu.get(category.id)}">
				<ul class="submenu">
				<c:forEach var="category_child" items="${requestScope.categoriesTopMenu.get(category.id)}" varStatus="status">
				<li><a href="<c:url value="/controller?command=view_products_by_category&id=${category_child.id}"/>">
				<c:out value="${category_child.name}" />
			</a> <c:if test="${not empty requestScope.categoriesTopMenu.get(category_child.id)}">
				<ul class="submenu">
				<c:forEach var="category_grandchild" items="${requestScope.categoriesTopMenu.get(category_child.id)}" varStatus="status">
				<li><a href="<c:url value="/controller?command=view_products_by_category&id=${category_grandchild.id}"/>">
				<c:out value="${category_grandchild.name}" />
			</a>
				</li>
				</c:forEach>
				</ul>
			</c:if>
				</li>
				</c:forEach>
				</ul>
			</c:if>
			</li>
			
			
			<c:if test="${not status.last}">
			
			</c:if>
		</c:forEach>
		</ul>
	</div>
</div>