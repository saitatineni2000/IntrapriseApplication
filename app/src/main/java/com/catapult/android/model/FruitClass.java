package com.catapult.android.model;

public class FruitClass {
	
	private String fruit_resource;
	private String fruit_name;
	private String fruit_qty;
	private String lastName;
	
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public FruitClass(String fruit_resource, String fruit_name, String fruit_qty, String lastName) {
		super();
		this.setFruit_resource(fruit_resource);
		this.setFruit_name(fruit_name);
		this.setFruit_qty(fruit_qty);
		this.setLastName(lastName);
	}
	public String getFruit_resource() {
		return fruit_resource;
	}
	public void setFruit_resource(String fruit_resource) {
		this.fruit_resource = fruit_resource;
	}
	public String getFruit_name() {
		return fruit_name;
	}
	public void setFruit_name(String fruit_name) {
		this.fruit_name = fruit_name;
	}
	public String getFruit_qty() {
		return fruit_qty;
	}
	public void setFruit_qty(String fruit_qty) {
		this.fruit_qty = fruit_qty;
	}

}
