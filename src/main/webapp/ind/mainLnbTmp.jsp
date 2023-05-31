<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%
String uId_Session_MLTmp = (String)session.getAttribute("uId_Session");
request.setCharacterEncoding("UTF-8");

String gnbParam = "";
if (request.getParameter("gnbParam") != null) {
	gnbParam = request.getParameter("gnbParam");
}

%>    
 --%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>메인영역 LNB 메뉴</title>
</head>
<body>

	<nav id="mainLNB">

		<ul id="lnbMainMenu">
			<c:choose>
				<c:when test='${(gnbParam eq "myPage") and (sessionScope.accLv eq "0")}'>
					<li class="lnbMainLi"><a href="/modPwCheck">회원정보수정</a></li>
					<li class="lnbMainLi"><a href="/memberQuit">회원탈퇴</a></li>
					<li class="lnbMainLi"><a href="/myBoard">나의 게시글</a></li>
					<li class="lnbMainLi"><a href="/modPw">비밀번호변경</a></li>
					<li class="lnbMainLi"><a href="#">menu5</a></li>
				</c:when>
				<c:when test='${(sessionScope.accLv eq "1")}'>
					<li class="lnbMainLi"><a href="/memberList">회원목록보기</a></li>
					<li class="lnbMainLi"><a href="#">게시글관리</a></li>
					<li class="lnbMainLi"><a href="#">menu3</a></li>
					<li class="lnbMainLi"><a href="#">menu4</a></li>
					<li class="lnbMainLi"><a href="#">menu5</a></li>
				</c:when>
				<c:when test='${(sessionScope.accLv eq "2")}'>
					<li class="lnbMainLi"><a href="/memberList">회원목록보기</a></li>
					<li class="lnbMainLi"><a href="#">부관리자생성</a></li>
					<li class="lnbMainLi"><a href="#">공지사항관리</a></li>
					<li class="lnbMainLi"><a href="#">게시글관리</a></li>
					<li class="lnbMainLi"><a href="#">menu5</a></li>
				</c:when>
				<c:otherwise>
					<li class="lnbMainLi"><a href="#">menu1</a></li>
					<li class="lnbMainLi"><a href="#">menu2</a></li>
					<li class="lnbMainLi"><a href="#">menu3</a></li>
					<li class="lnbMainLi"><a href="#">menu4</a></li>
					<li class="lnbMainLi"><a href="#">menu5</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</nav>

</body>
</html>