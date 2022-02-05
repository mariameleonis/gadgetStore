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
<title><c:if test="${product != null}">
		<fmt:message key="admin.edit.product" />
	</c:if> <c:if test="${product == null}">
		<fmt:message key="admin.create.new.product" />
	</c:if> | NOOR Accessories Administration</title>

<link rel="stylesheet" href="<c:url value="/css/style.css"/>">
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2>
			<c:if test="${product != null}">
				<fmt:message key="admin.edit.product" />
			</c:if>
			<c:if test="${product == null}">
				<fmt:message key="admin.create.new.product" />
			</c:if>
		</h2>

		<form action="controller" method="post" id="productForm"
			enctype="multipart/form-data">
			<input type="hidden" name="id" value="${product.id}">
			<input type="hidden" name="command" value="save_product" />
			
			<table class="form">
				<tr>
					<td align="right"><fmt:message key="form.product.category" />:</td>
					<td><select name="category" required>
							<c:forEach items="${leafCategories}" var="category">
								<option value="${category.id}"
									<c:if test="${category.id eq product.category.id}">
								 selected
							</c:if>>
									${category.name}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
				<tr>
					<td align="right"><fmt:message key="form.product.brand" />:</td>
					<td><select name="brand" required>
							<c:forEach items="${allBrands}" var="brand">
								<option value="${brand.id}"
									<c:if test="${brand.id eq product.brand.id}">
								selected
							</c:if>>
									${brand.name}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td align="right"><fmt:message key="form.product.name" />:</td>
					<td align="left"><input type="text" id="name" name="name"
						size="20" value="${product.name}" required /> <c:if
							test="${requestScope.isWrongName}">
							<label class="error" for="name"> <fmt:message
									key="form.validate.product.name" />
							</label>
						</c:if></td>
				</tr>

				<tr>
					<td align="right"><fmt:message key="form.product.image" />:</td>
					<td align="left"><input type="file" onchange="previewFile()"
						id="productImage" name="productImage"
						accept="image/jpeg,image/png,image/jpg"
						<c:if test="${product == null}">
				    required
				    </c:if> />
				    
				     <c:if
							test="${requestScope.isWrongImage}">
							<label class="error" for="productImage"> <fmt:message
									key="form.validate.product.image" />
							</label>
						</c:if>
				    
				    
				    <br />
						<c:if test="${product != null}">
							<img id="thumbnail" alt="Image Preview"
								style="width: 70px; margin-top: 10px"
								src="data:image/jpg;base64,${product.base64Image}" />
						</c:if> <c:if test="${product == null}">
							<img id="thumbnail" alt="Image Preview"
								style="width: 70px; margin-top: 10px"
								src="<c:url value="/images/no_image.jpg" />" />
						</c:if></td>
				</tr>

				<tr>
					<td align="right"><fmt:message key="form.product.decription" />:</td>
					<td align="left"><textarea rows="5" cols="50" id="description"
							name="description" required> ${product.description}</textarea></td>
							<c:if
							test="${requestScope.isWrongDescription}">
							<label class="error" for="description"> <fmt:message
									key="form.validate.product.description" />
							</label>
						</c:if>
				</tr>

				<tr>
					<td align="right"><fmt:message key="form.product.price" />:</td>
					<td align="left"><input type="text" id="price" name="price"
						size="5" min="1" max="99999" pattern="^([1-9][0-9]{0,4}|99999)$"
						value="<fmt:formatNumber type = "number" groupingUsed = "false" value="${product.price}" maxFractionDigits="0" />"
						required /> KZT&nbsp;
						<c:if
							test="${requestScope.isWrongPrice}">
							<label class="error" for="price"> <fmt:message
									key="form.validate.product.price" />
							</label>
						</c:if>
						</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<button type="submit">
							<fmt:message key="button.save" />
						</button>&nbsp;&nbsp;&nbsp;
						<button type="button"
							onclick="window.location.href = '<c:url value="/controller?command=list_products"/>';">
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
	function previewFile() {
		var preview = document.getElementById("thumbnail");
		var file = document.querySelector('input[type=file]').files[0];
		var reader = new FileReader();

		reader.onloadend = function() {
			preview.src = reader.result;
		}

		if (file) {
			reader.readAsDataURL(file);
		} else {
			preview.src = "";
		}
	}

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

	setInputFilter(document.getElementById("price"), function(value) {
		return /^([1-9][0-9]{0,4}|\s*)$/.test(value)
				&& (value === "" || parseInt(value) <= 99999);
	});
</script>
</html>