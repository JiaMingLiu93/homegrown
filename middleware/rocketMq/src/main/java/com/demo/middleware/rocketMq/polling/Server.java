package com.demo.middleware.rocketMq.polling;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 以下使用netty模拟一个http服务器，使用HttpURLConnection模拟客户端发送请求，DataCenter使用BlockingQueue存放数据
 *
 * @author youyu
 * @date 2020/5/21 2:36 PM
 */
public class Server {

    public static void start(final int port) throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        try {

            serverBootstrap.channel(NioServerSocketChannel.class).group(boss, worker)
                    .childOption(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-decoder", new HttpServerCodec());
                            ch.pipeline().addLast(new HttpServerHandler());
                        }
                    });

            ChannelFuture future = serverBootstrap.bind(port).sync();
            System.out.println("server start ok port is " + port);
            //同时启动数据中心服务DataCenter.start();
            DataCenter.start();
            future.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        //netty默认支持http协议，直接使用即可，启动端口为8080
        start(8080);
    }
}
