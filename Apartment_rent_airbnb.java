package Nadlan_package;

public class Apartment_rent_airbnb extends Apartment_rent {
	public int rent;
	protected final int DURATION_IN_DAYS = 1;

	public Apartment_rent_airbnb(String address, int area_sqrt, int num_of_rooms, int rating, int rent) {
		super(address, area_sqrt, num_of_rooms, rating);
		this.rent = rent;
	}

	@Override
	public int getPrice() {
		return rent;
	}

	@Override
	public String getDuration() {
		return "day";
	}

	@Override
	public int getfinalDur() {
		return DURATION_IN_DAYS;
	}

}
