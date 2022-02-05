<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@  taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><fmt:message key="order.overview"/> | NOOR Accessories Administration</title>
<link rel="stylesheet" href="<c:url value="/css/style.css"/>" >
<script type="text/javascript"
	src="<c:url value="/js/js-inputmask.js"/>"></script>  
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
	
	<div align="center">
		<h2><fmt:message key="order.overview"/>:</h2>	
		<form action="<c:url value="/controller?command=update_order"/>" method="post">
		<input type="hidden" name="id" value=${order.id} />
		<table class="normal">
		<tr>
				<td><b><fmt:message key="admin.table.id"/>: </b></td>
				<td>${order.id}</td>
			</tr>	
		<tr>
				<td><b><fmt:message key="admin.table.order.date"/>: </b></td>
				<td><fmt:formatDate type = "both" 
         dateStyle = "long" timeStyle = "short" value = "${order.orderDate}" /></td>
			</tr>	
			<tr>
				<td><b><fmt:message key="admin.table.ordered.by"/>: </b></td>
				<td>${order.user.firstName} ${order.user.lastName}</td>
			</tr>
			<tr>
				<td><b><fmt:message key="admin.table.total.amount"/>: </b></td>
				<td><fmt:formatNumber value="${order.total}" type="currency" currencySymbol="₸" maxFractionDigits="0"/></td>
			</tr>			
		
			<tr>
				<td><b><fmt:message key="admin.table.contact.phone"/>: </b></td>
				<td>
				<input type="tel" name="phonenumber" id="phonenumber"
								value="${order.shippingPhone}" required class="tel" size="20"  <c:if test="${order.status ne 'PROCESSING'}">readonly</c:if>  />
								<c:if test="${requestScope.isWrongPhone}">
                                            <label class="error" for="phonenumber"><fmt:message
                                                    key="checkout.error.phone"/></label>
                                        </c:if>
				</td>
			</tr>
		
			<tr>
				<td><b><fmt:message key="admin.table.ship.to"/>: </b></td>
				<td>
				<input type="text" name="address" id="address" required size="20" value="${order.shippingAddress}"  maxlength="255"  <c:if test="${order.status ne 'PROCESSING'}">readonly</c:if>  />
				<c:if test="${requestScope.isWrongAddress}">
                                            <label class="error" for="address"><fmt:message
                                                    key="checkout.error.address"/></label>
                                        </c:if>
				</td>
			</tr>
			<tr>
				<td><b><fmt:message key="admin.table.status"/>: </b></td>
				<td>
				<select name="order-status" id="order-status" required>
				<c:forEach var="status" items="${orderStatuses}">
				<option value="${status}" <c:if test="${order.status eq status}">selected="selected"</c:if> > <fmt:message key="order.status.${fn:toLowerCase(status)}"/></option>
				</c:forEach>
				</select>
				<c:if test="${requestScope.isWrongOrderStatus}">
                                            <label class="error" for="order-status"><fmt:message
                                                    key="checkout.error.order-status"/></label>
                                        </c:if>
				</td>
			</tr>
			<tr>
					<td colspan="2" align="center">
						<button type="submit">
							<fmt:message key="button.save" />
						</button>&nbsp;&nbsp;&nbsp;
					</td>
				</tr>											
		</table>
		</form>
	</div>
	
		<div align="center">
		<h2><fmt:message key="ordered.items"/></h2>
		<table border="1">
			<tr>
				<th><fmt:message key="admin.table.index"/></th>
				<th><fmt:message key="admin.table.name"/></th>
				<th><fmt:message key="admin.table.brand"/></th>
				<th><fmt:message key="admin.table.price"/></th>
				<th><fmt:message key="admin.table.quantity"/></th>
				<th><fmt:message key="admin.table.subtotal"/></th>
			</tr>
			<c:forEach items="${orderItems}" var="orderItem" varStatus="status">
			<tr>
				<td>${status.index + 1}</td>
				<td>
					<img style="vertical-align: middle;" src="data:image/jpg;base64,${orderItem.product.base64Image}" width="48" />
					${orderItem.product.name}
				</td>
				<td>${orderItem.product.brand.name}</td>
				<td><fmt:formatNumber value="${orderItem.product.price}" type="currency" currencySymbol="₸" maxFractionDigits="0" /></td>
				<td>${orderItem.quantity}</td>
				<td><fmt:formatNumber value="${orderItem.subtotal}" type="currency" currencySymbol="₸" maxFractionDigits="0" /></td>
			</tr>
			</c:forEach>
			<tr>
				<td colspan="4" align="right">
					<b><i><fmt:message key="admin.table.total"/>:</i></b>
				</td>
				<td>
					<b><c:set var="total" value="${0}"/>
					<c:forEach var="orderItem" items="${orderItems}">
   					 <c:set var="total" value="${total + orderItem.quantity }" />
						</c:forEach>
						<c:out value="${total}" /> <fmt:message key="admin.table.items"/>
					</b>
				</td>
				<td>
					<b><fmt:formatNumber value="${order.total}" type="currency" currencySymbol="₸" maxFractionDigits="0" /></b>
				</td>
			</tr>
		</table>
	</div>	
	
	<jsp:directive.include file="footer.jsp" />
	
	
</body>
</html>