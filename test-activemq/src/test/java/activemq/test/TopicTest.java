package activemq.test;

import activemq.ProxyJMSConsumer;
import activemq.listener.topic.TopicProducer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.JMSException;

/**
 * Created by dishui on 2016/7/7.
 */
public class TopicTest {

    public void sendTopic(String msg){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-topic.xml");
        TopicProducer topicProducer = (TopicProducer)applicationContext.getBean("topicProducer");
        try {
            topicProducer.send(msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TopicTest().sendTopic("hello3");
    }

}
