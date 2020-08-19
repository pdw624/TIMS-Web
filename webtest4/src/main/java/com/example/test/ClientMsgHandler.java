package com.example.test;

import io.netty.channel.Channel;
import kr.tracom.platform.attribute.AtCode;
import kr.tracom.platform.attribute.common.AtDeviceAuth;
import kr.tracom.platform.net.config.TimsConfig;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.TimsMessageBuilder;
import kr.tracom.platform.service.manager.TransactionManager;
import kr.tracom.platform.tcp.model.TcpChannelMessage;

public class ClientMsgHandler {
	
	public static TimsMessage logTimsMsg;

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
		 //테스트용으로 101번 요청
	     TimsMessage timsSendMessage = builder.getRequest((short) 101);
	     logTimsMsg = timsSendMessage;
	     TransactionManager.write(new TcpChannelMessage(ch, null, timsSendMessage));
    }
	
	
	
	public static void requestSet(Channel ch, TimsConfig timsConfig) {
		/*
		 * TimsMessageBuilder builder = new TimsMessageBuilder(timsConfig); TimsMessage
		 * timsSendMessage = builder.setRequest((short) 101); logTimsMsg =
		 * timsSendMessage; TransactionManager.write(new TcpChannelMessage(ch, null,
		 * timsSendMessage));
		 */
    }
	
	
	
	public static void requestAction(Channel ch, TimsConfig timsConfig) {
		
    }
	
	public static void responseAction(Channel ch, TimsConfig timsConfig) {
		
    }
	

	public static void responseEvent(Channel ch, TimsConfig timsConfig) {
		
    }
	
	
	
	

}
