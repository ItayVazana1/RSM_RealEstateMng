package Nadlan_package;

import java.util.ArrayList;

public class Apartment implements Cloneable{

	private long id_Generator() {
		return (long) ((Math.random() * (9999 - 1000)) + 1000);
	}

	protected long id;
	protected String address;
	protected int area_sqrt;
	protected int num_of_rooms;
	protected int rating;
	protected ArrayList<customer> Interested;
	public String type_name;
	protected double additional_costs;

	public Apartment(String address, int area_sqrt, int num_of_rooms, int rating) {
		super();
		this.id = id_Generator();
		this.address = address;
		this.area_sqrt = area_sqrt;
		this.num_of_rooms = num_of_rooms;
		this.rating = rating;
		Interested = new ArrayList<customer>();
		type_name = this.getClass().getSimpleName();
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getInterested() {
		String all_inter = "";
		for (int i = 0; i < Interested.size(); i++) {
			all_inter = all_inter + Interested.get(i).toString();
		}
		return all_inter;
	}
	
	public void setID(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getAddress() {
		return address;
	}

	public double getArea_sqrt() {
		return area_sqrt;
	}

	public int getNum_of_rooms() {
		return num_of_rooms;
	}

	public String getTName() {
		return type_name;
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
	
	public Apartment getClone() {
		try {
			return (Apartment) this.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public String toString() {
		String space_row = "******************************\n";
		String row1 = "ID: " + this.id + "\n";
		String row2 = "Type: " + this.getClass().getSimpleName() + "\n";
		String row3 = "Address: " + this.address + "\n";
		String row4 = "Area: " + this.area_sqrt + " sqrt meters\n";
		String row5 = "Rooms: " + this.num_of_rooms + " rooms\n";
		String row6 = "Rating: " + this.rating + "/10\n";
		String row7 = "Price: " + getPrice() + " ILS for " + getDuration() + " + Agency Fees\n";
		String row8 = "Num. of Intrested: " + this.Interested.size() + "\n";
		return space_row + row1 + row2 + row3 + row4 + row5 + row6 + row7 + row8 +  space_row;
	}
	
	public String Addtional_costs_show() {
		String space_row = "******************************\n";
		String row1 = "ID: " + this.id + "\n";
		String row2 = "Type: " + this.getClass().getSimpleName() + "\n";
		String row3 = "Address: " + this.address + "\n";
		String row4 = "Price: " + getPrice() + " ILS for " + getDuration() + "\n";
		String row5 = "Agency Fees: " + String.valueOf(getAddFee()) + " ILS\n";
		return space_row + row1 + row2 + row3 + row4 + row5 + space_row;
	}
	
	public double getAddFee() {
		return 0;
	}
	

	@Override
	public boolean equals(Object o) {
		Apartment new_apt = (Apartment) o;
		if (this.address.compareTo(new_apt.address) == 0) {
			return true;
		}
		return false;
	}

}
