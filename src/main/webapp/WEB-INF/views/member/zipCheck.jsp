
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>우편번호 찾기</title>
    <link rel="stylesheet" href="/resources/style/style_Common.css">
    <script src="/resources/source/jquery-3.6.0.min.js"></script>
    <script src="/resources/script/script_Join.js"></script>
</head>

<body>
    <div id="wrap_zipcodePopup">    	
    	<h1>우편번호 찾기</h1>    	
    	<form name="zipFrm" id="zipFrm" method="post">
    		<table id="zipFrmTbl">
    			<tbody>
    				<tr>
    					<td id="searchInputArea">
    						<span>동 이름 입력</span>
    						<input type="text" name="area3" id="area3">
    						<button type="button" id="addrSearchBtn">검색</button>
    					</td>
    				</tr>
    				<tr>
    					<td><hr></td>
    				</tr>
    				
    				<tr>
    					<td id="zipResArea">

					<c:if test="${empty area3}">
							<span>검색어를 입력해주세요</span>
					</c:if>		

					<c:if test="${!empty area3}">
							<span><b>[ ${area3} ]</b></span> 에 대한 검색결과입니다.
					</c:if>		
							
							
							
							<table id="zipResTbl">
								<tbody>
								<c:forEach var="item" items="${list}">
									<tr>
										<td><span>${item.zipcode}&nbsp;${item.area1}&nbsp;${item.area2}&nbsp;${item.area3}&nbsp;${item.area4}</span></td>
									</tr>
								</c:forEach>
								
								</tbody>							
							</table>
							 
		
    					</td>
    				</tr>
    				
    			</tbody>
    		</table>
    	</form>
        
    </div>
    <!-- div#wrap -->

</body>

</html>