package Nadlan_package;

public class Apartment_rent extends Apartment {
	protected final int DURATION_IN_DAYS;

	public Apartment_rent(String address, int area_sqrt, int num_of_rooms, int rating) {
		super(address, area_sqrt, num_of_rooms, rating);
		this.DURATION_IN_DAYS = 0;
	}

	public int getPrice() {
		return 0;
	}

	public String getDuration() {
		return "";
	}

	public int getfinalDur() {
		return 0;
	}
	
	@Override
	public double getAddFee() {
		if (this.type_name.contains("airbnb")==false) {
			return 4000;
		}else {
			return 0;
		}
	}

}
