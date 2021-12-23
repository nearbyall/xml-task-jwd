package by.epamtc.melnikov.xmltask.parser.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtc.melnikov.xmltask.bean.Device;
import by.epamtc.melnikov.xmltask.handler.DeviceXmlTag;
import by.epamtc.melnikov.xmltask.parser.AbstractDevicesBuilder;

public class DevicesStaxBuilder extends AbstractDevicesBuilder {
	
	private Set<Device> devices;
	private XMLInputFactory inputFactory;
	
	private final static Logger logger = LogManager.getLogger(DevicesStaxBuilder.class);
	
	public DevicesStaxBuilder() {
		inputFactory = XMLInputFactory.newInstance();
		devices = new HashSet<Device>();
	}
	
	public Set<Device> getDevices() {
		return devices;
	}
	
	public void buildSetDevices(String filename) {
		XMLStreamReader reader;
		String name;
		try(FileInputStream inputStream = new FileInputStream(new File(filename))) {
			reader = inputFactory.createXMLStreamReader(inputStream);
			// StAX parsing
			while (reader.hasNext()) {
				int type = reader.next();
				if (type == XMLStreamConstants.START_ELEMENT) {
					name = reader.getLocalName();
					if (name.equals(DeviceXmlTag.DEVICE.getValue())) {
						Device student = buildDevice(reader);
						devices.add(student);
					}
				}
			}
		} catch (XMLStreamException | FileNotFoundException e) {
			logger.error("Stax parse error: ", e);
		} catch (IOException e) {
			logger.error("Stax parse error: ", e);
		}
	}
	 
	private Device buildDevice(XMLStreamReader reader) throws XMLStreamException {
		Device device = new Device();
		device.setCritical(reader.getAttributeValue(null, DeviceXmlTag.CRITICAL.getValue()));
		String name;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
				case XMLStreamConstants.START_ELEMENT:
					name = reader.getLocalName();
					switch (DeviceXmlTag.valueOf(name.toUpperCase())) {
						case NAME:
							device.setName(getXMLText(reader));
							break;
						case PRICE:
							device.setPrice(Integer.parseInt(getXMLText(reader)));
							break;
						case ID:
							device.setId(Integer.parseInt(getXMLText(reader)));
						case DEVICE_ADD_TS:
							device.setAddDate(getXMLText(reader));
							break;
						case TYPE:
							device.setType(getXMLAddress(reader));
							break;
						default:
							break;
					}
					break;
				case XMLStreamConstants.END_ELEMENT:
					name = reader.getLocalName();
					if (DeviceXmlTag.valueOf(name.toUpperCase()) == DeviceXmlTag.DEVICE) {
						return device;
					}
					break;
			}
		}
		throw new XMLStreamException("Unknown element in tag <student>");
	}
	
	private Device.Type getXMLAddress(XMLStreamReader reader) throws XMLStreamException {
			Device.Type devicetype = new Device().new Type();
			int type;
			String name;
	 		while (reader.hasNext()) {
	 			type = reader.next();
	 			switch (type) {
	 				case XMLStreamConstants.START_ELEMENT:
	 					name = reader.getLocalName();
	 					switch (DeviceXmlTag.valueOf(name.toUpperCase())) {
	 						case PERIPHERALITY:
	 							devicetype.setPeripherality(getXMLText(reader));
	 							break;
	 						case CONSUMPTION:
	 							devicetype.setConsumption(Integer.parseInt(getXMLText(reader)));
	 							break;
	 						case GROUP:
	 							devicetype.setGroup(getXMLText(reader));
	 							break;
	 						case PORT:
	 							devicetype.setPort(getXMLText(reader));
	 							break;
	 						default:
	 							break;
	 					}
	 					break;
	 				case XMLStreamConstants.END_ELEMENT:
	 					name = reader.getLocalName();
	 					if (DeviceXmlTag.valueOf(name.toUpperCase()) == DeviceXmlTag.TYPE) {
	 						return devicetype;
	 					}
	 			}
	 		}
	 		throw new XMLStreamException("Unknown element in tag <type>");
		}
	 
	 private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
		 String text = null;
		 if (reader.hasNext()) {
			 reader.next();
			 text = reader.getText();
		 }
		 return text;
	 }
}
