package by.epamtc.melnikov.xmltask.parser.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import by.epamtc.melnikov.xmltask.bean.Device;
import by.epamtc.melnikov.xmltask.handler.DeviceErrorHandler;
import by.epamtc.melnikov.xmltask.handler.DeviceHandler;
import by.epamtc.melnikov.xmltask.parser.AbstractDevicesBuilder;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Set;

public class DevicesSaxBuilder extends AbstractDevicesBuilder {
	
	private Set<Device> devices;
	private DeviceHandler handler = new DeviceHandler();
	private XMLReader reader;
	
	private final static Logger logger = LogManager.getLogger(DevicesSaxBuilder.class);
	
	public DevicesSaxBuilder() {
		// reader configuration
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = factory.newSAXParser();
			reader = saxParser.getXMLReader();
		} catch (ParserConfigurationException | SAXException e) {
			logger.error("DevicesSaxBuilder creation error: ", e);
		}
		reader.setErrorHandler(new DeviceErrorHandler());
		reader.setContentHandler(handler);
	}
	
	public Set<Device> getDevices() {
		return devices;
	}
	
	public void buildSetDevices(String filename) {
		try {
			reader.parse(filename);
		} catch (IOException | SAXException e) {
			logger.error("Sax parse error: ", e);
		}
		devices = handler.getDevices();
	}
	
}
