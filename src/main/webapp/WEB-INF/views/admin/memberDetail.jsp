<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!--   errorPage="/err/errorProc.jsp" -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>글내용 보기</title>
	<link rel="stylesheet" href="/resources/style/style_Common.css">
	<link rel="stylesheet" href="/resources/style/style_Template.css">
	<link rel="stylesheet" href="/resources/style/style_BBS.css">
	<script src="/resources/source/jquery-3.6.0.min.js"></script>
	<script src="/resources/script/script_Admin.js"></script>
</head>

<body>
    <div id="wrap">
    	
    	<!--  헤더템플릿 시작 -->
		<%@ include file="/ind/headerTmp.jsp" %>
    	<!--  헤더템플릿 끝 -->    	
    	
    	
    	<main id="main" class="dFlex">
    	
    	<div id="contents" class="joinInsert">
    		
    			<form name="regFrm" id="regFrm" method="post">
    			
    				<table id="regFrmTbl">
    					<caption>회원 정보 수정</caption>
    					<tbody>
    						<tr>
    							<td class="req">아이디</td>
    							<td id="uId">${memberMap.uId}</td>
    							<td>&nbsp;</td>
    						</tr>
    						<tr>
    							<td class="req">패스워드</td>
									<td>${memberMap.uPw}</td>
									<td>&nbsp;</td>
    						</tr>
    						<tr>
    							<td class="req">이름</td>
    								<td>${memberMap.uName}</td>
    							<td>&nbsp;</td>
    						</tr>
    						<tr>
    							<td class="req">Email</td>
    								<td>${memberMap.uEmail}</td>
    							<td>&nbsp;</td>
    						</tr>
    						<tr>
    							<td>성별</td>
    							<td>
    									<c:if test = "${memberMap.gender == 1}">남</c:if>
    									<c:if test = "${memberMap.gender == 2}">여</c:if>
    							</td>
    							<td>&nbsp;</td>
    						</tr>
    						<tr>
    							<td class="req">생년월일</td>
    								<td>${memberMap.uBirthday}</td>
    							<td>&nbsp;</td>
    						</tr>
    						<tr>
    							<td class="req">우편번호</td>
    								<td>${memberMap.uZipcode}</td>
    							<td>&nbsp;</td>
    						</tr>
    						<tr>
    							<td class="req">주소</td>
    								<td>${memberMap.uAddr}</td>
    							<td>&nbsp;</td>
    						</tr>
    						<tr>
    							<td>취미</td>
    							<td>    							
    								${hobby}
    							</td>
    							<td></td>
    						</tr>
    						<tr>
    							<td>직업</td>
    							<td>    								
    								${memberMap.uJob}
    							</td>
    							<td></td>
    						</tr>
    						<tr>
    							<td colspan="3">
    								<button type="button" id="memberList" class="frmBtn">회원목록</button>
    								<button type="button" id="memberEdit" class="frmBtn">회원정보수정</button>
    								<button type="button" id="memberDelete" class="frmBtn">회원삭제</button>
    							</td>
    						</tr>
    					</tbody>	
    				</table>
    			<input type="hidden" name="nowPage" id="nowPage" value="${pageMap.nowPage}">
    			<input type="hidden" name="pKeyField" id="keyField" value="${pageMap.keyField}">
    			<input type="hidden" name="pKeyWord" id="keyWord" value="${pageMap.keyWord}">
    			<input type="hidden" name="uId" id="uId" value="${memberMap.uId}">
    			
    			
	     
	     
    			</form>
    			<!-- form[name=regFrm] -->
    			
    		</div>
    	
    	
    	
    	
    	
    	<!-- 원본 -->
    		
    		
	    	<!-- 실제 작업 영역 시작 -->
    		
    		    	
    	</main>
    	<!--  main#main  -->
    
        	   	
    	<!--  푸터템플릿 시작 -->
		<%@ include file="/ind/footerTmp.jsp" %>
    	<!--  푸터템플릿 끝 -->  
        
    </div>
    <!-- div#wrap -->

</body>

</html>