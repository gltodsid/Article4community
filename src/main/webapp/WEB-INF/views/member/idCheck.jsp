<%@page import="org.apache.jasper.tagplugins.jstl.core.If"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 액션태그는 Java 클래스를 객체로 생성하고
       JSP에서 참조할 수 있도록 만든다.
       mMgr : 객체의 인스턴스 변수
       위의 액션태그는 아래의 코드와 동일하다
       MemberMgr mMgr = new MemberMgr(); --> 

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>아이디 중복 체크</title>
    <link rel="stylesheet" href="/resources/style/style_Common.css">    
    <script src="/resources/source/jquery-3.6.0.min.js"></script>
    <script src="/resources/script/script_Join.js"></script>
</head>

<body>
    <div id="wrap_Popup">
    	
    	<div>
    		<h1 id = "uId">${uId}</h1>
    	
    		<span>	
    			${result}
	    	</span>
    	
    	</div>
    	<hr>
    	<div id="closeBtnArea">
    		<input type = "hidden" name = "confirmOk" id = "confirmOk" value = "${confirmOk}">
    		<button type="button">${btnName}</button>
    	</div>
    	
    	        
    </div>
    <!-- div#wrap -->

</body>

</html>






