package com.pupil.entity;

import java.io.Serializable;

public class DimensionEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7630137768813639287L;
	//
	private int id;
	//
	private int qid;
	private String title;
	//
	private int number;
	private String subtitle;
	//
	private int choice;
	private String answer;
	//
	private double loyalty;
	//
	private double positive;
	//
	private double responsibility;
	//
	private double morality;
	private double intelligence;
	//
	private double thinking;
	//
	private double plan;
	//
	private double innovation;
	//
	private double teamwork;
	//
	private double communication;
	//
	private double strain;
	//
	private double details;
	//
	private double potential;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
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

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @return the loyalty
	 */
	public double getLoyalty() {
		return loyalty;
	}

	/**
	 * @param loyalty
	 *            the loyalty to set
	 */
	public void setLoyalty(double loyalty) {
		this.loyalty = loyalty;
	}

	/**
	 * @return the positive
	 */
	public double getPositive() {
		return positive;
	}

	/**
	 * @param positive
	 *            the positive to set
	 */
	public void setPositive(double positive) {
		this.positive = positive;
	}

	/**
	 * @return the responsibility
	 */
	public double getResponsibility() {
		return responsibility;
	}

	/**
	 * @param responsibility
	 *            the responsibility to set
	 */
	public void setResponsibility(double responsibility) {
		this.responsibility = responsibility;
	}

	/**
	 * @return the morality
	 */
	public double getMorality() {
		return morality;
	}

	/**
	 * @param morality
	 *            the morality to set
	 */
	public void setMorality(double morality) {
		this.morality = morality;
	}

	/**
	 * @return the thinking
	 */
	public double getThinking() {
		return thinking;
	}

	/**
	 * @param thinking
	 *            the thinking to set
	 */
	public void setThinking(double thinking) {
		this.thinking = thinking;
	}

	/**
	 * @return the plan
	 */
	public double getPlan() {
		return plan;
	}

	/**
	 * @param plan
	 *            the plan to set
	 */
	public void setPlan(double plan) {
		this.plan = plan;
	}

	/**
	 * @return the innovation
	 */
	public double getInnovation() {
		return innovation;
	}

	/**
	 * @param innovation
	 *            the innovation to set
	 */
	public void setInnovation(double innovation) {
		this.innovation = innovation;
	}

	/**
	 * @return the teamwork
	 */
	public double getTeamwork() {
		return teamwork;
	}

	/**
	 * @param teamwork
	 *            the teamwork to set
	 */
	public void setTeamwork(double teamwork) {
		this.teamwork = teamwork;
	}

	/**
	 * @return the communication
	 */
	public double getCommunication() {
		return communication;
	}

	/**
	 * @param communication
	 *            the communication to set
	 */
	public void setCommunication(double communication) {
		this.communication = communication;
	}

	/**
	 * @return the strain
	 */
	public double getStrain() {
		return strain;
	}

	/**
	 * @param strain
	 *            the strain to set
	 */
	public void setStrain(double strain) {
		this.strain = strain;
	}

	/**
	 * @return the details
	 */
	public double getDetails() {
		return details;
	}

	/**
	 * @param details
	 *            the details to set
	 */
	public void setDetails(double details) {
		this.details = details;
	}

	/**
	 * @return the potential
	 */
	public double getPotential() {
		return potential;
	}

	/**
	 * @param potential
	 *            the potential to set
	 */
	public void setPotential(double potential) {
		this.potential = potential;
	}

	/**
	 * @return the choice
	 */
	public int getChoice() {
		return choice;
	}

	/**
	 * @param choice the choice to set
	 */
	public void setChoice(int choice) {
		this.choice = choice;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the subtitle
	 */
	public String getSubtitle() {
		return subtitle;
	}

	/**
	 * @param subtitle the subtitle to set
	 */
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * @return the intelligence
	 */
	public double getIntelligence() {
		return intelligence;
	}

	/**
	 * @param intelligence the intelligence to set
	 */
	public void setIntelligence(double intelligence) {
		this.intelligence = intelligence;
	}
}