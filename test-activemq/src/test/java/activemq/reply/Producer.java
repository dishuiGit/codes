package activemq.reply;

import java.util.UUID;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;


public class Producer implements MessageListener{

	private static final Logger LOGGER = Logger.getLogger(Consumer.class);

	private JmsTemplate jmsTemplate;

	private Destination requestDestination;
	
	private Destination replyDestination;
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public Destination getRequestDestination() {
		return requestDestination;
	}

	public void setRequestDestination(Destination requestDestination) {
		this.requestDestination = requestDestination;
	}

	public Destination getReplyDestination() {
		return replyDestination;
	}

	public void setReplyDestination(Destination replyDestination) {
		this.replyDestination = replyDestination;
	}

	public String sendMessage(final String message){
		
		final String correlationID = UUID.randomUUID().toString();
		
		jmsTemplate.send(requestDestination, new MessageCreator() {
			
			public Message createMessage(Session session) throws JMSException {
				
				
				Message msg = session.createTextMessage(message);
				
				
				msg.setJMSReplyTo(replyDestination);
				msg.setJMSCorrelationID(correlationID);
				
				return msg;
			}
		});
		
		return null;
	}
	
	public void onMessage(Message message) {
		if(message instanceof TextMessage){
			TextMessage textMessage = (TextMessage)message;
			
			try {
				LOGGER.info("生产者收到"+textMessage.getText());
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				LOGGER.error("接收信息出错", e);
			}
		}
		
		
	}

}
