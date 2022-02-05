<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="top.menu.cart"/> | NOOR Accessories</title>
<link rel="stylesheet" href="<c:url value="/css/style.css"/>">

	<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Open+Sans+Condensed:wght@700&family=Roboto:wght@400;700&display=swap" rel="stylesheet">
</head>
<body>
<jsp:directive.include file="header.jsp" />
	<div align="center">
		
		<h2><fmt:message key="top.menu.cart"/></h2>

		<c:if test="${message != null}">
			<div align="center">
				<h4 class="message">${message}</h4>
			</div>
		</c:if>	
		<c:set var="cart" value="${sessionScope['shoppingCart']}" /> 	
		<c:if test="${cart.totalItems == 0}">
		<h2><fmt:message key="empty.cart"/></h2>
		</c:if>
		<c:if test="${cart.totalItems > 0}">
		<div>
		<form action="<c:url value="/controller?command=update_cart"/>" method="post" id="cartForm">
		<div>
		<table>
	
		<tr>
		<th><fmt:message key="admin.table.index"/></th>
							<th colspan="2"><fmt:message key="admin.table.product.name"/></th>
							<th><fmt:message key="admin.table.quantity"/></th>
							<th><fmt:message key="admin.table.price"/></th>
							<th><fmt:message key="admin.table.subtotal"/></th>
							<th></th>
		</tr>
		<c:forEach items="${cart.items}" var="item" varStatus="status">
							<tr>
								<td>${status.index + 1}</td>
								<td><img class="book-small"
									src="data:image/jpg;base64,${item.key.base64Image}" /></td>
								<td><span id="book-title">${item.key.category.itemTitle}<br>${item.key.name}</span></td>
								<td>
									<input type="hidden" name="productId${status.index + 1}" value="${item.key.id}" />
									<input type="text" id="quantity${status.index + 1}" name="quantity${status.index + 1}" value="${item.value}" size="3" min="1" max="10" pattern="^([1-9]|1[0])$" required />
								
									
									</td>
									
								<td><fmt:formatNumber value="${item.key.price}"
										type="currency" currencySymbol="₸" maxFractionDigits="0"/></td>
								<td><fmt:formatNumber
										value="${item.value * item.key.price}" type="currency" currencySymbol="₸" maxFractionDigits="0" /></td>
								<td><a href="<c:url value="/controller?command=remove_from_cart&id=${item.key.id}"/>"><fmt:message key="remove.from.cart"/></a></td>
							</tr>
						</c:forEach>

						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td><b>${cart.totalQuantity}  <fmt:message key="admin.table.items"/></b></td>
							<td><fmt:message key="admin.table.total"/>:</td>
							<td colspan="2"><b><fmt:formatNumber
										value="${cart.totalCost}" type="currency" currencySymbol="₸" maxFractionDigits="0" /></b></td>
						</tr>
		</table>
			</div>
			<div>
					<table class="normal">
						<tr><td>&nbsp;</td></tr>
						<tr>
							<td></td>
							<td><button type="submit"><fmt:message key="update.cart"/></button></td>
							<td><button  onclick="window.location.href = '<c:url value="/controller?command=clear_cart"/>';"><fmt:message key="clear.cart"/></button></td>
							<td><a href="${pageContext.request.contextPath}/"><fmt:message key="continue.shopping"/></a></td>
							<td><a href="<c:url value="/controller?command=checkout"/>"><fmt:message key="checkout"/></a>
						</tr>
					</table>
				</div>
		</form>
		</div>
		</c:if>

		
	</div>
	<jsp:directive.include file="footer.jsp" />
	<script type="text/javascript">
	function setInputFilter(textbox, inputFilter) {
		[ "input", "keydown", "keyup", "mousedown", "mouseup", "select",
				"contextmenu", "drop" ].forEach(function(event) {
			textbox.addEventListener(event, function() {
				if (inputFilter(this.value)) {
					this.oldValue = this.value;
					this.oldSelectionStart = this.selectionStart;
					this.oldSelectionEnd = this.selectionEnd;
				} else if (this.hasOwnProperty("oldValue")) {
					this.value = this.oldValue;
					this.setSelectionRange(this.oldSelectionStart,
							this.oldSelectionEnd);
				} else {
					this.value = "";
				}
			});
		});
	}
	for (let i = 1; i <= <c:out value = "${cart.items.size()}" />; i++) { 
		setInputFilter(document.getElementById("quantity"+i), function(value) {
			return /^([1-9]|1[0]|\s*)$/.test(value);
		});
		}
	
	
	</script>
</body>
</html>