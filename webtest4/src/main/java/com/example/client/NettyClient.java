package com.example.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyClient {

	
    public void connect(int port, String host) throws Exception{
        //客户端启动类
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new SimpleMessageToByteEncoder());
                        ch.pipeline().addLast(new SimpleByteToMessageDecoder());
                        ch.pipeline().addLast(new ClientHandler());
                    }
                })
                .handler(new LoggingHandler(LogLevel.INFO));
                
        
            //同步等待
            ChannelFuture future = bootstrap.connect(host, port).sync();       
            if(future.isSuccess()) { 
            	System.out.println("TIMS connected!!");
            }
            //等待客户端连接端口关闭
            future.channel().closeFuture().sync();
        } finally {
            //优雅关闭 线程组
            group.shutdownGracefully();
        }
    }

//    public static void main(String[] args) {
//        int port = 8085;
//        NettyClient client = new NettyClient();
//        try {
//            client.connect(port, "192.168.34.6");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}