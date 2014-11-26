package com.pupil.entity;

import java.io.Serializable;

public class UserinfoEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	private int id;
	//
	private int userId;
	//
	private int state;

	private String gmtcreate;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getGmtcreate()
	{
		return gmtcreate;
	}

	public void setGmtcreate(String gmtcreate)
	{
		this.gmtcreate = gmtcreate;
	}
}