/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package activemq.test;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:applicationContext-jms-ack.xml")
public class JmsClientAckTest implements MessageListener {

	private static final Logger LOGGER = Logger.getLogger(JmsClientAckTest.class);
	
	private JmsTemplate jmsTemplate;
	
    private Connection connection;
    private boolean dontAck;

    @Before
    public void before() throws JMSException{
    	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-jms-ack.xml");
    	jmsTemplate = (JmsTemplate)applicationContext.getBean("jmsTemplate");
    	
    	PooledConnectionFactory pooledConnectionFactory = (PooledConnectionFactory)applicationContext.getBean("jmsFactory");
    	connection = pooledConnectionFactory.createConnection();
    }

    /**
     * Tests if acknowleged messages are being consumed.
     * 
     * @throws javax.jms.JMSException
     */
    @Test
    public void testAckedMessageAreConsumed() throws Exception {
    	
    	Connection connection2 = jmsTemplate.getConnectionFactory().createConnection();
    	LOGGER.info(connection2.hashCode());
    	LOGGER.info(connection.hashCode());
    	
        connection.start();
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Queue queue = session.createQueue("test");
        MessageProducer producer = session.createProducer(queue);
        producer.send(session.createTextMessage("Hello"));

        // Consume the message...
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(this);

        Thread.sleep(10000);

        // Reset the session.
        session.close();

        session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

        // Attempt to Consume the message...
        consumer = session.createConsumer(queue);
        Message msg = consumer.receive(1000);

        LOGGER.info(msg);
        
        session.close();
    }

    /**
     * Tests if unacknowleged messages are being redelivered when the consumer
     * connects again.
     * 
     * @throws javax.jms.JMSException
     */
    public void testUnAckedMessageAreNotConsumedOnSessionClose() throws Exception {
        connection.start();
        // don't aknowledge message on onMessage() call
        dontAck = true;
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Queue queue = session.createQueue("test");
        MessageProducer producer = session.createProducer(queue);
        producer.send(session.createTextMessage("Hello"));

        // Consume the message...
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(this);
        // Don't ack the message.

        // Reset the session. This should cause the Unacked message to be
        // redelivered.
        session.close();

        Thread.sleep(10000);
        session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        // Attempt to Consume the message...
        consumer = session.createConsumer(queue);
        Message msg = consumer.receive(2000);
        
        LOGGER.info(msg);
        
        msg.acknowledge();

        session.close();
    }

    public void onMessage(Message message) {

//    	LOGGER.info(message);
        if (!dontAck) {
            try {
                message.acknowledge();
            	LOGGER.info("no ack!");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
    
	
	@Test
	public void reConnectConsumer() throws JMSException{
		connection.start();
		//创建session
		Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		// queue
		Queue queue = session.createQueue("test");
		MessageConsumer consumer = session.createConsumer(queue);
		consumer.setMessageListener(this);
		
		Message msg = consumer.receive();

        LOGGER.info(msg);
        
        session.close();
	}
    
}
