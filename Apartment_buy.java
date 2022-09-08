package Nadlan_package;

public class Apartment_buy extends Apartment {
	protected int buying_price;

	public Apartment_buy(String address, int area_sqrt, int num_of_rooms, int rating, int buying_price) {
		super(address, area_sqrt, num_of_rooms, rating);
		this.buying_price = buying_price;
	}

	@Override
	public int getPrice() {
		return buying_price;
	}

	@Override
	public String getDuration() {
		return "Full ownership";
	}
	
	@Override
	public double getAddFee() {
		return 5*(buying_price/100);
	}


}
