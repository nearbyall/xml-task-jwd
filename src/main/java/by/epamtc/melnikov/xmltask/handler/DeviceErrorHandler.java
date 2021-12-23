package by.epamtc.melnikov.xmltask.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

public class DeviceErrorHandler implements ErrorHandler {
	
	private static Logger logger = LogManager.getLogger(DeviceErrorHandler.class);
	
	public void warning(SAXParseException e) {
		logger.warn(getLineColumnNumber(e) + "-" + e.getMessage());
	}

	public void error(SAXParseException e) {
		logger.error(getLineColumnNumber(e) + " - " + e.getMessage());
	}
	 
	public void fatalError(SAXParseException e) {
		logger.fatal(getLineColumnNumber(e) + " - " + e.getMessage());
	}
	
	private String getLineColumnNumber(SAXParseException e) {
		return e.getLineNumber() + " : " + e.getColumnNumber();
	}
	
}
