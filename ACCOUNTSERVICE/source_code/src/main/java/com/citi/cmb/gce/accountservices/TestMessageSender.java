package com.citi.cmb.gce.accountservices;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.citi.cmb.gce.accountservices.common.util.JmsTextMessageSender;

@Component
public class TestMessageSender {

	@Autowired
	JmsTextMessageSender jmsTextMessageSender;
	
	@Qualifier(value="gceJmsTemplate")
	JmsTemplate jmsTemplate;
	
	@Qualifier(value="messageResponseQueue")
	Destination messageResponseQueue;
	
	public String sendmsgResponse(String operation) throws URISyntaxException, IOException {
		if("MSG".equalsIgnoreCase(operation)) {
			Path path = Paths.get(getClass().getClassLoader().getResource("message.xml").toURI());
			Stream<String> lines = Files.lines(path);
			String data = lines.collect(Collectors.joining("\n"));
			jmsTextMessageSender.sentTextMessage(data, null, jmsTemplate, messageResponseQueue);
			System.out.println("message sent");
		}
		return "OK";
	}
	
}
