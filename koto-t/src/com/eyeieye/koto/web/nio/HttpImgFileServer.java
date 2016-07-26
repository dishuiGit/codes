package com.eyeieye.koto.web.nio;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

 @Component
public class HttpImgFileServer
 {

   @Value("${app.nio.server.port:8090}")
   private int port = 8090;

   @Autowired
   private ChannelPipelineFactory channelPipelineFactory;
   private ServerBootstrap bootstrap;
   private Channel channel;

   public void run() {
     this.bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

     this.bootstrap.setPipelineFactory(this.channelPipelineFactory);
     this.bootstrap.setOption("reuseAddress", Boolean.valueOf(true));

     this.channel = this.bootstrap.bind(new InetSocketAddress(this.port));
   }

   @PostConstruct
   public void init() {
     run();
   }

   @PreDestroy
   public void shutdown() {
     this.channel.close().awaitUninterruptibly();
     this.bootstrap.releaseExternalResources();
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.web.nio.HttpImgFileServer
 * JD-Core Version:    0.6.2
 */