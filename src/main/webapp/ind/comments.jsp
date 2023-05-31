<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>comments.jsp</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<script src="/resources/source/jquery-3.6.0.min.js"></script>
<script src="/resources/script/script_Comments.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
<link rel="stylesheet" href="/resources/style/style_Comment.css">
</head>
<body>

	<%-- 	<c:if test="${!empty vList}">
					<c:forEach var="item" items="${vList}"> --%>

	<div id="div0">
		<br>
		<h4>댓글</h4>
		<br>
		<div id="commentsList"></div>
		<div id="div1">
				<div id="div2">
					<form id="commentFrm" method="post">
						<strong>${sessionScope.uName}</strong>
						<div>
							<textarea id="content" name="content" placeholder="댓글을 남겨보세요"
								rows="1" cols="30"></textarea>
							<input type="hidden" id="tableNum" name="tableNum"
								value="${map.num}"> <input type="hidden" id="tableType"
								name="tableType" value="${map.tableType}"> <input
								type="hidden" id="uId" name="uId" value="${sessionScope.uId}">
							<input type="hidden" id="ip" name="ip"
								value="${pageContext.request.remoteAddr}">
						</div>
						<div style="text-align: right; padding-right: 13px;">
							<button type="button" id="commBtn">등록</button>
						</div>
					</form>
				</div>
		</div>
	</div>
</body>
</html>