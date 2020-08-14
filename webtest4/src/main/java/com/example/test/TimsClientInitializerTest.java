package com.example.test;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
//import kr.tracom.platform.tcp.cs.TimsChannelEvent;
//import kr.tracom.platform.tcp.cs.TimsDecoder;
//import kr.tracom.platform.tcp.cs.TimsEncoder;
import kr.tracom.platform.net.config.TimsConfig;
import kr.tracom.platform.tcp.cs.TimsDecoder;
import kr.tracom.platform.tcp.cs.TimsEncoder;

import java.util.concurrent.TimeUnit;


public class TimsClientInitializerTest extends ChannelInitializer<SocketChannel> {
	private TimsClientHandlerTest clientHandler;
	private TimsConfig timsConfig;

    public TimsClientInitializerTest(TimsClientTest timsClient) {
    	this.clientHandler = new TimsClientHandlerTest(timsClient);
    	this.timsConfig = timsClient.getTimsConfig();
    }
    
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();        
        p.addLast("read_timeout", new ReadTimeoutHandler(timsConfig.getReadTimeout(), TimeUnit.SECONDS));
        
        p.addLast("decoder", new TimsDecoder(timsConfig));
        p.addLast("encoder", new TimsEncoder(timsConfig));
        p.addLast("handler", clientHandler);
    }
}
