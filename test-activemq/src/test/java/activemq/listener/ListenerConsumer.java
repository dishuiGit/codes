package activemq.listener;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;

public class ListenerConsumer implements MessageListener {

	private static final Logger LOGGER = Logger.getLogger(ListenerConsumer.class);
	
	private JmsTemplate jmsTemplate;
	private String myId = "foo";
	private Destination destination;
	private Connection connection;
	private Session session;
	private MessageConsumer consumer;

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public void start() throws JMSException {
		String selector = "next = '" + myId + "'";

		try {
			ConnectionFactory factory = jmsTemplate.getConnectionFactory();
			connection = factory.createConnection();

			// we might be a reusable connection in spring
			// so lets only set the client ID once if its not set
			synchronized (connection) {
				if (connection.getClientID() == null) {
					connection.setClientID(myId);
				}
			}

			connection.start();

			session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
			consumer = session.createConsumer(destination, selector, false);
			consumer.setMessageListener(this);
		} catch (JMSException ex) {
			LOGGER.error("", ex);
			throw ex;
		}
	}

	public void onMessage(Message message) {
		TextMessage textMsg = (TextMessage) message;

		try {
			int i = 1/0;
//			 message.
			// message.acknowledge();
			System.out.println("1接收到了消息，消息内容是：" + textMsg.getText() + ";ack mode");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
