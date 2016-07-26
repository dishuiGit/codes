package com.eyeieye.koto.validate;

import org.jboss.netty.handler.codec.http.HttpRequest;

public abstract interface RefererVaildator
{
  public abstract boolean isForbiddenRequest(HttpRequest paramHttpRequest);
}

/* Location:           E:\codes\work\koto\WEB-INF\classes\
 * Qualified Name:     com.eyeieye.koto.validate.RefererVaildator
 * JD-Core Version:    0.6.2
 */