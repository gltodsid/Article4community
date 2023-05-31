/**
 * 
 */
 $(function(){
	
	$('#memQuitBtn').click(function(){
		let delCheck = confirm("정말 탈퇴하시겠습니까?");
		
		if(delCheck){
			$('#memQuitFrm').attr('action', '/memberQuit');
			$('#memQuitFrm').submit();
		}
	});
	
	$('#pwChk').click(function(){
	
		let upw=$('#uPw').val().trim();
		let uid=$('#uId').val().trim();
		$.ajax({
			url: "/PwCheck", 
			data: { uPw: upw, 
						uId:uid
			},  
			method: "POST",  
			dataType: "json", 
			success:function(res) {
				$('#pwChecked').val("pwChecked");
				alert(res.msg);
		   		$("#frm").attr("action", res.url);
				$("#frm").submit();	
			},
			error:function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		
		});
	
	
	});
	
	$('#pwChk2').click(function(){
	
		let upw=$('#uPw').val().trim();
		let uid=$('#uId').val().trim();
		$.ajax({
			url: "/PwCheck2", 
			data: { uPw: upw, 
						uId:uid
			},  
			method: "POST",  
			dataType: "json", 
			success:function(res) {
				$('#pwChecked').val("pwChecked");
				alert(res.msg);
		   		$("#frm").attr("action", res.url);
				$("#frm").submit();	
			},
			error:function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		
		});
	
	
	});
	
	$("#modPwBtn").click(function(){		
		fnModPwSbm();		
	});
	
	function fnModPwSbm() {	
		let uPw = $("#uPw").val().trim();		
		$("#uPw").val(uPw);		
		let uPw_Re = $("#uPw_Re").val().trim();	
		
		if (uPw == "") {
			alert("비밀번호를 입력해주세요.");
			$("#uPw").focus();
			return;
		} else if (uPw_Re == "" || uPw != uPw_Re) {
			alert("비밀번호 일치여부를 확인해주세요.");
			$("#uPw_Re").focus();
			return;
		} else {
			let chkSbmTF = confirm("비밀번호를 변경하시겠습니까?");
			if (chkSbmTF) {
				$("#regFrm").attr("action", "/modPwProc");
				$("#regFrm").submit();
			}
		}
		 
	}
	
});


