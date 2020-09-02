var protocolIndicator = document.getElementById("protocolIndicator");
var pi_val;

var protocolOption = document.getElementById("protocolOption");
var po_val;

var opCode = document.getElementById("opCode");
var op_val;
var temp;
/*
for(i=0; i<protocolOption.options.length; i++) {
    if(protocolOption.options[i].selected == true) {
        val = protocolOption.options[i].value;
		text = protocolOption.options[i].text;
        break;
    }
}*/

//op코드별 payload 형식 변경
function goChange(val){
	if(val=="OP_GET_REQ"){
		//document.all.getReq.style.display="";
		document.all.getReq.style.display="";
		document.all.setReq.style.display="none";
		document.all.actionReq.style.display="none";
		document.all.actionRes.style.display="none";
		document.all.eventReq.style.display="none";
		//alert(val);
	}
	else if(val=="OP_SET_REQ"){
		document.all.setReq.style.display="";
		document.all.getReq.style.display="none";
		document.all.actionReq.style.display="none";
		document.all.actionRes.style.display="none";
		document.all.eventReq.style.display="none";
		//alert(val);
	}else if(val=="OP_ACTION_REQ"){
		document.all.actionReq.style.display="";
		document.all.getReq.style.display="none";
		document.all.setReq.style.display="none";
		document.all.actionRes.style.display="none";
		document.all.eventReq.style.display="none";
		//alert(val);
	}
	else if(val=="OP_ACTION_RES"){
		document.all.actionRes.style.display="";
		document.all.getReq.style.display="none";
		document.all.setReq.style.display="none";
		document.all.actionReq.style.display="none";
		document.all.eventReq.style.display="none";
		//alert(val);
	}
	else if(val=="OP_EVENT_REQ"){
		document.all.eventReq.style.display="";
		document.all.getReq.style.display="none";
		document.all.setReq.style.display="none";
		document.all.actionReq.style.display="none";
		document.all.actionRes.style.display="none";
		//alert(val);
	}
	
}

//전송버튼
function send_button_click(){
	//pi_val = protocolIndicator.options[protocolIndicator.selectedIndex].value;
	//console.log(button_click.pi_val);
	document.getElementById("isSend").value="true";
	temp = document.getElementById("isSend").value;
	alert("hello " + temp);
}


//저장버튼
function save_button_click(){
	//pi_val = protocolIndicator.options[protocolIndicator.selectedIndex].value;
	//console.log(button_click.pi_val);
	document.getElementById("isSaved").value="true";
	temp = document.getElementById("isSaved").value;
	alert("hello " + temp);
}

//추가버튼
function name_field_reset(){
	$("#ex1_Result2").html("");
	goChange("OP_GET_REQ");
	alert("추가추가");
}
//삭제버튼
function remove_button_click(){
	if ($("input:checkbox[name=user_CheckBox]").is(":checked") == true) {
		//체크가 되어있을때. 
            alert("WOW hihi remove"); 
			var checkbox = $("input[name=user_CheckBox]:checked"); 
			var tdArr = new Array();    // 배열 선언
            
            checkbox.each(function(i) {
				// checkbox.parent() : checkbox의 부모는 <td>이다.
				// checkbox.parent().parent() : <td>의 부모이므로 <tr>이다.
				var tr = checkbox.parent().parent().eq(i);
				var td = tr.children();
				
				//1번 값이 테스트코드이므로 1번 값을 가져온다.
				var testCode = td.eq(1).text();
				
				// 가져온 값을 배열에 담는다.
				tdArr.push(testCode);
				$("#isRemovedThings").val(tdArr);
			});
			document.getElementById("isRemoved").value="true";
			alert("WOW hihi remove : "+tdArr);  
				
    }else{
			alert("ㅠㅠ no checked");  
            //체크가 안되어있을때.
    }
}



    function sampleModalPopup(){
        // 팝업 호출 url
        var url = "http://localhost:8080/main";
        
        // 팝업 호출
        $("#exampleModalCenter > .modal-dialog").load(url, function() { 
            $("#exampleModalCenter").modal("show"); 
        });
    }


//복구버튼
function restore_button_click(){
	if ($("input:checkbox[name=restore_CheckBox]").is(":checked") == true) {
		//체크가 되어있을때. 
            alert("WOW hihi restore"); 
			var checkbox = $("input[name=restore_CheckBox]:checked"); 
			var tdArr = new Array();    // 배열 선언
            
            checkbox.each(function(i) {
				// checkbox.parent() : checkbox의 부모는 <td>이다.
				// checkbox.parent().parent() : <td>의 부모이므로 <tr>이다.
				var tr = checkbox.parent().parent().eq(i);
				var td = tr.children();
				
				//1번 값이 테스트코드이므로 1번 값을 가져온다.
				var testCode = td.eq(1).text();
				
				// 가져온 값을 배열에 담는다.
				tdArr.push(testCode);
				$("#isRestoredThings").val(tdArr);
			});
			document.getElementById("isRestored").value="true";
			alert("WOW hihi restore : "+tdArr);  
				
    }else{
			alert("ㅠㅠ no checked");  
            //체크가 안되어있을때.
    }
}





        // 테이블의 Row 클릭시 값 가져오기
        $("#dataTable tr").click(function(){     
 
            var str = ""
            var tdArr = new Array();    // 배열 선언
            
            // 현재 클릭된 Row(<tr>)
            var tr = $(this);
            var td = tr.children();
            
            // tr.text()는 클릭된 Row 즉 tr에 있는 모든 값을 가져온다.
            console.log("클릭한 Row의 모든 데이터 : "+tr.text());
            
            // 반복문을 이용해서 배열에 값을 담아 사용할 수 도 있다.
            td.each(function(i){
                tdArr.push(td.eq(i).text());
            });
            
            console.log("배열에 담긴 값 : "+tdArr);
            
            // td.eq(index)를 통해 값을 가져올 수도 있다.
            var no = td.eq(0).text();
            var testCode = td.eq(1).text();
            var testName = td.eq(2).text();
            var protocolIndicator = td.eq(4).text();
            var headerSize = td.eq(5).text();
			var protocolVersion = td.eq(6).text();
			var packetID = td.eq(7).text();
            var LF = td.eq(8).text();
			var RF = td.eq(9).text();
			var CE = td.eq(10).text();
			var TR = td.eq(11).text();
			var TO_A = td.eq(12).text();
			var RC = td.eq(13).text();
			var opCode = td.eq(14).text();
			var gReq_atId = td.eq(15).text();
			var sReq_atId = td.eq(16).text();
			var sReq_atSize = td.eq(17).text();
			var sReq_atData = td.eq(18).text();
			var aReq_atId = td.eq(19).text();
			var aReq_atSize = td.eq(20).text();
			var aReq_atParam = td.eq(21).text();
			var aRes_atId = td.eq(22).text();
			var aRes_atResult = td.eq(23).text();
			var aRes_atSize = td.eq(24).text();
			var aRes_atParam = td.eq(25).text();
			var eReq_atId = td.eq(26).text();
			var eReq_atSize = td.eq(27).text();
			var eReq_atData = td.eq(28).text();
			var totalSeq = td.eq(29).text();
			var cycle = td.eq(30).text();
            str +=    " No. : <font color='red'>" + no + "</font>" +
                    ", 테스트코드 : <font color='red'>" + testCode + "</font>" +
                    ", 테스트이름 : <font color='red'>" + testName + "</font>";        
            
			goChange(opCode);

            //$("#ex1_Result1").html(tr.text());        
            $("#ex1_Result2").html(str);
			$("#protocolIndicator").val(protocolIndicator);
			$("#headerSize").val(headerSize);
			$("#protocolVersion").val(protocolVersion);
			$("#packetID").val(packetID);
			$("#LF").val(LF);
			$("#RF").val(RF);
			$("#CE").val(CE);
			$("#TR").val(TR);
			$("#TO_A").val(TO_A);
			$("#RC").val(RC);
			$("#opCode").val(opCode);
			$("#gReq_atId").val(gReq_atId);
			$("#sReq_atId").val(sReq_atId);
			$("#sReq_atSize").val(sReq_atSize);
			$("#sReq_atData").val(sReq_atData);
			$("#aReq_atId").val(aReq_atId);
			$("#aReq_atSize").val(aReq_atSize);
			$("#aReq_atParam").val(aReq_atParam);
			$("#aRes_atId").val(aRes_atId);
			$("#aRes_atResult").val(aRes_atResult);
			$("#aRes_atSize").val(aRes_atSize);
			$("#aRes_atParam").val(aRes_atParam);
			$("#eReq_atId").val(eReq_atId);
			$("#eReq_atSize").val(eReq_atSize);
			$("#eReq_atData").val(eReq_atData);
			$("#totalSeq").val(totalSeq);
			$("#cycle").val(cycle);				
        });



//console.log(button_click.pi_val);