package by.epamtc.melnikov.xmltask.parser;

import java.util.HashSet;
import java.util.Set;

import by.epamtc.melnikov.xmltask.bean.Device;

public abstract class AbstractDevicesBuilder {

	protected Set<Device> devices;
	
	public AbstractDevicesBuilder() {
		devices = new HashSet<Device>();
	}
	
	public AbstractDevicesBuilder(Set<Device> devices) {
		this.devices = devices;
	}
	
	public Set<Device> getDevices() {
		return devices;
	}
	
	public abstract void buildSetDevices(String filename);
	
}
