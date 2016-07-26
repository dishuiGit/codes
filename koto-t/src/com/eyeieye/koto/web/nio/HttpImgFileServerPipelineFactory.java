package com.eyeieye.koto.web.nio;

import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpChunkAggregator;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.jboss.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

 @Component("imgFileServerPipelineFactory")
public class HttpImgFileServerPipelineFactory
   implements ChannelPipelineFactory
 {

   @Autowired
   private ChannelHandler imgFileServerHandler;

   public ChannelPipeline getPipeline()
     throws Exception
   {
     ChannelPipeline pipeline = Channels.pipeline();

     pipeline.addLast("decoder", new HttpRequestDecoder());
     pipeline.addLast("aggregator", new HttpChunkAggregator(65536));
     pipeline.addLast("encoder", new HttpResponseEncoder());
     pipeline.addLast("chunkedWriter", new ChunkedWriteHandler());

     pipeline.addLast("handler", this.imgFileServerHandler);
     return pipeline;
   }
 }

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.web.nio.HttpImgFileServerPipelineFactory
 * JD-Core Version:    0.6.2
 */