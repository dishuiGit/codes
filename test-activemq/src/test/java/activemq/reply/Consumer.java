package activemq.reply;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class Consumer implements MessageListener{

	private static final Logger LOGGER = Logger.getLogger(Consumer.class);
	
	private JmsTemplate jmsTemplate;
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		
		try {
			final String text = textMessage.getText();
			Destination destination = textMessage.getJMSReplyTo();
			final String jmsCorrelationID = textMessage.getJMSCorrelationID();
			
			LOGGER.info("消费者"+this.getClass().getName()+"收到消息<"+text+">"+jmsCorrelationID);
			
			jmsTemplate.send(destination, new MessageCreator() {
				
				public Message createMessage(Session session) throws JMSException {
					Message msg = session.createTextMessage("消费者<"+text + ">的应答！"+jmsCorrelationID);
					msg.setJMSCorrelationID(jmsCorrelationID);
					return msg;
				}
			});
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
