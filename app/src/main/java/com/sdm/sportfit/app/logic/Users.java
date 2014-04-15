package com.sdm.sportfit.app.logic;

public class Users {
	private int id;
	private String name;
	private String email;
	private String password;
	private String api_key;
	private String created_at;
	private double locationx;
	private double locationy;
	private String picture;
	
	public Users() {
		id = 0;
		name = "";
		email = "";
		password = "";
		api_key = "";
		created_at = "";
		locationx = 0.0;
		locationy = 0.0;
		picture = "";
	}
	
	public Users(String name, String email, String password, String api_key, String created_at, double locationx, double locationy, String picture) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.api_key = api_key;
		this.created_at = created_at;
		this.locationx = locationx;
		this.locationy = locationy;
		this.picture = picture;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getApi_key() {
		return api_key;
	}
	
	public String getCreated_at() {
		return created_at;
	}
	
	public double getLocationx() {
		return locationx;
	}
	
	public double getLocationy() {
		return locationy;
	}
	
	public String getPicture() {
		return picture;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}
	
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	public void setLocationx(double locationx) {
		this.locationx = locationx;
	}
	
	public void setLocationy(double locationy) {
		this.locationy = locationy;
	}
	
	public void setPicture(String picture) {
		this.picture = picture;
	}
  
}
