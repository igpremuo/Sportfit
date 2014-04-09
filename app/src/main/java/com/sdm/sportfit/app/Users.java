package com.sdm.sportfit.app;

import java.sql.Timestamp;

public class Users {
	private int id;
	private String name;
	private String email;
	private String password_hash;
	private String api_key;
	private Timestamp created_at;
	private double locationx;
	private double locationy;
	private String picture;
	
	public Users() {
		id = 0;
		name = "";
		email = "";
		password_hash = "";
		api_key = "";
		created_at = new Timestamp(0);
		locationx = 0.0;
		locationy = 0.0;
		picture = "";
	}
	
	public Users(int id, String name, String email, String password_hash, String api_key, Timestamp created_at, double locationx, double locationy, String picture) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password_hash = password_hash;
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
	
	public String getPassword_hash() {
		return password_hash;
	}
	
	public String getApi_key() {
		return api_key;
	}
	
	public Timestamp getCreated_at() {
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
	
	public void setPassword_hash(String password_hash) {
		this.password_hash = password_hash;
	}
	
	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}
	
	public void setCreated_at(Timestamp created_at) {
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
