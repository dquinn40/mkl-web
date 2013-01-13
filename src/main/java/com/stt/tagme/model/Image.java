package com.stt.tagme.model;

public class Image {
	private String refId;
	private String userId;
	private String tagId;
	private String name;
	private long size;
	
	public Image() {};
	
	public Image(String refId, String name, String userId, String tagId, long size) {
		this.refId = refId;
		this.userId = userId;
		this.name = name;
		this.tagId = tagId;
		this.size = size;
	}
	
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	public String getTagId() {
		return this.tagId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public long getSize() {
		return this.size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
}
