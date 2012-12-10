<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Student File</title>
</head>
<body>
	<h2>Student File</h2>
	<p>&nbsp;</p>

	<table class="table table-bordered">
		<thead>
			<tr>
				<th style="vertical-align: middle;">no.</th>
				<th style="vertical-align: middle;">name</th>
				<th style="vertical-align: middle;">Thesis name</th>
				<th style="vertical-align: middle;">abstract</th>
				<th style="vertical-align: middle;">schedule</th>
				<th style="vertical-align: middle;">reference</th>
				<th style="vertical-align: middle;">paper</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${students}" var="student" varStatus="st">
				<tr>
					<td>${student.loginName}</td>
					<td>${student.name}</td>
					<td>${studentsThesises[st.index].name}</td>
					<td><a href="${ctx}/file/ktup/${student.ktup}">${student.ktup}</a></td>
					<td><a href="${ctx}/file/ktup/${student.rwsup}">${student.rwsup}</a></td>
					<td><a href="${ctx}/file/ktup/${student.transup}">${student.transup}</a></td>
					<td><a href="${ctx}/file/ktup/${student.thesisup}">${student.thesisup}</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- Javascript -->
	<script type="text/javascript">
		$(document).ready(function() {
			$('#li-teacher_file').addClass('active');
		});
	</script>
</body>
</html>