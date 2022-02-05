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
<title><fmt:message key="admin.edit.customer" /></title>
<link rel="stylesheet" href="<c:url value="/css/style.css"/>">
<script type="text/javascript"
	src="<c:url value="/js/js-inputmask.js"/>"></script>
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
		<h2 class="pageheading">
			<fmt:message key="admin.edit.customer" />
		</h2>

		<form
			action="<c:url value="/controller?command=update_customer_profile"/>"
			method="post" id="customerForm">
			<input type="hidden" name="isAdmin" value="false" />


			<table class="form">
				<tr>
					<td align="right"><fmt:message key="form.user.email" /></td>
					<td align="left"><input type="email" required id="email"
						name="email" size="20" readonly
						value="${requestScope.formUser.email}" /> (<fmt:message
							key="cannot.be.changed" />)</td>
				</tr>
				<tr>
					<td align="right"><fmt:message key="form.user.firstname" /></td>
					<td align="left"><input type="text" name="firstname"
						value="${formUser.firstName}" /> <c:if
							test="${requestScope.isWrongFirstname}">
							<label class="error" for="firstname"> <fmt:message
									key="form.validate.firstname" />
							</label>
						</c:if></td>
				</tr>
				<tr>
					<td align="right"><fmt:message key="form.user.lastname" /></td>
					<td align="left"><input type="text" class="name" required
						id="lastname" name="lastname" size="20"
						pattern="^[a-zа-яёA-ZА-ЯЁ]+(?:[-\s][a-zA-Za-zа-яёA-ZА-ЯЁ]+){0,2}$"
						minlength="2" maxlength="45"
						value="${requestScope.formUser.lastName}" /> <c:if
							test="${requestScope.isWrongLastname}">
							<label class="error" for="lastname"> <fmt:message
									key="form.validate.lastname" />
							</label>
						</c:if></td>
				</tr>
				<tr>
					<td align="right"><fmt:message key="form.user.phonenumber" /></td>
					<td align="left"><input type="tel" required class="tel"
						id="phonenumber" name="phonenumber" size="20"
						value="${requestScope.formUser.phoneNumber}" /> <c:if
							test="${requestScope.isWrongPhone}">
							<label class="error" for="phonenumber"> <fmt:message
									key="form.invalide.phone" />
							</label>
						</c:if> <c:if test="${requestScope.isDuplicatePhone}">
							<label class="error" for="phonenumber"> <fmt:message
									key="register.error.phone.exist" />
							</label>
						</c:if></td>
				</tr>
				<tr>
					<td colspan="2"><i>(<fmt:message
								key="leave.password.blank" />)
					</i></td>
				</tr>
				<tr>
					<td align="right"><fmt:message key="form.user.password" /></td>
					<td align="left"><input type="password" id="password"
						name="password" size="20" minlength="5" maxlength="45"
						pattern="[0-9a-zA-Z!@#$%^&*]{5,45}" onChange="onChange()" /> <c:if
							test="${requestScope.isWrongPassword}">
							<label class="error" for="password"> <fmt:message
									key="form.validate.password" />
							</label>
						</c:if></td>
				</tr>
				<tr>
					<td align="right"><fmt:message
							key="form.user.confirm.password" /></td>
					<td align="left"><input type="password" id="confirmpassword"
						name="confirmpassword" size="20" minlength="5" maxlength="45"
						pattern="[0-9a-zA-Z!@#$%^&*]{5,45}" onChange="onChange()" /> <span
						id='message'></span></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<button type="submit">
							<fmt:message key="button.save" />
						</button>&nbsp;&nbsp;&nbsp;
						<button type="button" id="buttonCancel"
							onclick="window.location.href = '<c:url value="/controller?command=show_customer_profile"/>';">
							<fmt:message key="button.cancel" />
						</button>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<jsp:directive.include file="footer.jsp" />
</body>
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

	setInputFilter(document.getElementById("firstname"), function(value) {
		return /^[A-ZА-ЯЁ\s-]*$/i.test(value);
	});
	setInputFilter(document.getElementById("lastname"), function(value) {
		return /^[A-ZА-ЯЁ\s-]*$/i.test(value);
	});
	setInputFilter(document.getElementById("email"), function(value) {
		return /[^а-яёА-ЯЁ]/.test(value);
	});

	setInputFilter(document.getElementById("password"), function(value) {
		return /^[0-9a-z!@#$%^&*]*$/i.test(value);
	});

	setInputFilter(document.getElementById("confirmpassword"), function(value) {
		return /^[0-9a-z!@#$%^&*]*$/i.test(value);
	});

	function onChange() {
		const password = document.querySelector('input[name=password]');
		const confirmpassword = document
				.querySelector('input[name=confirmpassword]');
		if (confirmpassword.value === password.value) {
			confirmpassword.setCustomValidity('');
		} else {
			confirmpassword
					.setCustomValidity('<fmt:message key="passwords.do.not.match"/>');
		}
	}
</script>
</html>