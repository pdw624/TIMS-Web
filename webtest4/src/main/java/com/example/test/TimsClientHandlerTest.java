package com.example.test;

import java.util.concurrent.TimeUnit;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import kr.tracom.platform.attribute.AtCode;
import kr.tracom.platform.net.config.TimsConfig;
import kr.tracom.platform.net.protocol.TimsMessage;
import kr.tracom.platform.net.protocol.TimsMessageBuilder;
import kr.tracom.platform.service.manager.TransactionManager;
import kr.tracom.platform.tcp.model.TcpChannelMessage;


public class TimsClientHandlerTest extends SimpleChannelInboundHandler<TimsMessage> {
	private TimsClientTest timsClient;	
	
    public static void responseAuth(Channel ch, TimsConfig timsConfig) {
  	
        TimsMessageBuilder builder = new TimsMessageBuilder(timsConfig);
        TimsMessage timsSendMessage = builder.initResponse(AtCode.DEVICE_AUTH, null);
        TransactionManager.write(new TcpChannelMessage(ch, null, timsSendMessage));
    }
    
	public TimsClientHandlerTest(TimsClientTest timsClient) {
		this.timsClient = timsClient;
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) {		
		responseAuth(ctx.channel(), timsClient.getTimsConfig());
		ctx.channel();
		
	}
	
	@Override
    public void channelInactive(ChannelHandlerContext ctx) {			
		ctx.channel();
	}	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TimsMessage timsMessage) throws Exception {
		this.channelRead0((ChannelHandlerContext) ctx.channel(), timsMessage);
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable t) {
        t.printStackTrace();
        if (!ctx.channel().isActive()) {
            ctx.writeAndFlush("").addListener(ChannelFutureListener.CLOSE);
        }
    }
	
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        final EventLoop loop = ctx.channel().eventLoop();
        loop.schedule(new Runnable() {
            public void run() {
                Bootstrap b = timsClient.configureBootstrap(new Bootstrap(), loop);
                ChannelFuture f = b.connect();
                try {
                    f.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 10, TimeUnit.SECONDS);
    }   	
}
