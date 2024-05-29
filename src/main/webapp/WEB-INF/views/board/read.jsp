<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 보기</title>
<link rel="stylesheet" type="text/css" href="/resources/css/read.css">
</head>
<body>
	<div class="table-container">
		<h1>게시글 보기</h1>
		<table>
			<tr>
				<th>게시물 번호</th>
				<td>${board.bno}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${board.title}</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>${board.content}</td>
			</tr>
			<tr>
				<th>작성자</th>
				<td>${board.writer}</td>
			</tr>
			<tr>
				<th>작성일</th>
				<td>${board.regdate}</td>
			</tr>
			<tr>
				<th>업데이트일</th>
				<td>${board.updateDate}</td>
			</tr>
		</table>
		<button type="button"
			onclick="location.href='${pageContext.request.contextPath}/board/list'">목록으로</button>
	</div>
	<div class="comments">
            <h2>댓글</h2>
            <form action="${pageContext.request.contextPath}/board/comment" method="post">
                <input type="hidden" name="bno" value="${board.bno}">
                <textarea name="content" placeholder="댓글을 입력하세요"></textarea>
                <input type="text" name="writer" placeholder="작성자">
                <button type="submit">댓글 작성</button>
            </form>

            <div class="comment-list">
                <c:forEach var="comment" items="${comments}">
                    <div class="comment">
                        <div class="comment-header">
                            <span class="comment-writer">${comment.writer}</span>
                            <span class="comment-date">${comment.regdate}</span>
                        </div>
                        <div class="comment-content">
                            ${comment.content}
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
</body>
</html>
