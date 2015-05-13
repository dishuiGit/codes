package cn.dishui.test;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Controller;

import redis.clients.jedis.Jedis;

@Controller
public class TestSolr extends SpringBaseTest {
	@Resource
	private HttpSolrServer solrServer;
	
	@Test
	public void testSolr() throws Exception{
	
		SolrInputDocument document = new SolrInputDocument();
		//向文档中添加域
		document.addField("id", "1111");
		//document.addField("hah", "hah");
		//solr索引库没有对应的field
		//System.out.println(jedis.get("1"));
		
		//向索引库中添加文档
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
	
	

}
