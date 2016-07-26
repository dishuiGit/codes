
package com.eyeieye.koto.remote.broker;

import com.eyeieye.melody.util.StringUtil;
import com.eyeieye.melody.web.url.MultiHostURLBroker;

public class KotoMultiHostURLBroker extends MultiHostURLBroker
{
    public class KotoMultiHostQueryData extends com.eyeieye.melody.web.url.URLBroker.QueryData
    {

        public KotoMultiHostQueryData size(int width)
        {
            if(width <= 0)
            {
                throw new IllegalArgumentException("width can't less than 0.");
            } else
            {
                query.append("_").append(width).append(".img");
                return this;
            }
        }

        public KotoMultiHostQueryData size(int width, int height)
        {
            if(width <= 0)
                throw new IllegalArgumentException("width can't less than 0.");
            if(height <= 0)
            {
                throw new IllegalArgumentException("height can't less than 0.");
            } else
            {
                query.append("_").append(width);
                query.append("_").append(height).append(".img");
                return this;
            }
        }

        final KotoMultiHostURLBroker this$0;

        public KotoMultiHostQueryData(String id)
        {
            this$0 = KotoMultiHostURLBroker.this;
            if(StringUtil.isEmpty(id))
                id = "";
            query = new StringBuilder();
            query.append(getServer(getTargetHash(id)));
            if(!id.startsWith("img/") && !id.startsWith("/img/"))
                query.append("/img/");
            else
                query.append("/");
            query.append(id);
            if(!id.endsWith(".img"))
                query.append(".img");
        }
    }


    public KotoMultiHostURLBroker()
    {
    }

    public KotoMultiHostQueryData setImgId(String oid)
    {
        return new KotoMultiHostQueryData(oid);
    }

    private static final String ImgPathPrefix = "/img/";
    private static final String ImgNamePostfix = ".img";
    private static final String ImgNameSeparator = "_";


}
