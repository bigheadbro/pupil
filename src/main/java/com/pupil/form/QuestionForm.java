package com.pupil.form;

import java.io.Serializable;

public class QuestionForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7238602389806897715L;
	
	private int questionid;
	
	private int choice;
	
	private int userid;

	/**
	 * @return the qustionid
	 */
	public int getQuestionid() {
		return questionid;
	}

	/**
	 * @param qustionid the qustionid to set
	 */
	public void setQuestionid(int questionid) {
		this.questionid = questionid;
	}

	/**
	 * @return the answer
	 */
	public int getChoice() {
		return choice;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setChoice(int choice) {
		this.choice = choice;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}
}
