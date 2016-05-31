import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bulk.pojo.Mlq_product_pictureQuery;
import com.bulk.service.Mlq_product_pictureService;
import com.bulk.service.impl.Mlq_product_pictureServiceImpl;

public class BulkUpdate {
	
	private static final Logger logger = LoggerFactory.getLogger(Mlq_product_pictureServiceImpl.class);
	private ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

	public void test() {
		Mlq_product_pictureService mlq_product_pictureService = (Mlq_product_pictureService) context
				.getBean("mlq_product_pictureService");
		Mlq_product_pictureQuery mppq = new Mlq_product_pictureQuery();
		int count = mlq_product_pictureService.countByExample(mppq);
		logger.info(count + "");
	}

	public static void main(String[] args) {
		BulkUpdate bu = new BulkUpdate();
		bu.test();
	}
}
