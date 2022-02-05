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
<link rel="stylesheet" href="<c:url value="/css/style.css"/>">
<title><c:if test="${categoryDTO.id != null}">
		<fmt:message key="admin.edit.category" />
	</c:if> <c:if test="${categoryDTO.id == null}">
		<fmt:message key="admin.create.new.category" />
	</c:if></title>
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2 class="pageheading">
			<c:if test="${categoryDTO.id != null}">
				<fmt:message key="admin.edit.category" />
			</c:if>
			<c:if test="${categoryDTO.id == null}">
				<fmt:message key="admin.create.new.category" />
			</c:if>
		</h2>

		<form action="controller" method="post" id="categoryForm">
			<input type="hidden" name="command" value="save_category" /> 
			<input type="hidden" name="id" value="${categoryDTO.id}" />

			<table class="form">
				<tr>
					<td align="right"><fmt:message
							key="form.category.choose.parent" />:</td>
					<td><select name="parent-category" required>
							<option value="0"
								<c:if test="${categoryDTO.parentId == 0 or categoryDTO.id == null}">
                                                selected="selected"
                                            </c:if>>
								<fmt:message key="form.category.no.parent" />
							</option>
							<c:forEach var="category"
								items="${requestScope.allPotentialParentCategories.get(0)}">
								<option value="${category.id}"
									<c:if test="${category.id == categoryDTO.parentId}">
                                                selected="selected"
                                            </c:if>>${category.name}</option>
								<c:if
									test="${not empty requestScope.allPotentialParentCategories.get(category.id)}">

									<c:forEach var="category_child"
										items="${requestScope.allPotentialParentCategories.get(category.id)}">
										<option value="${category_child.id}"
											<c:if test="${category_child.id == categoryDTO.parentId}">
                                                selected="selected"
                                            </c:if>>
											&nbsp;&nbsp;- ${category_child.name}</option>
									</c:forEach>
								</c:if>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<c:forEach var="language" items="${sessionScope.appLanguages}">

					<tr>
						<td align="right"><fmt:message key="form.category.name" /> <fmt:message
								key="${language.name}" />:</td>
						<td align="left"><input type="text" id="name"
							name="category-name-${language.id}" size="20"
							<c:forEach var="category" items="${categoryDTO.localizations}">
							<c:if test="${category.languageId==language.id}" >
							value="${category.name}" </c:if> </c:forEach>
							required minlength="3" maxlength="30" /></td>
						<c:if test="${categoryErrorStorage != null}">
							<c:forEach var="error" items="${categoryErrorStorage}">
								<c:if test="${error.languageId == language.id}">
									<c:if test="${error.isWrongName}">
										<label class="error" for="name"> error!!! <fmt:message
												key="form.validate.category.name" /></label>
									</c:if>
									<c:if test="${error.isDuplicateName}">
										<label class="error" for="name"> error!!! <fmt:message
												key="form.validate.category.name.exist" /></label>
									</c:if>
								</c:if>
							</c:forEach>
						</c:if>
					</tr>
					<tr>
						<td align="right"><fmt:message key="form.category.item.title" />
							<fmt:message key="${language.name}" />:</td>
						<td align="left"><input type="text" id="item_title"
							name="category-item-title-${language.id}" size="20" required
							<c:forEach var="category" items="${categoryDTO.localizations}"> <c:if test="${category.languageId==language.id}" >
							value="${category.itemTitle}" </c:if>  </c:forEach>
							required minlength="3" maxlength="45" /> <c:if
								test="${categoryErrorStorage != null}">
								<c:forEach var="error" items="${categoryErrorStorage}">
									<c:if test="${error.languageId == language.id}">
										<c:if test="${error.isWrongItemTitle}">
											<label class="error" for="item_title"> error!!! <fmt:message
													key="form.validate.category.item.title" /></label>
										</c:if>

									</c:if>
								</c:forEach>
							</c:if></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>

				</c:forEach>

				<tr>
					<td colspan="2" align="center">
						<button type="submit">
							<fmt:message key="button.save" />
						</button>&nbsp;&nbsp;&nbsp;
						<button type="button"
							onclick="window.location.href = '<c:url value="/controller?command=list_category"/>';">
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
	
</script>
</html>