package com.pupil.entity;

import java.io.Serializable;

public class QuestionEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7630137768813639287L;
	//
	private int id;

	//
	private int userId;
	//
	private int qid;
	//
	private int choice;
	
	private int time;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the qid
	 */
	public int getQid() {
		return qid;
	}

	/**
	 * @param qid
	 *            the qid to set
	 */
	public void setQid(int qid) {
		this.qid = qid;
	}

	public int getChoice() {
		return choice;
	}

	public void setChoice(int choice) {
		this.choice = choice;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
}