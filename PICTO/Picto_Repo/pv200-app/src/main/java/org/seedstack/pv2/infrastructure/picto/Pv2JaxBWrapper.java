package org.seedstack.pv2.infrastructure.picto;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.seedstack.pv2.picxml.beans.PictoDescription;

	/*
	 * class Pv2JaxBWrapper: Unmarshalling the XML file to object
	 */
	public class Pv2JaxBWrapper {

	    /**
	     * Unmarshal XML data from the specified XML file and return the result as object.
	     * 
	     * @param importFile - the file to unmarshal XML data
	     * @return the newly created object of the java content
	     * @throws JAXBException If an error while unmarshalling.
	     */
	    public static PictoDescription unmarshal(File importFile) throws JAXBException {
	        PictoDescription picto = null;

	        final JAXBContext context = JAXBContext.newInstance(PictoDescription.class);
	        Unmarshaller um = context.createUnmarshaller();
	        picto = (PictoDescription) um.unmarshal(importFile);

	        return picto;
	    }

	}