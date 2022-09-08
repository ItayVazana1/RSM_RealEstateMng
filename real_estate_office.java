package Nadlan_package;

import java.util.ArrayList;

public class real_estate_office {

	public ArrayList<Apartment> all_apt;
	public ArrayList<customer> all_cust;

	public real_estate_office() {
		all_apt = new ArrayList<Apartment>();
		all_cust = new ArrayList<customer>();
	}

	public boolean insert_new_cust(customer nc) {
		for (int i = 0; i < all_cust.size(); i++) {
			if (nc.c_phone.equals(all_cust.get(i).c_phone) == true) {
				return true;
			}
		}
		all_cust.add(nc);
		return false;
	}

	public void insert_cost_to_list(String apt_adr, customer nc) {
		for (int i = 0; i < all_apt.size(); i++) {
			if (apt_adr.equals(all_apt.get(i).address) == true) {
				for (int k = 0; k < all_apt.get(i).Interested.size(); k++) {
					if (nc.c_phone.equals(all_apt.get(i).Interested.get(k).c_phone) == true) {
						//System.out.println("It seems that this customer already exists in the list!\n");
						return;
					}
				}
				all_apt.get(i).Interested.add(nc);
				return;
			}
		}
	}

	public void insert_apt(Apartment new_apt) {
		all_apt.add(new_apt);
	}

	public void show_all_apt() {
		for (int i = 0; i < all_apt.size(); i++) {
			System.out.println("Apartment " + (i + 1));
			System.out.println(all_apt.get(i).toString());
		}
	}

	private void only_from_type(String type) {
		System.out.println("****** " + type + " - Apartments List: *******\n");
		ArrayList<Apartment> from_type;
		from_type = new ArrayList<Apartment>();
		for (int i = 0; i < all_apt.size(); i++) {
			if (all_apt.get(i).getTName().contentEquals(type)) {
				from_type.add(all_apt.get(i));
			}
		}
		for (int k = 0; k < from_type.size(); k++) {
			System.out.println(from_type.get(k).toString());
		}
	}

	public boolean find_apt_by_adr(String address) {
		for (int i = 0; i < all_apt.size(); i++) {
			if (address.equals(all_apt.get(i).address) == true) {
				return true;
			}
		}
		return false;
	}

	public int calc_rent_for_period(int dur, String adr) {
		int res = 0;
		for (int i = 0; i < all_apt.size(); i++) {
			if (adr.equals(all_apt.get(i).address) == true) {
				Apartment r_apt = all_apt.get(i);
				res = r_apt.getPrice() * dur;
			}
		}
		return res;
	}

	public customer search_by_phone(String c_ph) {
		for (int i = 0; i < all_cust.size(); i++) {
			if (c_ph.equals(all_cust.get(i).c_phone) == true) {
				return all_cust.get(i);
			}
		}
		return null;
	}

	private int most_exp_assist(int price, Apartment r_apt) {
		if (r_apt.getfinalDur() == 30) {
			price = price / 30;
		}
		return price;
	}

	public int find_most_exp_for_rent(int req_dur) {
		ArrayList<Apartment> rent_type;
		rent_type = new ArrayList<Apartment>();
		for (int i = 0; i < all_apt.size(); i++) {
			if (all_apt.get(i).type_name.contains("rent") == true) {
				rent_type.add(all_apt.get(i));
			}
		}
		int apt_indicator = 0;
		int most_ex = calc_rent_for_period(req_dur, rent_type.get(0).address);
		most_ex = most_exp_assist(most_ex, rent_type.get(0));
		for (int k = 0; k < rent_type.size(); k++) {
			int check_price = calc_rent_for_period(req_dur, rent_type.get(k).address);
			check_price = most_exp_assist(check_price, rent_type.get(k));
			if (most_ex < check_price) {
				most_ex = check_price;
				apt_indicator = k;
			}
		}
		System.out.println("Address of the Aparetment - " + rent_type.get(apt_indicator).address);
		return most_ex;
	}

	public void show_type_apt(int index_type) {
		switch (index_type) {
		case 1:
			only_from_type("Apartment_buy");
			break;
		case 2:
			only_from_type("Apartment_rent_regular");
			break;
		case 3:
			only_from_type("Apartment_rent_airbnb");
			break;
		default:
			System.out.println("The apartment type does not exist in the system, contact the system administrator!");
		}
	}

	public void show_all_Intre_for_apt(String apt_adr) {
		for (int i = 0; i < all_apt.size(); i++) {
			if (apt_adr.equals(all_apt.get(i).address) == true) {
				for (int k = 0; k < all_apt.get(i).Interested.size(); k++) {
					System.out.println(all_apt.get(i).Interested.get(k).toString());
				}
			}
		}
	}

	public void show_Intrested_sorted(String apt_adr) {
		for (int i = 0; i < all_apt.size(); i++) {
			if (apt_adr.equals(all_apt.get(i).address) == true) {
				String[] s_customer = new String[all_apt.get(i).Interested.size()];
				String[] s_customer_ph = new String[all_apt.get(i).Interested.size()];
				for (int r = 0; r < all_apt.get(i).Interested.size(); r++) {
					s_customer[r] = all_apt.get(i).Interested.get(r).c_name;
					s_customer_ph[r] = all_apt.get(i).Interested.get(r).c_phone;
					s_customer[r] = s_customer[r].toLowerCase();
				}
				for (int k = 0; k < s_customer.length; k++) {
					String temp_name;
					String temp_phone;
					for (int n = 0; n < s_customer.length; n++) {
						for (int j = n + 1; j < s_customer.length; j++) {
							if (s_customer[n].compareTo(s_customer[j]) > 0) {
								temp_name = s_customer[n];
								temp_phone = s_customer_ph[n];
								s_customer[n] = s_customer[j];
								s_customer_ph[n] = s_customer_ph[j];
								s_customer[j] = temp_name;
								s_customer_ph[j] = temp_phone;
							}
						}
					}
				}

				for (int p = 0; p < all_apt.get(i).Interested.size(); p++) {
					for (int t = 0; t < s_customer.length; t++) {
						if (all_apt.get(i).Interested.get(p).c_name.toLowerCase().equals(s_customer[t])) {
							System.out.println(s_customer[p] + " - " + s_customer_ph[p]);
						}
					}
				}
			}
		}
	}
	
	public void Create_Clone(String apt_adr) throws CloneNotSupportedException{
		for (int i = 0; i < all_apt.size(); i++) {
			if (apt_adr.equals(all_apt.get(i).address) == true) {
				Apartment original = (Apartment) all_apt.get(i);
				Apartment apt_copy = original.getClone();
				int copyID = (int) (Math.random() * (9999 - 1000) + 1000);
				apt_copy.address = apt_copy.address + "(copyID-" + String.valueOf(copyID)+")" ;
				insert_apt(apt_copy);
				System.out.println(apt_copy.toString());
				System.out.println("\n");
				break;
			}
		}
	}
	
	public boolean check_if_apt_exsist(String req_apt) {
		if (find_apt_by_adr(req_apt) == false) {
			System.out.println("It seems that this Apartment does not exists!\n");
			return false;
		}
		return true;
	}

	public void show_apts_with_AFees() {
		for (int i = 0; i < all_apt.size(); i++) {
			System.out.println("Apartment " + (i + 1));
			System.out.println(all_apt.get(i).Addtional_costs_show());
		}
	}

	public void delete_apt_by_address(String adr) {
		for (int i = 0; i < all_apt.size(); i++) {
			if (adr.equals(all_apt.get(i).address) == true) {
					all_apt.remove(i);
			}
		}
	}
}