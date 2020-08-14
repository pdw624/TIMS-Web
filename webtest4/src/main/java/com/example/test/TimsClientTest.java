package com.example.test;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import kr.tracom.platform.net.config.TimsConfig;

public class TimsClientTest {
	
	private ChannelFuture clientChannel;
    private TimsConfig timsConfig;
	private String svrIp;
	private int svrPort;
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	public TimsConfig getTimsConfig() {
	    return this.timsConfig;
    }

	public TimsClientTest(String svrIp, int svrPort, TimsConfig timsConfig) {
        this.svrIp = svrIp;
        this.svrPort = svrPort;
        this.timsConfig = timsConfig;
    }

    public void run() {
        Bootstrap b = configureBootstrap(new Bootstrap(), new NioEventLoopGroup());
        clientChannel = b.connect().channel().closeFuture();  
        logger.info("연결시작");
    }
    
    public void shutdown() {
    	if(clientChannel != null) {
    		clientChannel.channel().close();
    		logger.info("셧다운");
    	}
    }
    
    public Bootstrap configureBootstrap(Bootstrap b, EventLoopGroup g) {
        b.group(g)
                .channel(NioSocketChannel.class)
                .remoteAddress(svrIp, svrPort)
                .handler(new TimsClientInitializerTest(this));
        		//.handler(new LoggingHandler(LogLevel.INFO));
        return b;
    }
}
