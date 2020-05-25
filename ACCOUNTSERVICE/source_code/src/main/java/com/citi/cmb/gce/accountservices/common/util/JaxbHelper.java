package com.citi.cmb.gce.accountservices.common.util;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Validator;

import org.xml.sax.InputSource;

public class JaxbHelper {
//The class provides buffer from marshaller, unmarshaller objects
	
	protected JAXBContext objCtx = null;
	protected Unmarshaller objUnMrsh = null;
	protected Marshaller objMrsh = null;
	protected Validator objValidator = null;
	
	/**
	 * 
	 * @param strPkgName: The path of the package of JAXB classes
	 * @throws JAXBException 
	 */
	public JaxbHelper(final String strPkgName) throws JAXBException {
		this.objCtx= JAXBContext.newInstance(strPkgName);
	}
/**
 * Return unmarshalled object from given input source
 * @param input
 * @param bValidating
 * @return
 * @throws JAXBException
 */
	public Object unmarshalXml(final String input, final boolean bValidating) throws JAXBException {
		String myString = input;
		byte[] currentXmlBytes;
		try {
			currentXmlBytes= myString.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new JAXBException(e);
		}
		ByteArrayInputStream inputStream = new ByteArrayInputStream(currentXmlBytes);
		if(this.objUnMrsh==null) {
			this.objUnMrsh = this.objCtx.createUnmarshaller();
		}
		return this.objUnMrsh.unmarshal(inputStream);
	}
	
	/**
	 * Return unmarshalled object from given input source
	 * @param ipSource
	 * @param bValidating
	 * @return
	 * @throws JAXBException
	 */
	public Object unmarshalXml(final InputSource ipSource, final boolean bValidating) throws JAXBException {
		if(this.objUnMrsh==null) {
			this.objCtx.createUnmarshaller();
		}
		this.objUnMrsh.setValidating(bValidating);
		return this.objUnMrsh.unmarshal(ipSource);
	}
	
	public boolean marshalXml(final Object xmlNode, final OutputStream opStream, final boolean bValidating) throws JAXBException {
		if(bValidating) {
			if(this.objValidator==null) {
				this.objValidator = this.objCtx.createValidator();
			}
			if(!this.objValidator.validate(xmlNode)) {
				return false;
			}
		}
		if(this.objMrsh==null) {
			this.objMrsh=this.objCtx.createMarshaller();
			this.objMrsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		}
		this.objMrsh.marshal(xmlNode, opStream);
		return true;
	}
	
	
	public String marshalXml(final Object xmlNode, final boolean bValidating) throws JAXBException {
		String strXml = null;
		StringWriter writer = new StringWriter();
		if(bValidating) {
			if(this.objValidator==null) {
				this.objValidator = this.objCtx.createValidator();
			}
			if(!this.objValidator.validate(xmlNode)) {
				return "";
			}
		}
		if(this.objMrsh==null) {
			this.objMrsh=this.objCtx.createMarshaller();
			this.objMrsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		}
		this.objMrsh.marshal(xmlNode, writer);
		return strXml;
	}
	
	public boolean validateXml(final Object xmlNode) throws JAXBException {
		if(this.objValidator==null) {
			this.objValidator=this.objCtx.createValidator();
		}
		return this.objValidator.validate(xmlNode);
	}
	
}
