package Nadlan_package;

public class Apartment_rent_regular extends Apartment_rent {
	public int rent;
	protected final int DURATION_IN_DAYS = 30;

	public Apartment_rent_regular(String address, int area_sqrt, int num_of_rooms, int rating, int rent) {
		super(address, area_sqrt, num_of_rooms, rating);
		this.rent = rent;
	}

	@Override
	public int getPrice() {
		return rent;
	}

	@Override
	public String getDuration() {
		return "month";
	}

	@Override
	public int getfinalDur() {
		return DURATION_IN_DAYS;
	}
}