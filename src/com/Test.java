package com;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import allJobs.JobVO;

public class Test {
	
	@SuppressWarnings("deprecation")
	public static void main(String [] args) throws ParseException {
		
		JobVO jobVO = new JobVO();
		jobVO.setJobName("CA%");
		
		
	//	OracleCon.getJobDetails(jobVO);
		String date="2017-01-03";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date2 = simpleDateFormat.parse(date);
		
		System.out.println(date2);
		
		String format = new SimpleDateFormat("DD-MMM-yy").format(date2);
		System.out.println(format.toUpperCase());
		
	}

}
