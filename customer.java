package Nadlan_package;

public class customer {
	protected String c_name;
	protected String c_phone;

	public customer(String c_name, String c_phone) {
		super();
		this.c_name = c_name;
		this.c_phone = c_phone;
	}

	public String getC_name() {
		return c_name;
	}

	public String getC_phone() {
		return c_phone;
	}

	@Override
	public String toString() {
		return c_name + " - " + c_phone;
	}

	@Override
	public boolean equals(Object o) {
		customer new_cst = (customer) o;
		if (this.c_phone.compareTo(new_cst.c_phone) == 0) {
			return true;
		}
		return false;
	}
}
