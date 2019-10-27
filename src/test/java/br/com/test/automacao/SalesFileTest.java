package br.com.test.automacao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.test.automacao.model.Customer;
import br.com.test.automacao.model.Item;
import br.com.test.automacao.model.Sale;
import br.com.test.automacao.model.Salesman;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SalesFileTest {

	private BufferedReader br;
	private static List<String> content = new ArrayList<String>();
	private String result = "";
	private int invalidCPFs;
	private static int oldestCustomerAge;
	private String oldestCustomerName = "";
	private List<String> salesmen;
	private List<Item> items;
	private Sale sale;
	private Double maxSalePrice = 0.0;
	private int maxSaleID;

	@Given("^I access the test data file$")
	public void access_test_file() throws FileNotFoundException {
		String basePath = new File("").getAbsolutePath();
		String path = new File("DataFile.txt").getAbsolutePath();
		br = new BufferedReader(new FileReader(path));
	}

	@When("^The file information are read$")
	public void read_test_file() throws IOException {
		String line = "";

		while ((line = br.readLine()) != null) {
			content.add(line);
		}
	}
	
	@Then("^It should verify the number of lines successfully imported$")
	public void verify_number_of_lines_imported() {
		result += "NUMBER OF IMPORTED LINES: " + content.size() + "\n";
	}
	
	@Then("^It should verify the number of customers with invalid CPF$")
	public void verify_number_of_customers_with_invalid_cpf() {
		for (String c : content) {
			if (c.substring(0, 3).equals("002")) {
				String cpf = c.substring(4, 18);
				/*
				 * Verifying if the Customer CPF is valid and counting the total of invalid ones
				 */
				if (!validateCPF(cpf))
					invalidCPFs++;
			}
		}
		result += "NUMBER OF INVALID CPFs: " + invalidCPFs + "\n";
	}
	
	@Then("^It should verify the name of the oldest customer$")
	public void verify_name_of_the_oldest_customer() {
		Customer oldestCustomer = null;
		for (String c : content) {
			if (c.substring(0, 3).equals("002")) {
				String customerInfo[] = c.split(";");
				int age = Integer.parseInt(customerInfo[3]);
				if (age > oldestCustomerAge) {
					oldestCustomerAge = age;
					oldestCustomerName = customerInfo[2];
					oldestCustomer = new Customer(customerInfo[1], customerInfo[2], Integer.parseInt(customerInfo[3]));
				}
			}
		}
		result += "NAME OF THE OLDEST CUSTOMER: " + oldestCustomer.getName() + "\n";
	}
	
	@Then("^It should verify the name of the worst salesman$")
	public void verify_name_of_the_worst_salesman() {
		salesmen = new ArrayList<String>();
		String worstSalesmanName = "";
		for (String c : content) {
			if (c.substring(0, 3).equals("003")) {
				/**
				 * Reading the Sale information
				 */
				String saleInfo[] = c.split(";");
				salesmen.add(saleInfo[3]);
				
				Map<String, Long> salesmenCounts = salesmen.stream()
						.collect(Collectors.groupingBy(e -> e, Collectors.counting()));
				String salesmenList[] = salesmenCounts.toString().split(",");

				String worstSalesman[];				
				int worstSalesmanSales = (int) Double.POSITIVE_INFINITY;
				;
				for (String s : salesmenList) {
					s = s.replace("{", "");
					s = s.replace("}", "");
					worstSalesman = s.split("=");
					if (Integer.parseInt(worstSalesman[1]) < worstSalesmanSales) {
						worstSalesmanSales = Integer.parseInt(worstSalesman[1]);
						worstSalesmanName = worstSalesman[0];
					}
				}
			}
		}
		result += "NAME OF THE WORST SALESMAN: " + worstSalesmanName + "\n";
	}
	
	@Then("^It should verify the id of the most expensive sale$")
	public void verify_the_id_of_the_most_expensive_sale() {
		for (String c : content) {
			if (c.substring(0, 3).equals("003")) {
				/**
				 * Reading the Sale information
				 */
				String saleInfo[] = c.split(";");
				salesmen.add(saleInfo[3]);

				/**
				 * Getting the Sale Id and Salesman name information
				 */

				int id = Integer.parseInt(saleInfo[1]);
				String salesmanName = saleInfo[3];
				Item item;
				items = new ArrayList<Item>();

				/**
				 * Getting the Sale items information
				 */
				String strItems = saleInfo[2];
				String strSaleItems[] = strItems.split(",");
				for (int i = 0; i < strSaleItems.length; i++) {
					strSaleItems[i] = strSaleItems[i].replace("[", "");
					strSaleItems[i] = strSaleItems[i].replace("]", "");
					String strSaleItemDetails[] = strSaleItems[i].split("-");
					item = new Item(Integer.parseInt(strSaleItemDetails[0]), Integer.parseInt(strSaleItemDetails[1]),
							Double.parseDouble(strSaleItemDetails[2]));
					items.add(item);
				}

				/**
				 * Instantiating the Sale object and getting its total price information
				 */
				sale = new Sale(id, items, salesmanName);
				Double itemPrice = 0.0;
				for (int i = 0; i < items.size(); i++) {
					Item saleItem = items.get(i);
					itemPrice += saleItem.getItemPrice();
				}

				/**
				 * Getting the Sale ID of the most expensive Sale
				 */
				if (itemPrice > maxSalePrice) {
					maxSalePrice = itemPrice;
					maxSaleID = sale.getId();
				}
			}
		}
		result += "ID OF MOST EXPENSIVE SALE: " + maxSaleID + "\n";
	}
	
	@Then("^It should generate a flat file with the test result$")
	public void generate_file_with_the_test_result() {
		/**
		 * Creating the text file with the test result
		 */
		PrintWriter writer;
		try {
			String basePath = new File("").getAbsolutePath();
			String path = new File("TestResult.txt").getAbsolutePath();

			writer = new PrintWriter(path, "UTF-8");
			writer.println(result);
			writer.close();
			System.out.println(result);
			System.out.println("Test Result File generaged: " + path);
		} catch (FileNotFoundException | UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Verifying if the Customer CPF is valid
	 * 
	 * @param cpf
	 * @return boolean
	 */
	public boolean validateCPF(String cpf) {
		cpf = cpf.replace(".", "");
		cpf = cpf.replace("-", "");

		if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222")
				|| cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888")
				|| cpf.equals("99999999999") || (cpf.length() != 11))
			return (false);

		char dig10, dig11;
		int sm, i, r, num, peso;

		try {
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48);

			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException ime) {
			return (false);
		}
	}
}
