package br.com.test.automacao.model;

public class Customer {

	private String CPF;
	private String name;
	private int age;
	
	public Customer() {}

	public Customer(String CPF, String name, int age) {
		this.CPF = CPF;
		this.name = name;
		this.age = age;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String CPF) {
		this.CPF = CPF;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
