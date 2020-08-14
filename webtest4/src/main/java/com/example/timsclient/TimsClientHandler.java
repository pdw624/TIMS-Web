package com.example.timsclient;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.client.ClientHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;


public class TimsClientHandler extends ChannelInboundHandlerAdapter {
	private TimsClient timsClient;	
	private final ByteBuf firstMSG;
	private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
	
	public TimsClientHandler(TimsClient timsClient) {
		this.timsClient = timsClient;
		String res="IMP0010021";
		byte[] resByte = res.getBytes();
		firstMSG = Unpooled.buffer(resByte.length);
        firstMSG.writeBytes(resByte);
        logger.info("impID:{}", Arrays.toString(resByte));
        logger.info("impID:{}", res);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) {		
		ctx.channel();
	}
	
	@Override
    public void channelInactive(ChannelHandlerContext ctx) {			
		ctx.channel();
	}	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ctx.channel();
		ByteBuf buf = (ByteBuf)msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        logger.info("receive : {}", Arrays.toString(req));
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
