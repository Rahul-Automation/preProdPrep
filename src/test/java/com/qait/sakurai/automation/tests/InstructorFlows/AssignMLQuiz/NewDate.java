package com.qait.sakurai.automation.tests.InstructorFlows.AssignMLQuiz;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class NewDate {

	public static void main(String[] args) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
		Date date = new Date();
		System.out.println("Date:" + dateFormat.format(date));
	}

}
