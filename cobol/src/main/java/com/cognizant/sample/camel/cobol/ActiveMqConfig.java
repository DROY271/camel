package com.cognizant.sample.camel.cobol;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;



/*
 * This is a mock MQ service that manipulated the input text.
 */
@EnableJms
@Configuration
public class ActiveMqConfig {

	@Bean
	public Destination replyQueue(ActiveMQConnectionFactory cf) throws JMSException {
		Connection c = cf.createConnection();
		c.start();
		Session s = c.createSession(false, Session.AUTO_ACKNOWLEDGE);
		return s.createQueue("reply-queue");
	}

	@Component
	public static class Listener {

		@Autowired
		JmsOperations jms;

		@Autowired
		@Qualifier("replyQueue")
		Destination dest;

		@JmsListener(destination = "timed-request")
		public void process(final Message message) throws JMSException {

			System.err.println("Received Message :" + message);
			String text = null;
			if (message instanceof BytesMessage) {
				BytesMessage bm = (BytesMessage) message;
				byte[] bytes = new byte[(int) bm.getBodyLength()];
				bm.readBytes(bytes);
				text = new String(bytes);
			}

			jms.convertAndSend(dest, "Modified text " + text, new MessagePostProcessor() {

				@Override
				public Message postProcessMessage(Message m) throws JMSException {
					m.setJMSCorrelationID(message.getJMSCorrelationID());
					return m;
				}
			});

		}
	}

}
