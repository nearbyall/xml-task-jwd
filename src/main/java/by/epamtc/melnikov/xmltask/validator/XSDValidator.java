package by.epamtc.melnikov.xmltask.validator;

import java.io.*;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import by.epamtc.melnikov.xmltask.handler.DeviceErrorHandler;

public class XSDValidator {
	
	private final Logger logger = LogManager.getLogger(XSDValidator.class);
	
	private XSDValidator() {
		
	}
	
	private static class XSDValidatorHolder {
		private final static XSDValidator instance = new XSDValidator();
	}
	
	public static XSDValidator getInstance() {
		return XSDValidatorHolder.instance;
	}
	
	public boolean validate(String fileName, String schemaName) {
		
		String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		SchemaFactory factory = SchemaFactory.newInstance(language);
		File schemaLocation = new File(schemaName);
		try {
			Schema schema = factory.newSchema(schemaLocation);
			Validator validator = schema.newValidator();
			Source source = new StreamSource(fileName);
			validator.setErrorHandler(new DeviceErrorHandler());
			validator.validate(source);
		} catch (SAXException | IOException e) {
			logger.error("Validation error", e);
			return false;
		}
		return true;
		
	}

}
