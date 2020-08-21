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
function goChange(val){
	if(val=="OP_GET_REQ"){
		//document.all.getReq.style.display="";
		document.all.getReq.style.display="";
		document.all.setReq.style.display="none";
		document.all.actionReq.style.display="none";
		document.all.actionRes.style.display="none";
		document.all.eventReq.style.display="none";
		alert(val);
	}
	else if(val=="OP_SET_REQ"){
		document.all.setReq.style.display="";
		document.all.getReq.style.display="none";
		document.all.actionReq.style.display="none";
		document.all.actionRes.style.display="none";
		document.all.eventReq.style.display="none";
		alert(val);
	}else if(val=="OP_ACTION_REQ"){
		document.all.actionReq.style.display="";
		document.all.getReq.style.display="none";
		document.all.setReq.style.display="none";
		document.all.actionRes.style.display="none";
		document.all.eventReq.style.display="none";
		alert(val);
	}
	else if(val=="OP_ACTION_RES"){
		document.all.actionRes.style.display="";
		document.all.getReq.style.display="none";
		document.all.setReq.style.display="none";
		document.all.actionReq.style.display="none";
		document.all.eventReq.style.display="none";
		alert(val);
	}
	else if(val=="OP_EVENT_REQ"){
		document.all.eventReq.style.display="";
		document.all.getReq.style.display="none";
		document.all.setReq.style.display="none";
		document.all.actionReq.style.display="none";
		document.all.actionRes.style.display="none";
		alert(val);
	}
	
}


function button_click(){
	//pi_val = protocolIndicator.options[protocolIndicator.selectedIndex].value;
	//console.log(button_click.pi_val);
	document.getElementById("isSend").value="true";
	temp = document.getElementById("isSend").value;
	alert("hello " + temp);
}

//console.log(button_click.pi_val);