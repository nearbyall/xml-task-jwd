package by.epamtc.melnikov.xmltask.parser.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import by.epamtc.melnikov.xmltask.bean.Device;
import by.epamtc.melnikov.xmltask.parser.AbstractDevicesBuilder;

public class DevicesDomBuilder extends AbstractDevicesBuilder {

	private Set<Device> devices;
	private DocumentBuilder docBuilder;
	
	private final static Logger logger = LogManager.getLogger(DevicesDomBuilder.class);
	
	public DevicesDomBuilder() {
		devices = new HashSet<Device>();
		// configuration
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			logger.error("DevicesDomBuilder creation error: ", e);
		}
	}
	
	public Set<Device> getDevices() {
		return devices;
	}
	
	public void buildSetDevices(String filename) {
		Document doc;
		try {
			doc = docBuilder.parse(filename);
			Element root = doc.getDocumentElement();
			// getting a list of <student> child elements
			NodeList devicesList = root.getElementsByTagName("device");
			for (int i = 0; i < devicesList.getLength(); i++) {
				Element deviceElement = (Element) devicesList.item(i);
				Device device = buildDevice(deviceElement);
				devices.add(device);
			}
		} catch (IOException | SAXException e) {
			logger.error("Dom parse error: ", e);
		}
	}
	
	private Device buildDevice(Element deviceElement) {
		Device device = new Device();
		// add null check
		device.setCritical(deviceElement.getAttribute("critical"));
		device.setId(Integer.parseInt(deviceElement.getAttribute("id")));
		device.setName(getElementTextContent(deviceElement, "name"));
		device.setOrigin(getElementTextContent(deviceElement, "origin"));
		device.setAddDate(getElementTextContent(deviceElement, "device_add_ts"));
		Integer price = Integer.parseInt(getElementTextContent(deviceElement, "price"));
		device.setPrice(price);
		Device.Type type = device.getType();
		// init an type object
		Element typeElement =
				(Element) deviceElement.getElementsByTagName("type").item(0);
	 
		type.setPeripherality(getElementTextContent(typeElement, "peripherality"));
		Integer consumption = Integer.parseInt(getElementTextContent(typeElement, "consumption"));
		type.setConsumption(consumption);
		type.setGroup(getElementTextContent(typeElement, "group"));
		type.setPort(getElementTextContent(typeElement, "port"));

		return device;
	}
	
	// get the text content of the tag
	private static String getElementTextContent(Element element, String elementName) {
		NodeList nList = element.getElementsByTagName(elementName);
		Node node = nList.item(0);
		String text = node.getTextContent();
		return text;
	}
	
	
}
