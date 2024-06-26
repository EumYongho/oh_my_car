<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>
<link rel="stylesheet" type="text/css" href="/resources/css/write.css?ver=1">
<style>
</style>
</head>
<body>
<jsp:include page="../includes/header.jsp"></jsp:include>
<div id="modifyWrap">
	<form action="/board/write" method="post">			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		<label for="title">제목:</label> <input type="text" id="title"
			name="title"><br> <label for="content">게시글 내용:</label>
		<textarea id="content" name="content"></textarea>
		<br> <label for="writer">작성자:</label> <input type="text"
			id="writer" name="writer" value="${userVO.userId}" readonly><br>
		<button type="submit">제출</button>
		<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
	</form>
</div>

	<jsp:include page="../includes/footer.jsp"></jsp:include>
</body>
</html>
