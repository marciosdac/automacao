package br.com.test.automacao.model;

import java.util.List;

public class Sale {

	private int id;
	private List<Item> items;
	private String salesmanName;
	
	public Sale() {}

	public Sale(int id, List<Item> items, String salesmanName) {
		this.id = id;
		this.items = items;
		this.salesmanName = salesmanName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String getSalesmanName() {
		return salesmanName;
	}

	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}	
}
