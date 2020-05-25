package com.citi.cmb.gce.accountservices.msg.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.citi.cmb.gce.accountservices.msg.handler.MessageHandler;
import com.citi.cmb.gce.accountservices.msg.processor.MessageProcessor;
import com.citi.cmb.gce.accountservices.msg.vo.MessageVO;

@Component
public class MessageReceiver implements MessageListener {

	@Autowired
	private Logger logger;

	@Autowired
	private MessageHandler messageHandler;

	@Autowired
	private MessageProcessor messageProcessor;

	@Override
	public void onMessage(Message xmlMessage) {
		final TextMessage objMsg = (TextMessage) xmlMessage;

		String textMessage;
		try {
			textMessage = objMsg.getText();
			logger.info("Received message: " + textMessage);
			MessageVO messageVO = messageHandler.handleMessage(textMessage);
			
			if (messageVO != null) {
				messageProcessor.processMessage(messageVO, textMessage);
			}

		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

}
