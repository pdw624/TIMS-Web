package com.example.test;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.controller.MainController;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import kr.tracom.platform.attribute.AtCode;
import kr.tracom.platform.attribute.common.AtDeviceAuth;
import kr.tracom.platform.attribute.manager.AttributeManager;
import kr.tracom.platform.net.config.TimsConfig;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.TimsMessageBuilder;
import kr.tracom.platform.net.protocol.payload.PlCode;
import kr.tracom.platform.service.manager.TransactionManager;
import kr.tracom.platform.tcp.model.TcpChannelMessage;


public class TimsClientHandlerTest extends SimpleChannelInboundHandler<TimsMessage> {
	private TimsClientTest timsClient;	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	public static ChannelHandlerContext usableCTX;
	public static String initStr = "[RCVD Payload] : ";
	public static String getStr = "[RCVD Payload] : ";
	
	public TimsClientHandlerTest(TimsClientTest timsClient) {
		this.timsClient = timsClient;
		//서버가 메시지를 보내줘도 client측에서 attribute를 해석하지 못하면 에러코드 16발생. 아래 코드 작성 필요
		AttributeManager.bind(AttributeManager.COMMON_ATTRIBUTE);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) {		
		//responseAuth(ctx.channel(), timsClient.getTimsConfig());
		ctx.channel();
		logger.info("채널액티브");
		usableCTX = ctx;
	}
	
	@Override
    public void channelInactive(ChannelHandlerContext ctx) {			
		ctx.channel();
		logger.info("채널인액티브");
	}	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TimsMessage timsMessage) throws Exception {
		//logger.info("채널읽기");
		logger.info("받은페이로드 :{}",timsMessage.getPayload());
		if(MainController.isConnected == false) {
			MainController.isConnected = true;
		}
		
		
		if(timsMessage.getHeader().getOpCode()==PlCode.OP_INIT_REQ) {
			logger.info("Init Request Received!!!");
			//logger.info("Init Request getPayload : "+timsMessage.getPayload());
			ClientMsgHandler.responseAuth(ctx.channel(), timsClient.getTimsConfig());
			//initRequest 메시지를 Controller에 전달
			Object initObj = timsMessage.getPayload();
			initStr += String.valueOf(initObj);
			//System.out.println(initStr);
		}
		
		//ClientMsgHandler.requestGet(ctx.channel(), timsClient.getTimsConfig());
		//ClientMsgHandler.requestSet(ctx.channel(), timsClient.getTimsConfig());
		
		//ClientMsgHandler.msgSelect((String) MainController.usableMap.get("opCode"));
		if(timsMessage.getHeader().getOpCode()==PlCode.OP_GET_RES) {
			logger.info("Get Response Received!!!");
			Object getObj = timsMessage.getPayload();
			getStr += String.valueOf(getObj);
			//System.out.println(getStr);
		}
		
		
		//logger.info("Get Request 보냄!!!");
		//getStr = "";
		//initStr = "";
	}
	
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
		//logger.info("채널읽기완료");
	}

	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable t) {
        t.printStackTrace();
        logger.info("예외처리");
        if (!ctx.channel().isActive()) {
            ctx.writeAndFlush("").addListener(ChannelFutureListener.CLOSE);
            logger.info("예외처리");
        }
    }
	
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        final EventLoop loop = ctx.channel().eventLoop();
        logger.info("채널미등록");
        
        loop.schedule(new Runnable() {
            public void run() {
                Bootstrap b = timsClient.configureBootstrap(new Bootstrap(), loop);
                ChannelFuture f = b.connect();
        
                try {
                    f.channel().closeFuture().sync();
                    logger.info("채널미등록");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 10, TimeUnit.SECONDS);
    }   	
}
