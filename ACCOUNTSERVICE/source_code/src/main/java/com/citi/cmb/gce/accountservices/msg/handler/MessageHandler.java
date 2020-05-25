package com.citi.cmb.gce.accountservices.msg.handler;

import java.io.ByteArrayInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.citi.cmb.gce.accountservices.common.util.JAXBValidator;
import com.citi.cmb.gce.accountservices.msg.vo.MessageVO;

@Component
public class MessageHandler {

	@Autowired
	private JAXBValidator jaxbValidator;
	
	public MessageVO handleMessage(String xmlMessage){
		
		MessageVO messageVO = new MessageVO();
		PublishAccRestMsg accRestMsg = new PublishAccRestMsg(); // auto generated from XSD
		byte[] bytes = xmlMessage.getBytes();
		
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		JAXBContext jaxbContext;
		
		try {
			jaxbContext = JAXBContext.newInstance(PublishAccRestMsg.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			unmarshaller.setEventHandler(jaxbValidator);
			accRestMsg = (PublishAccRestMsg)unmarshaller.unmarshal(inputStream);
			if(accRestMsg!=null) {
				getMessageVoObject(accRestMsg);
			}else {
				return null;
			}
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return messageVO;
	}

	private MessageVO getMessageVoObject(PublishAccRestMsg accRestMsg) {
		//Set all the fields into VO object and then return
		return new MessageVO();
	}
}


class PublishAccRestMsg{
	
}