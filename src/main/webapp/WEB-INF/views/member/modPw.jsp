<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <link rel="stylesheet" href='/resources/style/style_Common.css'>
    <link rel="stylesheet" href='/resources/style/style_Template.css'>
    <script src='/resources/source/jquery-3.6.0.min.js'></script>
    <script src='/resources/script/script_Member.js'></script>
</head>

<body>
    <div id="wrap">
    	
    	<!--  헤더템플릿 시작 -->
		<%@ include file="/ind/headerTmp.jsp" %>
    	<!--  헤더템플릿 끝 -->    	
    	
    	
    	<main id="main" class="dFlex">
    		
	    	<!-- 실제 작업 영역 시작 -->
    		<div id="contents" class="joinInsert">
    		
    			<form name="regFrm" id="regFrm" method="post">
    			
    				<table id="regFrmTbl">
    					<caption>비밀번호 변경</caption>
    					<tbody>
    					
    						<tr>
    							<td class="req">패스워드</td>
    							<td>
    								<input type="password" name="uPw" id="uPw"
    								maxlength="20">
    							</td>
    							<td>
    								<span>영어소문자/숫자, _, @, $, 5~20 </span>
    							</td>
    						</tr>
    						<tr>
    							<td class="req">패스워드 확인</td>
    							<td>
    								<input type="password" id="uPw_Re"
    								maxlength="20">
    								<span id="pwChk"></span>
    							</td>
    							<td>&nbsp;</td>
    						</tr>
    						<tr>
    							<td colspan="3">
    								<button type="button" id="modPwBtn" class="frmBtn">비밀번호변경</button>
    								<button type="reset" class="frmBtn">취소</button>
    							</td>
    						</tr>
    					</tbody>
    				</table>
    			
    			</form>
    			<!-- form[name=regFrm] -->
    			
    		</div>
    		<!-- 실제 작업 영역 끝 -->
    		    	
    	</main>
    	<!--  main#main  -->
    
        	   	
    	<!--  푸터템플릿 시작 -->
		<%@ include file="/ind/footerTmp.jsp" %>
    	<!--  푸터템플릿 끝 -->  
        
    </div>
    <!-- div#wrap -->

</body>

</html>