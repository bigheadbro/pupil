package com.pupil.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.pupil.common.Account;
import com.pupil.common.Constant;
import com.pupil.entity.DimensionEntity;
import com.pupil.entity.QuestionEntity;
import com.pupil.form.QuestionForm;

public class Util {

	public static boolean isMale(int gender) {
		if (gender > 0)
			return false;
		else
			return true;
	}

	public static void parseJob() {
		File file = new File("info/job.txt");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			System.out.println("<ul class=\"sub\">");
			while ((tempString = reader.readLine()) != "==") {
				
				if(tempString.trim().isEmpty() ){
					System.out.println("</ul>");
					System.out.println("<ul class=\"sub\">");
				}
				else{
					System.out.println("<li>"+tempString.split("#")[0]+"</li>");
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	
	// 获取学生信息
	public static Account getStudentInfo(String cardno) {
		Account account = new Account();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("http://180.153.108.93/EvaluationInterface.do?cardno=" + cardno);
			doc.getDocumentElement().normalize();
			account.setCardno(doc.getElementsByTagName("cardno").item(0).getTextContent());
			account.setUserName(doc.getElementsByTagName("name").item(0).getTextContent());
			account.setSchool(doc.getElementsByTagName("shool").item(0).getTextContent());
			if(StringUtil.isEqual(doc.getElementsByTagName("sex").item(0).getTextContent(), "女"))
			{
				account.setGender(1);
			}
			else if(StringUtil.isEqual(doc.getElementsByTagName("sex").item(0).getTextContent(), "null"))
			{
				account.setGender(2);
			}
			else
			{
				account.setGender(0);
			}

		} catch (Exception e) {
			
		}

		return account;
	}

	// 计算单选题
	public static QuestionEntity calcSingleScore(DimensionEntity dim, int uid,
			int choice, int time) {
		QuestionEntity ques = new QuestionEntity();
		ques.setUserId(uid);
		ques.setChoice(choice);
		ques.setTime(time);
		ques.setQid(dim.getQid());
		ques.setNumber(dim.getNumber());
		ques.setLoyalty(dim.getLoyalty());
		ques.setPositive(dim.getPositive());
		ques.setResponsibility(dim.getResponsibility());
		ques.setMorality(dim.getMorality());
		ques.setThinking(dim.getThinking());
		ques.setPlan(dim.getPlan());
		ques.setInnovation(dim.getInnovation());
		ques.setTeamwork(dim.getTeamwork());
		ques.setCommunication(dim.getCommunication());
		ques.setStrain(dim.getStrain());
		ques.setDetails(dim.getDetails());
		ques.setPotential(dim.getPotential());
		return ques;
	}
	
	// 智力题
	public static QuestionEntity calcIntelligenceScore(DimensionEntity dim,  int uid, int qid,
			int choice, int time) {
		if(dim == null)
		{
			dim = new DimensionEntity();
		}
		QuestionEntity ques = new QuestionEntity();
		ques.setUserId(uid);
		ques.setChoice(choice);
		ques.setTime(time);
		ques.setQid(qid);

		ques.setIntelligence(dim.getIntelligence());
		return ques;
	}
		
		
	// 计算维度题
	public static List<QuestionEntity> calcDimensionScore(DimensionEntity dim1,DimensionEntity dim2,DimensionEntity dim3,DimensionEntity dim4, int uid, QuestionForm form) {
		List<QuestionEntity> list = new ArrayList<QuestionEntity>();
		//A
		QuestionEntity ques = new QuestionEntity();
		ques.setUserId(uid);
		ques.setChoice(form.getChoice());
		ques.setTime(form.getTime());
		ques.setQid(dim1.getQid());
		ques.setNumber(form.getNumber());
		ques.setLoyalty(dim1.getLoyalty()*(form.getChoice()-Constant.DIMENSION_CONSTANT));
		ques.setPositive(dim1.getPositive()*(form.getChoice()-Constant.DIMENSION_CONSTANT));
		ques.setResponsibility(dim1.getResponsibility()*(form.getChoice()-Constant.DIMENSION_CONSTANT));
		ques.setMorality(dim1.getMorality()*(form.getChoice()-Constant.DIMENSION_CONSTANT));
		ques.setThinking(dim1.getThinking()*(form.getChoice()-Constant.DIMENSION_CONSTANT));
		ques.setPlan(dim1.getPlan()*(form.getChoice()-Constant.DIMENSION_CONSTANT));
		ques.setInnovation(dim1.getInnovation()*(form.getChoice()-Constant.DIMENSION_CONSTANT));
		ques.setTeamwork(dim1.getTeamwork()*(form.getChoice()-Constant.DIMENSION_CONSTANT));
		ques.setCommunication(dim1.getCommunication()*(form.getChoice()-Constant.DIMENSION_CONSTANT));
		ques.setStrain(dim1.getStrain()*(form.getChoice()-Constant.DIMENSION_CONSTANT));
		ques.setDetails(dim1.getDetails()*(form.getChoice()-Constant.DIMENSION_CONSTANT));
		ques.setPotential(dim1.getPotential()*(form.getChoice()-Constant.DIMENSION_CONSTANT));
		//B
		QuestionEntity ques2 = new QuestionEntity();
		ques2.setUserId(uid);
		ques2.setChoice(form.getChoice2());
		ques2.setTime(form.getTime());
		ques2.setQid(dim2.getQid());
		ques2.setNumber(form.getNumber2());
		ques2.setLoyalty(dim2.getLoyalty()*(form.getChoice2()-Constant.DIMENSION_CONSTANT));
		ques2.setPositive(dim2.getPositive()*(form.getChoice2()-Constant.DIMENSION_CONSTANT));
		ques2.setResponsibility(dim2.getResponsibility()*(form.getChoice2()-Constant.DIMENSION_CONSTANT));
		ques2.setMorality(dim2.getMorality()*(form.getChoice2()-Constant.DIMENSION_CONSTANT));
		ques2.setThinking(dim2.getThinking()*(form.getChoice2()-Constant.DIMENSION_CONSTANT));
		ques2.setPlan(dim2.getPlan()*(form.getChoice2()-Constant.DIMENSION_CONSTANT));
		ques2.setInnovation(dim2.getInnovation()*(form.getChoice2()-Constant.DIMENSION_CONSTANT));
		ques2.setTeamwork(dim2.getTeamwork()*(form.getChoice2()-Constant.DIMENSION_CONSTANT));
		ques2.setCommunication(dim2.getCommunication()*(form.getChoice2()-Constant.DIMENSION_CONSTANT));
		ques2.setStrain(dim2.getStrain()*(form.getChoice2()-Constant.DIMENSION_CONSTANT));
		ques2.setDetails(dim2.getDetails()*(form.getChoice2()-Constant.DIMENSION_CONSTANT));
		ques2.setPotential(dim2.getPotential()*(form.getChoice2()-Constant.DIMENSION_CONSTANT));
		
		//C
		QuestionEntity ques3 = new QuestionEntity();
		ques3.setUserId(uid);
		ques3.setChoice(form.getChoice3());
		ques3.setTime(form.getTime());
		ques3.setQid(dim3.getQid());
		ques3.setNumber(form.getNumber3());
		ques3.setLoyalty(dim3.getLoyalty()*(form.getChoice3()-Constant.DIMENSION_CONSTANT));
		ques3.setPositive(dim3.getPositive()*(form.getChoice3()-Constant.DIMENSION_CONSTANT));
		ques3.setResponsibility(dim3.getResponsibility()*(form.getChoice3()-Constant.DIMENSION_CONSTANT));
		ques3.setMorality(dim3.getMorality()*(form.getChoice3()-Constant.DIMENSION_CONSTANT));
		ques3.setThinking(dim3.getThinking()*(form.getChoice3()-Constant.DIMENSION_CONSTANT));
		ques3.setPlan(dim3.getPlan()*(form.getChoice3()-Constant.DIMENSION_CONSTANT));
		ques3.setInnovation(dim3.getInnovation()*(form.getChoice3()-Constant.DIMENSION_CONSTANT));
		ques3.setTeamwork(dim3.getTeamwork()*(form.getChoice3()-Constant.DIMENSION_CONSTANT));
		ques3.setCommunication(dim3.getCommunication()*(form.getChoice3()-Constant.DIMENSION_CONSTANT));
		ques3.setStrain(dim3.getStrain()*(form.getChoice3()-Constant.DIMENSION_CONSTANT));
		ques3.setDetails(dim3.getDetails()*(form.getChoice3()-Constant.DIMENSION_CONSTANT));
		ques3.setPotential(dim3.getPotential()*(form.getChoice3()-Constant.DIMENSION_CONSTANT));
				
		//D
		QuestionEntity ques4 = new QuestionEntity();
		ques4.setUserId(uid);
		ques4.setChoice(form.getChoice4());
		ques4.setTime(form.getTime());
		ques4.setQid(dim4.getQid());
		ques4.setNumber(form.getNumber4());
		ques4.setLoyalty(dim4.getLoyalty()*(form.getChoice4()-Constant.DIMENSION_CONSTANT));
		ques4.setPositive(dim4.getPositive()*(form.getChoice4()-Constant.DIMENSION_CONSTANT));
		ques4.setResponsibility(dim4.getResponsibility()*(form.getChoice4()-Constant.DIMENSION_CONSTANT));
		ques4.setMorality(dim4.getMorality()*(form.getChoice4()-Constant.DIMENSION_CONSTANT));
		ques4.setThinking(dim4.getThinking()*(form.getChoice4()-Constant.DIMENSION_CONSTANT));
		ques4.setPlan(dim4.getPlan()*(form.getChoice4()-Constant.DIMENSION_CONSTANT));
		ques4.setInnovation(dim4.getInnovation()*(form.getChoice4()-Constant.DIMENSION_CONSTANT));
		ques4.setTeamwork(dim4.getTeamwork()*(form.getChoice4()-Constant.DIMENSION_CONSTANT));
		ques4.setCommunication(dim4.getCommunication()*(form.getChoice4()-Constant.DIMENSION_CONSTANT));
		ques4.setStrain(dim4.getStrain()*(form.getChoice4()-Constant.DIMENSION_CONSTANT));
		ques4.setDetails(dim4.getDetails()*(form.getChoice4()-Constant.DIMENSION_CONSTANT));
		ques4.setPotential(dim4.getPotential()*(form.getChoice4()-Constant.DIMENSION_CONSTANT));
		
		list.add(ques);
		list.add(ques2);
		list.add(ques3);
		list.add(ques4);
		return list;
	}

	// 15题
	public static List<QuestionEntity> calcProcessScore(DimensionEntity dim,DimensionEntity dim2,DimensionEntity dim3, int uid, QuestionForm form) 
	{
		if(dim == null)
		{
			dim = new DimensionEntity();
		}
		List<QuestionEntity> list = new ArrayList<QuestionEntity>();
		QuestionEntity ques = new QuestionEntity();
		ques.setUserId(uid);
		ques.setChoice(form.getChoice());
		ques.setTime(form.getTime());
		ques.setQid(form.getQuestionid());
		ques.setNumber(1);
		ques.setLoyalty(dim.getLoyalty());
		ques.setPositive(dim.getPositive());
		ques.setResponsibility(dim.getResponsibility());
		ques.setMorality(dim.getMorality());
		ques.setThinking(dim.getThinking());
		ques.setPlan(dim.getPlan());
		ques.setInnovation(dim.getInnovation());
		ques.setTeamwork(dim.getTeamwork());
		ques.setCommunication(dim.getCommunication());
		ques.setStrain(dim.getStrain());
		ques.setDetails(dim.getDetails());
		ques.setPotential(dim.getPotential());
		
		list.add(ques);
		
		//if(form.getChoice() == 3)//第一题选择了C
		//{
			QuestionEntity ques2 = new QuestionEntity();
			ques2.setUserId(uid);
			ques2.setTime(form.getTime());
			ques2.setQid(form.getQuestionid());
			ques2.setNumber(2);
			if(form.getChoice2() == 2 && form.getChoice3() == 5 && form.getChoice4() == 3)
			{
				ques2.setNumber(2);
				ques2.setChoice(1);
				ques2.setLoyalty(dim2.getLoyalty());
				ques2.setPositive(dim2.getPositive());
				ques2.setResponsibility(dim2.getResponsibility());
				ques2.setMorality(dim2.getMorality());
				ques2.setThinking(dim2.getThinking());
				ques2.setPlan(dim2.getPlan());
				ques2.setInnovation(dim2.getInnovation());
				ques2.setTeamwork(dim2.getTeamwork());
				ques2.setCommunication(dim2.getCommunication());
				ques2.setStrain(dim2.getStrain());
				ques2.setDetails(dim2.getDetails());
				ques2.setPotential(dim2.getPotential());
				list.add(ques2);
			}
			else if(form.getChoice2() == 3 && form.getChoice3() == 4 && form.getChoice4() == 3)
			{
				ques2.setNumber(2);
				ques2.setChoice(2);
				ques2.setLoyalty(dim3.getLoyalty());
				ques2.setPositive(dim3.getPositive());
				ques2.setResponsibility(dim3.getResponsibility());
				ques2.setMorality(dim3.getMorality());
				ques2.setThinking(dim3.getThinking());
				ques2.setPlan(dim3.getPlan());
				ques2.setInnovation(dim3.getInnovation());
				ques2.setTeamwork(dim3.getTeamwork());
				ques2.setCommunication(dim3.getCommunication());
				ques2.setStrain(dim3.getStrain());
				ques2.setDetails(dim3.getDetails());
				ques2.setPotential(dim3.getPotential());
				list.add(ques2);
			}
			else
			{
				ques2.setNumber(2);
				ques2.setChoice(form.getChoice2()*100 + form.getChoice3()*10 + form.getChoice4());
				list.add(ques2);
			}
		//}
		return list;
	}
	
	public static QuestionEntity calcQ16MainScore(DimensionEntity dim, int uid, QuestionForm form) {
		//A
		QuestionEntity ques = new QuestionEntity();
		if(dim == null)
		{
			dim = new DimensionEntity();
		}
		ques.setUserId(uid);
		ques.setChoice(form.getChoice());
		ques.setTime(form.getTime());
		ques.setQid(form.getQuestionid());
		ques.setNumber(1);
		ques.setLoyalty(dim.getLoyalty());
		ques.setPositive(dim.getPositive());
		ques.setResponsibility(dim.getResponsibility());
		ques.setMorality(dim.getMorality());
		ques.setThinking(dim.getThinking());
		ques.setPlan(dim.getPlan());
		ques.setInnovation(dim.getInnovation());
		ques.setTeamwork(dim.getTeamwork());
		ques.setCommunication(dim.getCommunication());
		ques.setStrain(dim.getStrain());
		ques.setDetails(dim.getDetails());
		ques.setPotential(dim.getPotential());
		
		return ques;
	}
	
	public static List<QuestionEntity> calcQ16Score(DimensionEntity dim2,DimensionEntity dim3,DimensionEntity dim4, int uid, QuestionForm form) {
		List<QuestionEntity> list = new ArrayList<QuestionEntity>();
		
		//B
		QuestionEntity ques2 = new QuestionEntity();
		ques2.setUserId(uid);
		ques2.setChoice(form.getChoice2());
		ques2.setTime(form.getTime());
		ques2.setQid(form.getQuestionid());
		ques2.setNumber(form.getNumber2());
		ques2.setLoyalty(dim2.getLoyalty());
		ques2.setPositive(dim2.getPositive());
		ques2.setResponsibility(dim2.getResponsibility());
		ques2.setMorality(dim2.getMorality());
		ques2.setThinking(dim2.getThinking());
		ques2.setPlan(dim2.getPlan());
		ques2.setInnovation(dim2.getInnovation());
		ques2.setTeamwork(dim2.getTeamwork());
		ques2.setCommunication(dim2.getCommunication());
		ques2.setStrain(dim2.getStrain());
		ques2.setDetails(dim2.getDetails());
		ques2.setPotential(dim2.getPotential());
		
		//C
		QuestionEntity ques3 = new QuestionEntity();
		ques3.setUserId(uid);
		ques3.setChoice(form.getChoice3());
		ques3.setTime(form.getTime());
		ques3.setQid(form.getQuestionid());
		ques3.setNumber(form.getNumber3());
		ques3.setLoyalty(dim3.getLoyalty());
		ques3.setPositive(dim3.getPositive());
		ques3.setResponsibility(dim3.getResponsibility());
		ques3.setMorality(dim3.getMorality());
		ques3.setThinking(dim3.getThinking());
		ques3.setPlan(dim3.getPlan());
		ques3.setInnovation(dim3.getInnovation());
		ques3.setTeamwork(dim3.getTeamwork());
		ques3.setCommunication(dim3.getCommunication());
		ques3.setStrain(dim3.getStrain());
		ques3.setDetails(dim3.getDetails());
		ques3.setPotential(dim3.getPotential());
				
		//D
		QuestionEntity ques4 = new QuestionEntity();
		ques4.setUserId(uid);
		ques4.setChoice(form.getChoice4());
		ques4.setTime(form.getTime());
		ques4.setQid(form.getQuestionid());
		ques4.setNumber(form.getNumber4());
		ques4.setLoyalty(dim4.getLoyalty());
		ques4.setPositive(dim4.getPositive());
		ques4.setResponsibility(dim4.getResponsibility());
		ques4.setMorality(dim4.getMorality());
		ques4.setThinking(dim4.getThinking());
		ques4.setPlan(dim4.getPlan());
		ques4.setInnovation(dim4.getInnovation());
		ques4.setTeamwork(dim4.getTeamwork());
		ques4.setCommunication(dim4.getCommunication());
		ques4.setStrain(dim4.getStrain());
		ques4.setDetails(dim4.getDetails());
		ques4.setPotential(dim4.getPotential());
		
		list.add(ques2);
		list.add(ques3);
		list.add(ques4);
		return list;
	}
		
	public static void main(String args[]) {
		Account accnt = getStudentInfo("MjAxMTUyMDAwMg..");
		System.out.println(accnt.getCardno());
		System.out.println(accnt.getSchool());
		System.out.println(accnt.getUserName());

	}

}
