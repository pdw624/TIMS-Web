package com.example.test;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.controller.MainController;

import io.netty.channel.Channel;
import kr.tracom.platform.attribute.AtCode;
import kr.tracom.platform.attribute.common.AtDeviceAuth;
import kr.tracom.platform.attribute.common.AtFtpResultResponse;
import kr.tracom.platform.attribute.common.AtTimeStamp;
import kr.tracom.platform.attribute.common.AtTimeStampRequest;
import kr.tracom.platform.net.config.TimsConfig;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.TimsMessageBuilder;
import kr.tracom.platform.net.protocol.attribute.AtMessage;
import kr.tracom.platform.service.config.PlatformConfig;
import kr.tracom.platform.service.manager.TransactionManager;
import kr.tracom.platform.tcp.model.TcpChannelMessage;

public class ClientMsgHandler {
	
	public static TimsMessage logTimsMsg;
	//public static MainController mc = new MainController();
	//public static Channel ch = TimsClientHandlerTest.usableCTX.channel();
	
	public static void responseAuth(Channel ch, TimsConfig timsConfig) {
		AtDeviceAuth deviceAuth = new AtDeviceAuth();
    	deviceAuth.setDeviceId("IMP0010000");
        TimsMessageBuilder builder = new TimsMessageBuilder(timsConfig);
        TimsMessage timsSendMessage = builder.initResponse(AtCode.DEVICE_AUTH, deviceAuth);
        logTimsMsg = timsSendMessage;
        TransactionManager.write(new TcpChannelMessage(ch, null, timsSendMessage));
    }
	
	
	public static void requestGet(Channel ch, TimsConfig timsConfig) {
		 TimsMessageBuilder builder = new TimsMessageBuilder(timsConfig);
		 //테스트용으로 100번 요청
	     TimsMessage timsSendMessage = builder.getRequest((short)Integer.parseInt((String) MainController.usableMap.get("gReq_atId")));
	     logTimsMsg = timsSendMessage;
	     TransactionManager.write(new TcpChannelMessage(ch, null, timsSendMessage));
    }
	
	
	//TimeStamp Attribute 사용 (임시)
	public static void requestSet(Channel ch, TimsConfig timsConfig) {
//		AtMessage myMsg = new AtMessage();
//		myMsg.setAttrId((short)Integer.parseInt((String) MainController.usableMap.get("atIdSreq")));
//		myMsg.setAttrSize((short)Integer.parseInt((String) MainController.usableMap.get("atSizeSreq")));
//		//myMsg.setAttrData((String) MainController.usableMap.get("atSizeSreq"));
//		
//		TimsMessageBuilder builder = new TimsMessageBuilder(timsConfig);
//		TimsMessage timsSendMessage = builder.setRequest(myMsg); 
//		logTimsMsg = timsSendMessage; 
//		TransactionManager.write(new TcpChannelMessage(ch, null,timsSendMessage));	 
		
		//
		
		AtTimeStamp atData = new AtTimeStamp(DateTime.now().toString(PlatformConfig.PLF_DT_FORMAT));

        TimsMessageBuilder builder = new TimsMessageBuilder(timsConfig);
        System.out.println("////////////////////////////////////////////////////////////////");
        System.out.println("atData= " +atData.getLog()+", getAttrId= " +atData.getAttrId());
        TimsMessage timsMessage = builder.setRequest(atData);

        
        //System.out.println("setRequest log= " +timsMessage.getLog());
        TcpChannelMessage tcpChannelMessage = new TcpChannelMessage(ch, null, timsMessage);
        tcpChannelMessage.setResponse(true);
        TransactionManager.write(tcpChannelMessage);
    }
	
	
	//TimeStamp Attribute 사용 (임시)
	public static void requestAction(Channel ch, TimsConfig timsConfig) {
		AtTimeStampRequest atData = new AtTimeStampRequest();
        atData.setTimeStamp(new AtTimeStamp(DateTime.now().toString(PlatformConfig.PLF_DT_FORMAT)));

        TimsMessageBuilder builder = new TimsMessageBuilder(timsConfig);
        TimsMessage timsMessage = builder.actionRequest(atData);

        TcpChannelMessage tcpChannelMessage = new TcpChannelMessage(ch, null, timsMessage);
        tcpChannelMessage.setResponse(true);
        TransactionManager.write(tcpChannelMessage);
	}
	
	public static void responseAction(Channel ch, TimsConfig timsConfig) {
		AtFtpResultResponse atResponse = new AtFtpResultResponse();
        //atResponse.setResult((short)Integer.parseInt((String) MainController.usableMap.get("resultAres")));
        atResponse.setResult(MainController.toByte(MainController.usableMap,"aRes_atResult"));
        TimsMessageBuilder builder = new TimsMessageBuilder(timsConfig);
        TimsMessage timsMessage = builder.actionResponse(atResponse);
        TcpChannelMessage tcpChannelMessage = new TcpChannelMessage(ch, null, timsMessage);
        tcpChannelMessage.setPacketId((byte) 0);

        TransactionManager.write(tcpChannelMessage);
    }
	
//
//	public static void requestEvent(Channel ch, TimsConfig timsConfig) {
//		
//    }
	
	
	public static void msgSelect(String opCode) {
		//TimsClientTest timsClient = new TimsClientTest(opCode, 0, null);
		
		if(opCode.equals("OP_GET_REQ")) {
			requestGet(TimsClientHandlerTest.usableCTX.channel(), MainController.tct.getTimsConfig());
		}
		else if(opCode.equals("OP_SET_REQ")) {
			requestSet(TimsClientHandlerTest.usableCTX.channel(), MainController.tct.getTimsConfig());
		}
		else if(opCode.equals("OP_ACTION_REQ")) {
			requestAction(TimsClientHandlerTest.usableCTX.channel(), MainController.tct.getTimsConfig());
		}
		else if(opCode.equals("OP_ACTION_RES")) {
			responseAction(TimsClientHandlerTest.usableCTX.channel(), MainController.tct.getTimsConfig());
		}
		else if(opCode.equals("OP_EVENT_REQ")) {
			
		}else {
			
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	
//	public static void responseAuth(TimsConfig timsConfig) {
//		AtDeviceAuth deviceAuth = new AtDeviceAuth();
//    	deviceAuth.setDeviceId("IMP0010000");
//        TimsMessageBuilder builder = new TimsMessageBuilder(timsConfig);
//        TimsMessage timsSendMessage = builder.initResponse(AtCode.DEVICE_AUTH, deviceAuth);
//        logTimsMsg = timsSendMessage;
//        TransactionManager.write(new TcpChannelMessage( TimsClientHandlerTest.usableCTX.channel(), null, timsSendMessage));
//    }
//	
//	
//	public static void requestGet(TimsConfig timsConfig) {
//		 TimsMessageBuilder builder = new TimsMessageBuilder(timsConfig);
//		 //테스트용으로 101번 요청
//	     TimsMessage timsSendMessage = builder.getRequest((short)Integer.parseInt((String) MainController.usableMap.get("atIdGreq")));
//	     logTimsMsg = timsSendMessage;
//	     TransactionManager.write(new TcpChannelMessage(TimsClientHandlerTest.usableCTX.channel(), null, timsSendMessage));
//    }
//	
//	
//	
//	public static void requestSet(TimsConfig timsConfig) {
//		AtMessage myMsg = new AtMessage();
//		myMsg.setAttrId((short)Integer.parseInt((String) MainController.usableMap.get("atIdSreq")));
//		myMsg.setAttrSize((short)Integer.parseInt((String) MainController.usableMap.get("atSizeSreq")));
//		//myMsg.setAttrData((String) MainController.usableMap.get("atSizeSreq"));
//		
//		TimsMessageBuilder builder = new TimsMessageBuilder(timsConfig);
//		TimsMessage timsSendMessage = builder.setRequest(myMsg); 
//		logTimsMsg = timsSendMessage; 
//		TransactionManager.write(new TcpChannelMessage( TimsClientHandlerTest.usableCTX.channel(), null,timsSendMessage));
//		 
//    }
//	
//	
//	
//	public static void requestAction(TimsConfig timsConfig) {
//		
//    }
//	
//	public static void responseAction(TimsConfig timsConfig) {
//		
//    }
//	
//
//	public static void responseEvent(TimsConfig timsConfig) {
//		
//    }
//	
//	
//	
//	public static void msgSelect(String opCode) {
//		//TimsClientTest timsClient = new TimsClientTest(opCode, 0, null);
//		
//		if(opCode.equals("OP_GET_REQ")) {
//			requestGet(MainController.tct.getTimsConfig());
//		}
//		else if(opCode.equals("OP_SET_REQ")) {
//			requestSet(MainController.tct.getTimsConfig());
//		}
//		else if(opCode.equals("OP_ACTION_REQ")) {
//			
//		}
//		else if(opCode.equals("OP_ACTION_RES")) {
//			
//		}
//		else if(opCode.equals("OP_EVENT_REQ")) {
//			
//		}else {
//			
//		}
//	}
	

}
