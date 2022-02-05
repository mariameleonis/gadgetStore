<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="checkout.process" /> | NOOR Accessories</title>
<link rel="stylesheet" href="<c:url value="/css/style.css"/>">
<script type="text/javascript"
	src="<c:url value="/js/js-inputmask.js"/>"></script>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Open+Sans+Condensed:wght@700&family=Roboto:wght@400;700&display=swap"
	rel="stylesheet">
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	<div align="center">

		<c:set var="cart" value="${sessionScope['shoppingCart']}" />

		<c:if test="${cart.totalItems == 0}">
			<h2>
				<fmt:message key="empty.cart" />
			</h2>
		</c:if>
		<c:if test="${cart.totalItems > 0}">

			<h2>
				<fmt:message key="review.order.details" />
				| <a href="<c:url value="/controller?command=view_shopping_cart"/>"><fmt:message
						key="admin.action.edit" /></a>
			</h2>
			<div>
				<table>

					<tr>
						<th><fmt:message key="admin.table.index" /></th>
						<th colspan="2"><fmt:message key="admin.table.product.name" /></th>
						<th><fmt:message key="admin.table.quantity" /></th>
						<th><fmt:message key="admin.table.price" /></th>
						<th><fmt:message key="admin.table.subtotal" /></th>
						<th></th>
					</tr>
					<c:forEach items="${cart.items}" var="item" varStatus="status">

						<tr>
							<td>${status.index + 1}</td>
							<td><img class="book-small"
								src="data:image/jpg;base64,${item.key.base64Image}" /></td>
							<td><span id="book-title">${item.key.category.itemTitle}<br>${item.key.name}</span></td>
							<td><input type="hidden" name="productId${status.index + 1}"
								value="${item.key.id}" /> <input type="number"
								name="quantity${status.index + 1}" value="${item.value}"
								size="5" min="1" max="10" readonly /></td>
							<td><fmt:formatNumber value="${item.key.price}"
									type="currency" currencySymbol="₸" maxFractionDigits="0" /></td>
							<td><fmt:formatNumber value="${item.value * item.key.price}"
									type="currency" currencySymbol="₸" maxFractionDigits="0" /></td>

						</tr>

					</c:forEach>

					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td><b>${cart.totalQuantity} <fmt:message
									key="admin.table.items" /></b></td>
						<td><fmt:message key="admin.table.total" />:</td>
						<td colspan="2"><b><fmt:formatNumber
									value="${cart.totalCost}" type="currency" currencySymbol="₸"
									maxFractionDigits="0" /></b></td>
					</tr>
				</table>
				<h2>
					<fmt:message key="shipping.information" />
				</h2>
				<form action="<c:url value="/controller?command=place_order"/>"
					method="post">
					<table class="normal">
						<tr>
							<td><fmt:message key="admin.table.contact.phone" />:</td>
							<td><c:if test="${checkoutDTO == null}">
									<input type="tel" name="phonenumber"
										value="${loggedUser.phoneNumber}" required class="tel"
										size="20" />
								</c:if> <c:if test="${checkoutDTO != null}">
									<input type="tel" name="phonenumber"
										value="${sessionScope.checkoutDto.phone}" required class="tel"
										size="20" />
								</c:if> <c:if test="${requestScope.isWrongPhone}">
									<label class="error"><fmt:message
											key="checkout.error.phone" /></label>
								</c:if></td>
						</tr>
						<tr>
							<td><fmt:message key="admin.table.ship.to" />:</td>
							<td><input type="text" name="address" id="address" required
								size="20" value="${sessionScope.checkoutDto.address}"
								maxlength="255" /> <c:if test="${requestScope.isWrongAddress}">
									<label class="error"><fmt:message
											key="checkout.error.address" /></label>
								</c:if></td>
						</tr>

					</table>


					<h2>
						<fmt:message key="payment.details" />
					</h2>

					<table class="normal">
						<tr>
							<td><fmt:message key="cardholder.name" />:</td>
							<td><input type="text" name="card-holder" id="card-holder"
								size="20" minlength="3" maxlength="26" required
								pattern="^[a-zA-Z]+(?:[-\s][a-zA-Z]+){0,4}$"
								value="${sessionScope.checkoutDto.cardHolder}" /> <c:if
									test="${requestScope.isWrongCardHolder}">
									<label class="error"><fmt:message
											key="checkout.error.cardholder" /></label>
								</c:if></td>
						</tr>

						<tr>
							<td><fmt:message key="card.number" />:</td>
							<td><input type="text" name="card-number" maxlength="16"
								required
								pattern="^(?:4[0-9]{12}(?:[0-9]{3})?|(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]
			|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|6(?:011|5[0-9]{2})[0-9]{12}
			|(?:2131|1800|35\d{3})\d{11})$"
								value="${sessionScope.checkoutDto.cardNumber}" /> <c:if
									test="${requestScope.isWrongCardNumber}">
									<label class="error"><fmt:message
											key="checkout.error.cardnumber" /></label>
								</c:if></td>
						</tr>

						<tr>
							<td><fmt:message key="expiration.date" />:</td>
							<td><input type="month" name="date" required
								value="${sessionScope.checkoutDto.expirationDate}" /> <c:if
									test="${requestScope.isWrongDate}">
									<label class="error"><fmt:message
											key="checkout.error.date" /></label>
								</c:if></td>
						</tr>

						<tr>
							<td>CVV:</td>
							<td><input type="password" name="security-code"
								minlength="3" maxlength="4" size="4" required
								pattern="^\d{3,4}$" /> <c:if
									test="${requestScope.isWrongSecurityCode}">
									<label class="error"><fmt:message
											key="checkout.error.security.code" /></label>
								</c:if></td>
						</tr>

					</table>

					<table class="normal">
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td><button type="submit">
									<fmt:message key="place.order" />
								</button></td>
							<td><a href="${pageContext.request.contextPath}/"><fmt:message
										key="continue.shopping" /></a></td>

						</tr>
					</table>

				</form>
			</div>
		</c:if>
	</div>
	<br />
	<br />
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

		setInputFilter(document.getElementById("card-holder"), function(value) {
			return /^[A-Z\s-]*$/i.test(value);
		});

		setInputFilter(document.getElementById("card-number"), function(value) {
			return /^\d*$/.test(value);
		});

		setInputFilter(document.getElementById("security-code"),
				function(value) {
					return /^\d*$/.test(value);
				});
	</script>
</body>
</html>