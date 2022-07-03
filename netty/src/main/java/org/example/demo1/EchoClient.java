package org.example.demo1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {
    static final String HOST = System.getProperty("host", "127.0.0.1");// java -Dhost=100 vm参数
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    public static void main(String[] args) {
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            Bootstrap boot = new Bootstrap();
            boot.group(work)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                        }
                    });

            ChannelFuture connect = boot.connect(HOST, PORT);
//            connect.channel().closeFuture().sync();

            System.out.printf("started client %s:%s%n", HOST, PORT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            work.shutdownGracefully();
        }
    }
}
