package com.citi.cmb.gce.accountservices.common.util;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.bind.util.ValidationEventCollector;

import org.springframework.stereotype.Component;

@Component
public class JAXBValidator extends ValidationEventCollector {

	@Override
	public boolean handleEvent(ValidationEvent event) {
		if (event.getSeverity() == event.ERROR || event.getSeverity() == event.FATAL_ERROR) {
			ValidationEventLocator locator = event.getLocator();
			//Change RT exception to something more appropriate
			throw new RuntimeException("XML validation exception: "+ event.getMessage()+
					" at row: "+locator.getLineNumber()+
					" column: "+locator.getColumnNumber());
		}
		return true;
	}
}
