package com.stt.tagme.model;

import java.util.List;

public class User {
	
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private List<Tag> tags;
	private List<String> tagIds;
	
	public User() {};
	
	public User(String firstName, String lastName, String username) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getFirstName() {
		return this.firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getLastName() {
		return this.lastName;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return this.username;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return this.email;
	}
	
	public List<Tag> getTags() {
		return this.tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
	public List<String> getTagIds() {
		return this.tagIds;
	}
	public void setTagIds(List<String> tagIds) {
		this.tagIds = tagIds;
	}

}
