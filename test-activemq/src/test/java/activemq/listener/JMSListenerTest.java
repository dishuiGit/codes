package activemq.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JMSListenerTest {
    public static void main(String[] args) {  
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-listener.xml");  
          
        ListenerProducer producer = (ListenerProducer) applicationContext.getBean("listenerProducer");
        ListenerConsumer consumer = (ListenerConsumer)applicationContext.getBean("listenerConsumer1");
        
        try {
			consumer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        producer.send("你在哪里啊？2016-06-28 10:55:13");  
        producer.send("嗨，你好吗？");  
          
    }  
}
