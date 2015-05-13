package cn.itcast;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.common.junit.SpringJunitTest;

/**
 * 测试Solr
 * @author lx
 *
 */
public class TestSolr extends SpringJunitTest{

	
	@Autowired
	private SolrServer solrServer;
	
	//添加
	@Test
	public void testAdd() throws Exception {
		//创建SolrInputDocument
		SolrInputDocument doc = new SolrInputDocument();
		//商品信息  
		//ID
		doc.setField("id", 4);
		//商品名称
		doc.setField("name", "瑜伽服=1125");
		//价格 float
		doc.setField("price", 666);
		//添加数据
		solrServer.add(doc);
		//提交事务
		solrServer.commit();
		
	}
	//查询
	@Test
	public void testFind() throws Exception {
		
		String baseURL = "http://192.168.200.150:8080/solr";
		//实例化Solr服务器  SolrJ  java
		SolrServer  solrServer = new HttpSolrServer(baseURL);
		//查询对象
		SolrQuery params = new SolrQuery();
		//设置通过名称
		params.set("q", "name:瑜伽服");
		
		//查询  
		QueryResponse response = solrServer.query(params);
		//文档集合
		SolrDocumentList docs = response.getResults();
		
		for (SolrDocument solrDocument : docs) {
			//为什么是字符串类型
			String id = (String) solrDocument.get("id");
			
			System.out.println("ID:" + id);
			
			String name = (String) solrDocument.get("name");
			
			System.out.println("Name:" + name);
			float price = (float) solrDocument.get("price");
			System.out.println("价格:" + price);
			
			//
		}
	}
}
