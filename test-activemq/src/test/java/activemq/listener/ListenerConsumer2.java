package activemq.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ListenerConsumer2 implements MessageListener {

	public void onMessage(Message message) {
        TextMessage textMsg = (TextMessage) message;  
        try {  
            System.out.println("2接收到了消息，消息内容是：" + textMsg.getText());  
        } catch (JMSException e) {  
            e.printStackTrace();  
        }  
	}
 
}
