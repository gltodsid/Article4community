<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Document</title>
	<link rel="stylesheet" href="/resources/style/style_Common.css">
	<link rel="stylesheet" href="/resources/style/style_Template.css">
	<link rel="stylesheet" href="/resources/style/style_BBS.css">
	<script src="/resources/source/jquery-3.6.0.min.js"></script>
	<script src="/resources/script/script_Admin.js"></script>           <!--                           ddasddasdasdasdsadsa -->
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
    		<div id="contents" class="bbsList">
    		
	    		<div id="pageInfo" class="dFlex">
					<span>${prnType} :  ${totalRecord} 개</span>
					<span>페이지 :  ${nowPage} / ${totalPage}</span>  
				</div>	
					
			<table id="boardList">
				<thead>
					<tr>
						<th>번호</th>
						<th>ID</th>
						<th>이름</th>
						<th>성별</th>
						<th>가입일자</th>
					</tr>		
					<tr>
						<td colspan="5" class="spaceTd"></td>
					</tr>		
				</thead>
				<tbody>
			
	
				<c:if test="${empty vList}">
					<tr>
						<td colspan="5">
							회원이 없습니다
						</td>
					</tr>
				</c:if>
				
				<c:if test="${!empty vList}">
					<c:forEach var="item" items="${vList}">
					
						<tr class="prnTr" onclick="read('${item.uId}', '${nowPage}')">
							
							<td>
								
									${item.num}
								
							</td> 
							<td class="subjectTd" style="text-align: center;">
									<c:out value="${item.uId}"></c:out>
							</td>
							<td align="center">${item.uName}</td>
							<td>${item.gender}</td>
							<td>${item.joinTM}</td>
							
						</tr>
					
					</c:forEach>
				</c:if>
					
					<tr id="listBtnArea">
						<%-- <td colspan="2">
							<c:if test="${empty sessionScope.uId}">
								<button type="button" id="loginAlertBtn" class="listBtnStyle">글쓰기</button>
							</c:if>
							<c:if test="${!empty sessionScope.uId}">
								<button type="button" id="writeBtn" class="listBtnStyle">글쓰기</button>
							</c:if>
						</td> --%>
						
						<td colspan="5">
						
							<form name="searchFrm" class="dFlex" id="searchFrm">
							
								<div>
									<select name="keyField" id="keyField">
										<option value="uId" 
											<c:if test="${keyField eq 'uId'}">selected</c:if>>아이디</option>
										<option value="uName" 
											<c:if test="${keyField eq 'uName'}">selected</c:if>>이  름</option>
										<option value="gender" 
											<c:if test="${keyField eq 'gender'}">selected</c:if>>성 별</option>
									</select>
								</div>
								<div>
									<input type="text" name="keyWord" id="keyWord"
									  id="keyWord" size="20" maxlength="30" value="${keyWord}">
								</div>
								<div>
									<button type="button" id="searchBtn" class="listBtnStyle">검색</button>
								</div>
															
							</form>
							
							<!-- 검색결과 유지용 매개변수 데이터시작 -->
							<input type="hidden" id="pKeyField" value="${keyField}">
							<input type="hidden" id="pKeyWord" value="${keyWord}">
							<!-- 검색결과 유지용 매개변수 데이터끝 -->
						
						</td>
					</tr>  <!-- tr#listBtnArea -->
					
					<tr id="listPagingArea">
						
					<!-- 페이징(= 페이지 나누기) 시작 -->
						<td colspan="5" id="pagingTd">
						
							<c:if test="${totalPage !=0}">
								<c:if test="${nowBlock > 1}">
									<span class="moveBlockArea" onclick="moveBlock('${nowBlock-1}', '${pagePerBlock}', 'pb')">
										&lt;
									</span>
								</c:if>
								<c:if test="${nowBlock <= 1}">
									<span class="moveBlockArea" ></span>
								</c:if>
								
								<c:forEach var="i" begin="${pageStart}" end="${pageEnd}">
									<c:if test="${i == nowPage}">
										<span class="nowPageNum">${i}</span>
									</c:if>
									<c:if test="${i != nowPage}">
										<span class="pageNum" onclick="movePage('${i}')">${i}</span>
									</c:if>
								</c:forEach>
								
								<c:if test="${totalBlock > nowBlock}">
									<span  class="moveBlockArea" onclick="moveBlock('${nowBlock+1}', '${pagePerBlock}', 'nb')">
										&gt;
									</span>
								</c:if>
								<c:if test="${totalBlock <= nowBlock}">
									 <span class="moveBlockArea"></span>
								</c:if>
							</c:if>
							<c:if test="${totalPage == 0}">
								<span class="pageNum">1</span>
								<%-- <c:out value="<b>[ Paging Area ]</b>"></c:out> --%>
							</c:if>			
						</td>
					</tr>
					
				</tbody>
			</table>
		
		
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