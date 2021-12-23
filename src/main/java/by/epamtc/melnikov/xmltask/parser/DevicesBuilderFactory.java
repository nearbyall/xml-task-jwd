package by.epamtc.melnikov.xmltask.parser;

import by.epamtc.melnikov.xmltask.parser.impl.DevicesDomBuilder;
import by.epamtc.melnikov.xmltask.parser.impl.DevicesSaxBuilder;
import by.epamtc.melnikov.xmltask.parser.impl.DevicesStaxBuilder;

public class DevicesBuilderFactory {

	private enum TypeParser {
		SAX, STAX, DOM
	}
	
	private DevicesBuilderFactory() {
		
	}
	
	public static AbstractDevicesBuilder createDevicesBuilder(String typeParser) {
		
		TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
		switch (type) {
		case DOM:
			return new DevicesDomBuilder();
		case STAX:
			return new DevicesStaxBuilder();
		case SAX:
			return new DevicesSaxBuilder();
		default:
				throw new EnumConstantNotPresentException(type.getDeclaringClass(), type.name());//log
		}
		
	}
	
}
