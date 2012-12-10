<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>
</head>
<body>
	<div class="row">
		<div class="span8 offset2">
			<shiro:guest>
				<c:if test="${allNewses!=null}">
					<h3>Info:</h3>
					<c:forEach items="${allNewses}" var="news" varStatus="st">
						<div class="alert alert-block alert-info">
							<a class="close" data-dismiss="alert" href="#">×</a>
							<h4 class="alert-heading">${news.title}</h4>
							<p>${news.message}</p>
						</div>
					</c:forEach>
				</c:if>
			</shiro:guest>

			<shiro:hasRole name="ROLE_STUDENT">
				<c:if test="${studentNewses!=null}">
					<h3>Student Info:</h3>
					<c:forEach items="${studentNewses}" var="news" varStatus="st">
						<div class="alert alert-block alert-info">
							<a class="close" data-dismiss="alert" href="#">×</a>
							<h4 class="alert-heading">${news.title}</h4>
							<p>${news.message}</p>
						</div>
					</c:forEach>
				</c:if>
			</shiro:hasRole>

			<shiro:hasRole name="ROLE_TEACHER">
				<c:if test="${teacherNewses!=null}">
					<h3>Teacher Info:</h3>
					<c:forEach items="${teacherNewses}" var="news" varStatus="st">
						<div class="alert alert-block alert-info">
							<a class="close" data-dismiss="alert" href="#">×</a>
							<h4 class="alert-heading">${news.title}</h4>
							<p>${news.message}</p>
						</div>
					</c:forEach>
				</c:if>
			</shiro:hasRole>

			<shiro:hasRole name="ROLE_ADMIN">
				<h3>Admin Info:</h3>
				<c:forEach items="${adminNewses}" var="news" varStatus="st">
					<div class="alert alert-block alert-info">
						<a class="close" data-dismiss="alert" href="#">×</a>
						<h4 class="alert-heading">${news.title}</h4>
						<p>${news.message}</p>
					</div>
				</c:forEach>
			</shiro:hasRole>
		</div>
		<!-- /span8 offset2 -->
	</div>
	<!-- /row -->

	<div class="row">
		<div class="span8 offset2">
			<h3>Download</h3>
			<table class="table table-bordered" style="margin-top: 20px;">
				<tbody>
					<c:forEach items="${papers}" var="paper">
						<tr>
							<td width="80%">${paper.description}</td>
							<td><a href="${ctx}/file/paper/${paper.filename}">download</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- /span8 offset2 -->
	</div>
	<!-- /row -->

	<!-- Javascript -->
	<script type="text/javascript">
		$(document).ready(function() {
			$('#li-index').addClass('active');
		});
	</script>
</body>
</html>