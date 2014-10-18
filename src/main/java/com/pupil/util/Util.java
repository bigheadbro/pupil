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

		ques.setQid(dim.getQid());

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
		ques.setQid(qid);

		return ques;
	}
		
		
	// 计算维度题
	public static List<QuestionEntity> calcDimensionScore(DimensionEntity dim1,DimensionEntity dim2,DimensionEntity dim3,DimensionEntity dim4, int uid, QuestionForm form) {
		List<QuestionEntity> list = new ArrayList<QuestionEntity>();
		//A
		QuestionEntity ques = new QuestionEntity();
		ques.setUserId(uid);
		ques.setChoice(form.getChoice());
		ques.setQid(dim1.getQid());

		//B
		QuestionEntity ques2 = new QuestionEntity();
		ques2.setUserId(uid);

		ques2.setQid(dim2.getQid());
		
		//C
		QuestionEntity ques3 = new QuestionEntity();
		ques3.setUserId(uid);

		ques3.setQid(dim3.getQid());

				
		//D
		QuestionEntity ques4 = new QuestionEntity();
		ques4.setUserId(uid);

		ques4.setQid(dim4.getQid());

		
		list.add(ques);
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
