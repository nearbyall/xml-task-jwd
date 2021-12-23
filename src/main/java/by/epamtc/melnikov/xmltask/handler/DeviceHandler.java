package by.epamtc.melnikov.xmltask.handler;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import by.epamtc.melnikov.xmltask.bean.Device;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class DeviceHandler extends DefaultHandler {
	
	private Set<Device> devices;
	private Device current;
	private DeviceXmlTag currentXmlTag;
	private EnumSet<DeviceXmlTag> withText;
	 
	private static final String ELEMENT_DEVICE = "device";
	 
	public DeviceHandler() {
		devices = new HashSet<Device>();
		withText = EnumSet.range(DeviceXmlTag.NAME, DeviceXmlTag.DEVICE_ADD_TS);
	}
	 
	public Set<Device> getDevices() {
		return devices;
	}
	 
	public void startElement(String uri, String localName, String qName, Attributes attrs) {
		if (ELEMENT_DEVICE.equals(qName)) {
			current = new Device();
			if (attrs.getLength() == 2) { // warning!!!!
				current.setCritical(attrs.getValue(1));
				current.setId(Integer.parseInt(attrs.getValue(0)));
			}
		} else {
			DeviceXmlTag temp = DeviceXmlTag.valueOf(qName.toUpperCase());
			if (withText.contains(temp)) {
				currentXmlTag = temp;
			}
		}
	}
	 
	public void endElement(String uri, String localName, String qName) {
		if (ELEMENT_DEVICE.equals(qName)) {
			devices.add(current);
		}
	}
	 
	public void characters(char[] ch, int start, int length) {
		String data = new String(ch, start, length).strip();
		if (currentXmlTag!= null) {
			switch (currentXmlTag) {
				case NAME: 
					current.setName(data);
					break;
				case ORIGIN: 
					current.setOrigin(data);
					break;
			 	case PRICE:
			 		current.setPrice(Integer.parseInt(data));
			 		break;
			 	case CONSUMPTION:
			 		current.getType().setConsumption(Integer.parseInt(data));
			 		break;
			 	case GROUP:
			 		current.getType().setGroup(data);
			 		break;
			 	case PORT:
			 		current.getType().setPort(data);
			 		break;
			 	case PERIPHERALITY:
			 		current.getType().setPeripherality(data);
			 		break;
			 	case DEVICE_ADD_TS:
			 		current.setAddDate(data);
			 		break;
			 	default:
			 		throw new EnumConstantNotPresentException(currentXmlTag.getDeclaringClass(), currentXmlTag.name());
			}
		}
		currentXmlTag = null;
	}
	
}
