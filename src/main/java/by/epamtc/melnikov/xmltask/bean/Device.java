package by.epamtc.melnikov.xmltask.bean;

public class Device {

	private String critical;
	private String name;
	private String origin;
	private String addDate;
	private int price;
	private int id;
	private Type type;
	
	public Device() {
		type = new Type();
	}

	public Device(String critical, String name, String origin, int price, int id, Type type, String addDate) {
		this.critical = critical;
		this.name = name;
		this.origin = origin;
		this.addDate = addDate;
		this.price = price;
		this.id = id;
		this.type = type;
	}
	
	public String getAddDate() {
		return addDate;
	}

	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCritical() {
		return critical;
	}

	public void setCritical(String critical) {
		this.critical = critical;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [critical=" + critical + ", name=" + name + ", origin=" + origin + ", addDate=" + addDate
				+ ", price=" + price + ", id=" + id + ", type=" + type + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addDate == null) ? 0 : addDate.hashCode());
		result = prime * result + ((critical == null) ? 0 : critical.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result + price;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Device other = (Device) obj;
		if (addDate == null) {
			if (other.addDate != null)
				return false;
		} else if (!addDate.equals(other.addDate))
			return false;
		if (critical == null) {
			if (other.critical != null)
				return false;
		} else if (!critical.equals(other.critical))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		if (price != other.price)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public class Type {
		
		private String peripherality;
		private String group;
		private String port;
		private int consumption;
		
		public Type() {
		
		}
		
		public Type(String peripherality, String group, String port, int consumption) {
			this.peripherality = peripherality;
			this.group = group;
			this.port = port;
			this.consumption = consumption;
		}

		public String getPeripherality() {
			return peripherality;
		}

		public void setPeripherality(String peripherality) {
			this.peripherality = peripherality;
		}

		public String getGroup() {
			return group;
		}

		public void setGroup(String group) {
			this.group = group;
		}

		public String getPort() {
			return port;
		}

		public void setPort(String port) {
			this.port = port;
		}

		public int getConsumption() {
			return consumption;
		}

		public void setConsumption(int consumption) {
			this.consumption = consumption;
		}

		@Override
		public String toString() {
			return getClass().getSimpleName() + " [peripherality=" + peripherality + ", group=" + group + ", port=" + port + ", consumption="
					+ consumption + "]";
		}

	}
	
}
