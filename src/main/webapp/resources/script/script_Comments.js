/**
 * 
 */
 
 
$(function(){
	
	
	$.ajax({
	 			type:"GET",
	 			url:"/commentsList",
	 			data:{
	 				"tableNum":$("#tableNum").val().trim(),
	 				"tableType":$("#tableType").val().trim(),
	 			},
	 			dataType:"JSON",
	 			success:function(res) {
          			 let str="<div id = 'commList'>";
          			 $(res).each(function(index,item){
          			 		
          			 		str+="<ul><li><b>"+item.uId+"</b></li>"
          			 				+"<li>" +item.content+"</li>"
          			 				+"<li class='li'>" + item.regTM + "</li>"
          			 				+"<li><p class='comm'>"
  									+"<a class='btn' data-bs-toggle='collapse' href='#collapseExample"+index+"' role='button' aria-expanded='false' aria-controls='collapseExample'>"
									+"답글쓰기</a></p></li></ul>"
									+"<div class='collapse' id='collapseExample"+index+"'>"
									+"<div class='card card-body'>"
									+"<textarea id='repText"+index+"'></textarea><span class='commReply' onclick='commReply("+index+","+ JSON.stringify(item)+")'>답변달기</span>"
									+"</div></div>";
					});
					str+="</div>";
					$("#commentsList").html(str);
        		},
        		  error:function(request,status,error){
        			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
       			}
	});//onload ajax
	
	
	$("#commBtn").click(function(){
		let contents=$('#content').val().trim();
 		
 		if(contents==""){
 			alert("내용을 입력하세요");
 			$("#content").focus();
 		}else{
 			
	 		$.ajax({
	 		
	 			type:"POST",
	 			url:"/comments",
	 			data:{
	 				"tableNum":$("#tableNum").val().trim(),
	 				"tableType":$("#tableType").val().trim(),
	 				"uId":$("#uId").val().trim(),
	 				"ip":$("#ip").val().trim(),
	 				"content":$("#content").val()
	 			},
	 			dataType:"JSON",
	 			
	 			success:function(data) {
          			  location.reload();
        		},
        		  error:function(request,status,error){
        			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
       			}
       			
	 		});//$("#commBtn").click ajax
 			
 		
 		}//else
	
	
	});//$("#commBtn").click
	
	

		
});
	function commReply(index,item){
		let repComment=$('#repText'+index).val();
		
		$.ajax({
	 			type:"POST",
	 			url:"/replyComment",
	 			data:{
	 				"ref":item.ref,
	 				"tableNum":item.tableNum,
	 				"tableType":item.tableType,
	 				"uId":$("#uId").val().trim(),
	 				"ip":$("#ip").val().trim(),
	 				"content":repComment,
	 				"pos":item.pos,
	 				"depth":item.depth
	 			},
	 			dataType:"JSON",
	 			success:function(data) {
          			  if(data==1){
          			  	location.reload();
          			  }else{
          			  	alert("data:"+data);
          			  }
        		},
        		  error:function(request,status,error){
        			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
       			}
       			
	 		});//대댓글ajax
		
		
}




 