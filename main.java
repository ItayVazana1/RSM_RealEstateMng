package Nadlan_package;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class main {

	public static Scanner in = new Scanner(System.in);

	public static void writeFile(String f_name, ArrayList<String> data) {
		try {
			FileWriter fw = new FileWriter(f_name);
			PrintWriter pw = new PrintWriter(fw);
			for (int i = 0; i < data.size(); i++) {
				pw.println(data.get(i));
			}
			pw.close();
		} catch (IOException e) {
			System.out.println("Error - Cannot write to file : " + f_name);
		}
	}

	public static void readFile(String f_name, ArrayList<String> data) {
		try {
			FileReader fr = new FileReader(f_name);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				data.add(line);
			}
		} catch (IOException e) {
			System.out.println("Error - Cannot read from file : " + f_name);
		}
	}

	public static void savingData_apt(ArrayList<Apartment> Apartments, ArrayList<String> data) {
		real_estate_office myOffice = new real_estate_office();
		myOffice.all_apt = Apartments;
		data.clear();
		for (int i = 0; i < Apartments.size(); i++) {
			String type = Apartments.get(i).type_name;
			String id = Long.toString(Apartments.get(i).id);
			String address = Apartments.get(i).address;
			String area = Integer.toString(Apartments.get(i).area_sqrt);
			String rooms = Integer.toString(Apartments.get(i).num_of_rooms);
			String rate = Integer.toString(Apartments.get(i).rating);
			String price = Integer.toString(Apartments.get(i).getPrice());
			String line = type + "," + id + "," + address + "," + area + "," + rooms + "," + rate + "," + price;
			data.add(line);
		}
	}

	public static void savingData_cust(ArrayList<customer> Customers, ArrayList<String> data) {
		real_estate_office myOffice = new real_estate_office();
		myOffice.all_cust = Customers;
		data.clear();
		for (int i = 0; i < Customers.size(); i++) {
			String name = Customers.get(i).c_name;
			String phone = Customers.get(i).c_phone;
			String line = name + "," + phone;
			data.add(line);
		}
	}

	public static void savingData_intres(ArrayList<Apartment> Apartments, ArrayList<customer> Customers,ArrayList<String> Intrested_data) {
		real_estate_office myOffice = new real_estate_office();
		myOffice.all_apt = Apartments;
		myOffice.all_cust = Customers;
		Intrested_data.clear();
		for (int i = 0; i < Apartments.size(); i++) {
			String intres_list = ",";
			for (int k = 0; k < Customers.size(); k++) {
				for (int s = 0; s < Apartments.get(i).Interested.size(); s++) {
					if (Customers.get(k).c_phone == Apartments.get(i).Interested.get(s).c_phone) {
						intres_list = intres_list + Customers.get(k).c_phone + ",";
					}
				}
			}
			Intrested_data.add(Apartments.get(i).address + intres_list);
		}
	}

	public static void createApts_fromFile(ArrayList<Apartment> Apartments, ArrayList<String> data) {
		real_estate_office myOffice = new real_estate_office();
		for (int i = 0; i < data.size(); i++) {
			String[] temp = new String[6];
			temp = data.get(i).split(",");
			String type_apt = temp[0];
			switch (type_apt) {
			case "Apartment_buy":
				Apartment b_apt = new Apartment_buy(temp[2], Integer.valueOf(temp[3]), Integer.valueOf(temp[4]),
						Integer.valueOf(temp[5]), Integer.valueOf(temp[6]));
				b_apt.setID(Long.valueOf(temp[1]));
				Apartments.add(b_apt);
				myOffice.insert_apt(b_apt);
				break;
			case "Apartment_rent_regular":
				Apartment r_apt = new Apartment_rent_regular(temp[2], Integer.valueOf(temp[3]),
						Integer.valueOf(temp[4]), Integer.valueOf(temp[5]), Integer.valueOf(temp[6]));
				r_apt.setID(Long.valueOf(temp[1]));
				Apartments.add(r_apt);
				myOffice.insert_apt(r_apt);
				break;
			case "Apartment_rent_airbnb":
				Apartment ab_apt = new Apartment_rent_airbnb(temp[2], Integer.valueOf(temp[3]),
						Integer.valueOf(temp[4]), Integer.valueOf(temp[5]), Integer.valueOf(temp[6]));
				ab_apt.setID(Long.valueOf(temp[1]));
				Apartments.add(ab_apt);
				myOffice.insert_apt(ab_apt);
				break;
			}
		}
	}

	public static void createUsers_fromFile(ArrayList<customer> Customers, ArrayList<String> data) {
		real_estate_office myOffice = new real_estate_office();
		for (int i = 0; i < data.size(); i++) {
			String[] temp = new String[2];
			temp = data.get(i).split(",");
			customer cs = new customer(temp[0], temp[1]);
			myOffice.insert_new_cust(cs);
			Customers.add(cs);
		}
	}

	public static void setUsersAsIntrested_fromFile(ArrayList<Apartment> Apartments, ArrayList<customer> Customers,
			ArrayList<String> Intrested_data) {
		real_estate_office myOffice = new real_estate_office();
		myOffice.all_apt = Apartments;
		myOffice.all_cust = Customers;
		for (int i = 0; i < Intrested_data.size(); i++) {
			String[] temp = new String[Customers.size() + 1];
			temp = Intrested_data.get(i).split(",");
			String apt_address = temp[0];
			int run = 1;
			for (int c = 0; c < Apartments.size(); c++) {
				if (apt_address.contentEquals(Apartments.get(i).address) == true) {
					for (int s = 1; s < temp.length; s++) {
						for (int d = 0; d < Customers.size(); d++) {
							if (Customers.get(d).c_phone.contentEquals(temp[s]) == true) {
								myOffice.insert_cost_to_list(apt_address, Customers.get(d));
							}
						}
					}
				}
			}
		}
	}


	
	public static void main(String[] args) throws IOException, CloneNotSupportedException {


		String apt_DB = "apt_DB.bin";
		String cust_DB = "cust_DB.bin";
		String intre_DB = "intrested_DB.bin";
		real_estate_office myOffice = new real_estate_office();

		ArrayList<String> Apartments_data = new ArrayList<String>();
		ArrayList<Apartment> Apartments = new ArrayList<Apartment>();
		ArrayList<String> customers_data = new ArrayList<String>();
		ArrayList<customer> customers = new ArrayList<customer>();
		ArrayList<String> Intrested_data = new ArrayList<String>();

		readFile(apt_DB, Apartments_data);
		readFile(cust_DB, customers_data);
		readFile(intre_DB, Intrested_data);
		createApts_fromFile(Apartments, Apartments_data);
		createUsers_fromFile(customers, customers_data);
		setUsersAsIntrested_fromFile(Apartments, customers, Intrested_data);
		myOffice.all_apt = Apartments;
		myOffice.all_cust = customers;

		// ***** Arrays for dialogues :

		String[] dialogMain = { "*********** Real estate agency management v1.0 ***********\n", "Main Menu:\n",
				"1. Adding an apartment to the database\n"
				+ "2. Adding a customer to the list of those interested in the apartment\n"
				+ "3. Show all the apartments\n"
				+ "4. Showing all the apartments of a certain type\n"
				+ "5. Rental calculator for the rental period\n"
				+ "6. The most expensive apartment for the rental period\n"
				+ "7. Displaying all those interested in the apartment\n"
				+ "8. Displaying all those interested in the apartment in alphabetical order\n"
				+ "9. Create a Clone of Apartment\n"
				+ "10. Additional payments for all apartments\n"
				+ "11. Remove an apartment from the database\n"
				+ "12. Exit\n",
				"Enter the number of the requested action:\n" };
		String[] dialogNewSApt = { "Area in square meters:", "Enter the number of rooms in the apartment:",
				"Enter the rating of the apartment (1-10):", "Enter the Price:" };
		String[] dialogNewRApt = { "Area in square meters:", "Enter the number of rooms in the apartment:",
				"Enter the rating of the apartment (1-10):",
				"Enter the apartment rental for a minimum period of time" };
		String[] dialogAddClitoList = { "Enter the apartment address:", "Enter the Client's Phone num(only digits):",
				"Is the customer new(0) or existing(1) in the database?", "Enter the Client's name:" };
		String end_op = "Back to the main menu...\n";
		String invalid_input = "Invalid value entered!";

		// ***** Defining variables :

		boolean vld_flg = true;
		boolean main_f = true; // Main menu flag
		int op_num = 0; // an Integer that hold the nubmer of requested action from main menu
		String[] param = new String[5]; // array that hold the parameters for new Apartment
		int type_num; // an Integer that hold the number that represents the requested type
		String req_adr; // a String that hold the requested address
		String pho_c; // a String that hold the requested customer's phone num
		String name_c; // a String that hold the requested customer's name
		int client_status;// an Integer that hold the answer from the user about client new or old from
							// user
		String time_unit = ""; // a String that hold the requested time unit for calculate the rent in String
		int duration; // an Integer that hold the time of rent period in days
		int rent_type; // an Integer that hold the Digit that represents time unit for calculate the
						// rent
		int res_for_most_expensive; // an Integer that hold the most expensive option for specific period for rent

		// ***** The beginning of the program *****//
		System.out.println(dialogMain[0]);
		while (main_f) {
			vld_flg=true;
			for (int i = 1; i < dialogMain.length; i++) {
				System.out.println(dialogMain[i]);
			}
			
			do {
				try {
					op_num = Integer.parseInt(in.nextLine());
					vld_flg=false;
				}catch (NumberFormatException e) {
					System.out.println("Error - invalid type of input , should ne an Integer!");
				}catch (Exception e) {
					System.out.println("Try Again:");
					in.next();
				}
			}while(vld_flg);

			
			switch (op_num) {

			case 1: // *********************** action 1 ----> Adding an apartment to the DB
				int k = 0;
				int rate_vld=1;
				type_num = 0;
				System.out.println("Enter a number to indicate type (1-Sale 2-Rental 3-AirBnb):");
				vld_flg = true;
				do {
					try {
						type_num = Integer.parseInt(in.nextLine());
						vld_flg=false;
					}catch (NumberFormatException e) {
						System.out.println("Error - invalid type of input , should ne an Integer!");
					}catch (Exception e) {
						System.out.println("Try Again:");
						in.next();
					}
				}while(vld_flg);
				System.out.println("Enter the apartment address:");
				param[0] = in.nextLine();
				if (myOffice.find_apt_by_adr(param[0]) == true) {
					System.out.println("It seems that this apartment already exists in the database!\n");
					break;
				}
				if (type_num == 1) {
					for (int i = 1; i < param.length; i++) {
						System.out.println(dialogNewSApt[k]);
						k++;
						param[i] = in.nextLine();
					}
					try {
						rate_vld = Integer.parseInt(param[3]);
						if(rate_vld>10 || rate_vld<1) {
							System.out.println("Error - Rating can be only in range of 1-10! - Return to Main Menu..\n"); //issue!
							break;
						}
					}catch (Exception e) {
						System.out.println("Error - invalid type of input for some fields,return to main menu!\n"); //issue!
						vld_flg=false;
						break;
					}
						vld_flg=true;
						do {
							try {
								Apartment new_apt_s = new Apartment_buy(param[0], Integer.parseInt(param[1]),
										Integer.parseInt(param[2]), Integer.parseInt(param[3]), Integer.parseInt(param[4]));
								myOffice.insert_apt(new_apt_s);
								System.out.println(end_op);
								vld_flg=false;
								break;
							}catch (Exception e) {
								System.out.println("Error - invalid type of input for some fields,return to main menu!"); //issue!
								vld_flg=false;
							}
						}while(vld_flg);
						break;
						
					}else if (type_num == 3 || type_num == 2) {
					for (int i = 1; i < param.length; i++) {
						System.out.println(dialogNewRApt[k]);
						k++;
						param[i] = in.nextLine();
					}
					switch (type_num) {
					case 2:
						try {
							rate_vld = Integer.parseInt(param[3]);
							if(rate_vld>10 || rate_vld<1) {
								System.out.println("Error - Rating can be only in range of 1-10! - Return to Main Menu..\n"); //issue!
								break;
							}
						}catch (Exception e) {
							System.out.println("Error - invalid type of input for some fields,return to main menu!\n"); //issue!
							vld_flg=false;
							break;
						}
							vld_flg=true;
							do {
								try {
									Apartment new_apt_r = new Apartment_rent_regular(param[0], Integer.parseInt(param[1]),
											Integer.parseInt(param[2]), Integer.parseInt(param[3]), Integer.parseInt(param[4]));
									myOffice.insert_apt(new_apt_r);
									System.out.println(end_op);
									vld_flg=false;
									
								}catch (Exception e) {
									System.out.println("Error - invalid type of input for some fields,return to main menu!\n"); //issue!
									vld_flg=false;
									
								}
							}while(vld_flg);
							break;

					case 3:
						try {
							rate_vld = Integer.parseInt(param[3]);
							if(rate_vld>10 || rate_vld<1) {
								System.out.println("Error - Rating can be only in range of 1-10! - Return to Main Menu..\n"); //issue!
								break;
							}
						}catch (Exception e) {
							System.out.println("Error - invalid type of input for some fields,return to main menu!\n"); //issue!
							vld_flg=false;
							break;
						}
							vld_flg=true;
							do {
								try {
									Apartment new_apt_a = new Apartment_rent_airbnb(param[0], Integer.parseInt(param[1]),
											Integer.parseInt(param[2]), Integer.parseInt(param[3]), Integer.parseInt(param[4]));
									myOffice.insert_apt(new_apt_a);
									System.out.println(end_op);
									vld_flg=false;
									break;
								}catch (Exception e) {
									System.out.println("Error - invalid type of input for some fields,return to main menu!\n"); //issue!
									vld_flg=false;
								}
							}while(vld_flg);
						break;
						}
					}
				
				System.out.println();
				break;
			case 2: // *********************** action 2 ----> Adding a customer to the list of those
					// interested in the apartment
				System.out.println(dialogAddClitoList[0]);
				req_adr = in.nextLine();
				if (myOffice.check_if_apt_exsist(req_adr) == false) {
					break;
				}
				if (myOffice.find_apt_by_adr(req_adr) == true) {
					System.out.println(dialogAddClitoList[1]);
					pho_c = in.nextLine();
					System.out.println(dialogAddClitoList[3]);
					name_c = in.nextLine();
					System.out.println(dialogAddClitoList[2]);
					client_status=0;
					vld_flg=true;
					do {
						try {
							client_status = Integer.parseInt(in.nextLine());
							vld_flg=false;
						}catch (NumberFormatException e) {
							System.out.println("Error - invalid type of input , should ne an Integer!");
						}catch (Exception e) {
							System.out.println("Try Again:");
							in.next();
						}
					}while(vld_flg);
					switch (client_status) {
					case 0:
						customer nc = new customer(name_c, pho_c); // new customer
						if (myOffice.insert_new_cust(nc) == true) {
							System.out.println("It seems that this customer already exists!\n");
							System.out.println(end_op);
							break;
						} else {
							myOffice.insert_cost_to_list(req_adr, nc);
							System.out.println(end_op);
							break;
						}
					case 1:
						customer oc = myOffice.search_by_phone(pho_c); // customer already exists
						if (oc == null) {
							System.out.println("It seems that this customer is not exists!\n");
							break;
						} else {
							myOffice.insert_cost_to_list(req_adr, oc);
							System.out.println(end_op);
							break;
						}
					default:
						System.out.println(invalid_input);
						System.out.println(end_op);
					}
				}
				break;

			case 3: // *********************** action 3 ----> Show all the apartments
				myOffice.show_all_apt();
				System.out.println(end_op);

				break;

			case 4: // *********************** action 4 ----> Showing all the apartments of a
					// certain type
				System.out.println("Enter a number to indicate type (1-Sale 2-Rental 3-AirBnb):");
				type_num =0;
				vld_flg = true;
				do {
					try {
						type_num = Integer.parseInt(in.nextLine());
						vld_flg=false;
					}catch (NumberFormatException e) {
						System.out.println("Error - invalid type of input , should ne an Integer!");
					}catch (Exception e) {
						System.out.println("Try Again:");
						in.next();
					}
				}while(vld_flg);
				if (type_num != 1 && type_num != 2 && type_num != 3) {
					System.out.println(invalid_input);
					System.out.println(end_op);
					break;
				}
				myOffice.show_type_apt(type_num);
				System.out.println(end_op);
				break;

			case 5: // *********************** action 5 ----> Rental calculator for the rental
					// period
				duration = 0;
				System.out.println("Interested in daily(0) or monthly(1) rent?");
				rent_type = 0;
				vld_flg = true;
				do {
					try {
						rent_type = Integer.parseInt(in.nextLine());
						vld_flg=false;
					}catch (NumberFormatException e) {
						System.out.println("Error - invalid type of input , should ne an Integer!");
					}catch (Exception e) {
						System.out.println("Try Again:");
						in.next();
					}
				}while(vld_flg);
				if (rent_type != 1 && rent_type != 0) {
					System.out.println(invalid_input);
					System.out.println(end_op);
					break;
				}
				switch (rent_type) {
				case 0:
					time_unit = " days";
					myOffice.show_type_apt(3);
					System.out.println("How long is the period in days?");
					vld_flg = true;
					do {
						try {
							duration = Integer.parseInt(in.nextLine());
							vld_flg=false;
						}catch (NumberFormatException e) {
							System.out.println("Error - invalid type of input , should ne an Integer!");
						}catch (Exception e) {
							System.out.println("Try Again:");
							in.next();
						}
					}while(vld_flg);
					break;
				case 1:
					time_unit = " months";
					myOffice.show_type_apt(2);
					System.out.println("How long is the period in months?");
					vld_flg = true;
					do {
						try {
							duration = Integer.parseInt(in.nextLine());
							vld_flg=false;
						}catch (NumberFormatException e) {
							System.out.println("Error - invalid type of input , should ne an Integer!");
						}catch (Exception e) {
							System.out.println("Try Again:");
							in.next();
						}
					}while(vld_flg);
					break;
				}
				System.out.println("Enter the requested apartment address:");
				req_adr = in.nextLine();
				if (myOffice.check_if_apt_exsist(req_adr) == false) {
					break;
				}
				int calc_res = myOffice.calc_rent_for_period(duration, req_adr);
				System.out.println("Price for the entire rental period: " + String.valueOf(calc_res) + " ILS for "
						+ String.valueOf(duration) + time_unit);
				System.out.println(end_op);
				break;

			case 6: // *********************** action 6 ----> The most expensive apartment for the
					// rental period
				duration = 0;
				System.out.println("How long is the period in days?");
				vld_flg = true;
				do {
					try {
						duration = Integer.parseInt(in.nextLine());
						vld_flg=false;
					}catch (NumberFormatException e) {
						System.out.println("Error - invalid type of input , should ne an Integer!");
					}catch (Exception e) {
						System.out.println("Try Again:");
						in.next();
					}
				}while(vld_flg);
				res_for_most_expensive = myOffice.find_most_exp_for_rent(duration);
				System.out.println(
						"The rental cost of this apartment for the requested period is the most expensive and stands at "
								+ String.valueOf(res_for_most_expensive) + " ILS for " + String.valueOf(duration)
								+ " days.");
				System.out.println(end_op);

				break;

			case 7: // *********************** action 7 ----> Displaying all those interested in the
					// apartment (unsortd)
				System.out.println(dialogAddClitoList[0]);
				req_adr = in.nextLine();
				if (myOffice.check_if_apt_exsist(req_adr) == false) {
					break;
				}
				myOffice.show_all_Intre_for_apt(req_adr);
				System.out.println(end_op);
				break;

			case 8: // *********************** action 8 ----> Displaying all those interested in the
					// apartment in alphabetical order
				System.out.println(dialogAddClitoList[0]);
				req_adr = in.nextLine();
				if (myOffice.check_if_apt_exsist(req_adr) == false) {
					break;
				}
				myOffice.show_Intrested_sorted(req_adr);
				System.out.println(end_op);
				break;
				
			case 9: // *********************** action 8 ----> Create a clone of Apartment
				
				System.out.println(dialogAddClitoList[0]);
				req_adr = in.nextLine();
				myOffice.Create_Clone(req_adr);
				System.out.println(end_op);
				break;
				
			case 10:
				myOffice.show_apts_with_AFees();
				System.out.println(end_op);
				break;
				
			case 11:
				System.out.println(dialogAddClitoList[0]);
				req_adr = in.nextLine();
				myOffice.delete_apt_by_address(req_adr);
				System.out.println(end_op);
				break;
				
			case 12: // *********************** action 9 ----> Exit the Main menu,save changes and Stop the
					// Program
				System.out.println("Saving changes to DB...");
				savingData_apt(Apartments, Apartments_data);
				savingData_cust(customers, customers_data);
				savingData_intres(Apartments, customers, Intrested_data);
				writeFile(apt_DB, Apartments_data);
				writeFile(cust_DB, customers_data);
				writeFile(intre_DB, Intrested_data);
				System.out.println("Bye Bye!");
				main_f = false;
				break;
			}
		}
	}
}