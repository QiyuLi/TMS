<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>
</head>
<body>
	<div class="row">
		<div class="span6 offset3">
			<p>&nbsp</p>
			<h2 align="center">Midtern Check</h2>
			<p>&nbsp</p>

			<c:if test="${warn!=null&&!warn.isEmpty()}">
				<div class="alert alert-block">
					<a class="close" data-dismiss="alert" href="#">×</a>
					<h4 class="alert-heading">Warn</h4>
					<p>${warn}</p>
				</div>
			</c:if>

			<c:if test="${success!=null&&success}">
				<div class="alert alert-block alert-success">
					<a class="close" data-dismiss="alert" href="#">×</a>
					<h4 class="alert-heading">success</h4>
				</div>
			</c:if>
			<c:if test="${success!=null&&!success}">
				<div class="alert alert-block alert-warn">
					<a class="close" data-dismiss="alert" href="#">×</a>
					<h4 class="alert-heading">fail</h4>
				</div>
			</c:if>

			<form:form id="midternCheck" modelAttribute="student" method="post" cssClass="form-horizontal">
				<!-- ID -->
				<form:hidden path="id" />

				<div class="control-group">
					<label class="control-label">no.:</label>
					<div class="controls">
						<span type="text" id="loginName" name="loginName" class="input-xlarge uneditable-input">${student.loginName}</span>
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">progress:</label>
					<div class="controls">
						<form:input path="progress" size="20" cssClass="input-xlarge" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">quantity:</label>
					<div class="controls">
						<form:input path="quality" size="20" cssClass="input-xlarge" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">attitude:</label>
					<div class="controls">
						<form:input path="attitude" size="20" cssClass="input-xlarge" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">duty:</label>
					<div class="controls">
						<form:input path="duty" size="20" cssClass="input-xlarge" />
					</div>
				</div>

				<div class="control-group">
					<label class="control-label">warn:</label>
					<div class="controls">
						<form:input path="warn" size="20" cssClass="input-xlarge" />
					</div>
				</div>

				<div class="form-actions">
					<input type="submit" class="btn btn-primary" value="Submit" /> <input type="reset" class="btn" value="cancel" /> <a
						href="${ctx}/teacher/midternCheck" class="btn btn-inverse">Return</a>
				</div>

			</form:form>
		</div>
	</div>

	<!-- Javascript -->
	<script type="text/javascript">
		$(document).ready(function() {
			$('#li-teacher_score').addClass('active');
		});
	</script>
</body>
</html>