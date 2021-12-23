package by.epamtc.melnikov.xmltask.handler;

public enum DeviceXmlTag {
	
	DEVICES("devices"),
	CRITICAL("critical"),
	ID("id"),
	DEVICE("device"),
	NAME("name"),
	PRICE("price"),
	ORIGIN("origin"),
	PERIPHERALITY("peripherality"),
	CONSUMPTION("consumption"),
	GROUP("group"),
	PORT("port"),
	DEVICE_ADD_TS("device_add_ts"),
	TYPE("type");

	private String value;
	
	DeviceXmlTag(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
