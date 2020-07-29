package com.demo.middleware.rocketMq.polling;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

/**
 * @author youyu
 * @date 2020/5/21 2:38 PM
 */
public class HttpServerHandler extends ChannelInboundHandlerAdapter {

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            //获取到客户端的请求之后，从数据中心获取一条消息，如果没有数据，会进行等待，直到有数据为止；然后使用FullHttpResponse返回给客户端
            httpResponse.content().writeBytes(DataCenter.getData().getBytes());
            httpResponse.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");
            httpResponse.headers().set(HttpHeaders.Names.CONTENT_LENGTH, httpResponse.content().readableBytes());
            ctx.writeAndFlush(httpResponse);
        }
    }
}
