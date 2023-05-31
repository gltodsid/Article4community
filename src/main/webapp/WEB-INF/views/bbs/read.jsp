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
	<script src="/resources/script/script_BBS.js"></script>
</head>

<body>
    <div id="wrap">
    	
    	<!--  헤더템플릿 시작 -->
		<%@ include file="/ind/headerTmp.jsp" %>
    	<!--  헤더템플릿 끝 -->    	
    	
    	
    	<main id="main" class="dFlex">
    	
    		<div id="lnb">
	    		<!--  메인 LNB 템플릿 시작 -->
				<%@ include file="/ind/mainLnbTmp.jsp" %>
	    		<!--  메인 LNB 템플릿 끝 -->    	
    		</div>
    		
    		
	    	<!-- 실제 작업 영역 시작 -->
    		<div id="contents" class="bbsRead">

				<!--  게시글 상세보기 페이지 내용 출력 시작 -->
				<h2>${map.subject}</h2>
				
				<table id="readTbl">
					<tbody id="readTblBody">
						<tr>
							<td>작성자</td>  <!-- td.req 필수입력 -->
							<td>${map.uName}</td>
							<td>등록일</td>  <!-- td.req 필수입력 -->
							<td>${map.regTM}</td>
						</tr>
						<tr>
							<td>첨부파일</td> <!-- td.req 필수입력 -->
							<td colspan="3">
								<input type="hidden" name="fileName" value="${map.fileName}" 
											id="hiddenFname">
								<c:if test="${!empty map.fileName}">
									<span id="downloadFile">${map.fileName}</span>							
									(<span>${map.fileSize} ${map.fUnit}</span>)
								</c:if>
								<c:if test="${empty map.fileName}">
									등록된 파일이 없습니다.
								</c:if>
							</td>
						</tr>
						<tr>
							<td colspan="4" id="readContentTd"><pre>${map.content}</pre></td>
						</tr>					
					</tbody>
					 
					<tfoot id="readTblFoot">	
						<tr>
							<td colspan="4" id="footTopSpace"></td>							
						</tr>			     
						<tr>
							<td colspan="4" id="articleInfoTd">
								<span>조회수 : ${map.readCnt}</span>
								<span>(IP : ${map.ip})</span>							
							</td>							
						</tr>
						<tr>
							<td colspan="4" id="hrTd"><hr></td>							
						</tr>
						<tr>
							<td colspan="4" id="btnAreaTd" class="read">
								<button type="button" id="listBtn">${map.listBtnLabel}</button>
								<button type="button" id="replyBtn">답 변</button>
								
								<c:if test="${!empty sessionScope.uId}">
									<c:if test="${sessionScope.uId eq map.uId}">
										<button type="button" id="delBtn">삭 제</button>
										<button type="button" id="modBtn">수 정</button>
									</c:if>
								</c:if>
							</td>
						</tr>
					</tfoot>
					 
				</table>
				<input type="hidden" name="nowPage" value="${map.nowPage}" id="nowPage">
				<input type="hidden" name="num" value="${map.num}" id="num">
				<input type="hidden" name="ref" value="${map.ref}" id="ref">
				
				<!-- 검색어전송 시작 -->
				<input type="hidden" id="pKeyField" value="${map.keyField}">
				<input type="hidden" id="pKeyWord" value="${map.keyWord}">
				<!-- 검색어전송 끝 -->
			  
				<!--  게시글 상세보기 페이지 내용 출력 끝 -->
				

    		</div>
    		<!-- 실제 작업 영역 끝 -->
    		    	
    	</main>
    	<!--  main#main  -->
    
        	
    	<!--  푸터템플릿 시작 -->
    	<%@ include file="/ind/comments.jsp" %>
		<%@ include file="/ind/footerTmp.jsp" %>
    	<!--  푸터템플릿 끝 -->  
        
    </div>
    <!-- div#wrap -->

</body>

</html>