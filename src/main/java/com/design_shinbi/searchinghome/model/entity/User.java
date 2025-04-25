package com.design_shinbi.searchinghome.model.entity;

import java.sql.Timestamp;

public class User {
	private int id;
	private String name;
	private String email;
	private String password;
	private String salt;
	private boolean admin;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public User() {
	}
	
	public User(String name, String email, String password, boolean admin) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.admin = admin;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getSalt() {
		return salt;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public Timestamp getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public String toString() {
		String string = "User [id=" + id + ", name=" + name +
				", email=" + email + ", password=" + password +
				", admin=" + admin + ", createdAt=" + createdAt
				+ ", updateAt=" + updatedAt + "]";
		return string;
	}
}
