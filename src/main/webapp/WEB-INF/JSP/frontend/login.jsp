<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="top.menu.sign.in" /></title>
<link rel="stylesheet" href="<c:url value="/css/style.css"/>">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Open+Sans+Condensed:wght@700&family=Roboto:wght@400;700&display=swap"
	rel="stylesheet">
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	<div align="center">

		<h2>
			<fmt:message key="top.menu.sign.in" />
		</h2>

		<c:if test="${message != null}">
			<div align="center">
				<h4 class="message">
					<i><fmt:message key="${message}">
							<fmt:param value="${messageParameter}" />
						</fmt:message> </i>
				</h4>
			</div>
		</c:if>

		<form id="loginForm" action="<c:url value="/controller"/>"
			method="post">
			<input type="hidden" name="command" value="customer_login">
			<table class="form">
				<tr>
					<td><fmt:message key="form.user.email" />:</td>
					<td><input type="email" required id="email" name="email"
						size="20"
						pattern="^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$"
						value="${requestScope.formUser.email}" /> <c:if
							test="${requestScope.isWrongEmail}">
							<div class="error">
								<fmt:message key="form.invalide.email" />
							</div>
						</c:if></td>
				</tr>
				<tr>
					<td><fmt:message key="form.user.password" />:</td>
					<td><input type="password" name="password" id="password"
						size="20" minlength="5" maxlength="45"
						pattern="[0-9a-zA-Z!@#$%^&*]{5,45}" /> <c:if
							test="${requestScope.isWrongPassword}">
							<div class="error">
								<fmt:message key="form.validate.password" />
							</div>
						</c:if></td>
				</tr>
				<tr>
					<td></td>
					<td align="center">
						<button type="submit">
							<fmt:message key="button.login" />
						</button> &nbsp; &nbsp;&nbsp;<a
						href="<c:url value="/controller?command=show_register_form"/>"><fmt:message
								key="top.menu.register" /></a>
					</td>
				</tr>
			</table>
		</form>
	</div>
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

		setInputFilter(document.getElementById("email"), function(value) {
			return /[^а-яёА-ЯЁ]/.test(value);
		});
		setInputFilter(document.getElementById("password"), function(value) {
			return /^[0-9a-z!@#$%^&*]*$/i.test(value);
		});
	</script>
</body>
</html>