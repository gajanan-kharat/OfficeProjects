package com.citi.cmb.gce.accountservices.common.util;

import java.util.Map;
import java.util.Map.Entry;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class JmsTextMessageSender {

	private static final String TIBCO_PRESERVE = "JMS_TIBCO_PRESERVE_UNDELIVERED";

	public void sentTextMessage(final String text, final Map<String, String> propertyMap,
			JmsTemplate jmsTemplate, final Destination destination) {
		jmsTemplate.send(destination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage(text);
				message.clearProperties();
				
				if(propertyMap !=null) {
					for(Entry<String,String>property:propertyMap.entrySet()) {
						message.setStringProperty(property.getKey(), property.getValue());
					} 
				}
				message.setBooleanProperty(TIBCO_PRESERVE, true);
				return message;
			}
		});
	}

}
