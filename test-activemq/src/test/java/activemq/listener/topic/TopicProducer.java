package activemq.listener.topic;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

public class TopicProducer {
	private JmsTemplate jmsTemplate;

	private Connection connection;

	private Destination destination;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	// 使用JMSTemplate发送消息
	public void send(final String msg) throws JMSException {
		connection = jmsTemplate.getConnectionFactory().createConnection();

		Session session = connection.createSession(false, 2);
		MessageProducer producer = session.createProducer(destination);

		producer.send(session.createTextMessage(msg));

//		jmsTemplate.
		/*producer.send(destination,new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
//				System.out.println(session.getAcknowledgeMode());
				return session.createTextMessage(msg);
			}
		});*/
	}


}
