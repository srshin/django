package com.encore.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {
		// // TODO Auto-generated method stub
		//
		// //String c = "!\"#$%&(사람인){}@`*:+;-.<>,^~|'[]";
		// String c = "(사람인) [잡코리아] {빅데이터} machine. ";
		// //c = c.replaceAll("!\"#[$]%&\\(\\)\\{\\}@`[*]:[+];-.<>,\\^~|'\\[\\]", "");
		// c = c.replaceAll("\\(|\\)|\\]|\\[|\\{|\\}|\\.", "");
		//
		// String d ="\\[|\\]";
		// System.out.println(c);
		// String s= "10350. 비씨카드 차세대-정보계 JAVA- -장기SI";
		// String sep = "\\s*\\:\\s*|\\s*\\/\\s*|\\s+\\-\\s+|\\s+&\\s+|\\.\\s+|\\s+";
		// String[] words = s.trim().split(sep);
		// for (String word:words) {
		//
		// word = word.replaceAll("-$|^-", "");
		// //word = word.trim().replaceAll("^-", "");
		// System.out.println(word);

		//String s = "  The    dog   has a long   tail,  and  it is RED!";
		String x= "http://www.jobkorea.co.kr/Recruit/GI_Read/27636707?Oem_Code=C1&view_type=01";
		
		String reg="[0-9]{8}";
		Matcher m = Pattern.compile("[0-9]{8}").matcher(x); 
		if(m.find()) { 
		    String nums =m.group();
		    System.out.println(nums);
		} 		
				
		Pattern pattern = Pattern.compile("([\\d]{5,})");
		String url = "http://www.jobkorea.co.kr/Recruit/GI_Read/27760879?Oem_Code=C1&view_type=01";
		Matcher match = pattern.matcher(url);
		if (match.find()) {
			String location = match.group();
		//s = s.replaceAll("\\s\\s+", " ");
		//System.out.println(s);
		}
		File file = new File("a.txt");

		// try {
		// BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
		// new FileOutputStream(file, true), "UTF-8"));
		// if (file.exists())
		// System.out.println("file exist");
		// else
		// System.out.println("file new");
		// } catch (UnsupportedEncodingException | FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//

//		String ref = "http://www.gamejob.co.kr/List_GI/GIB_Read.asp?GI_No=135305";
//		if (ref.startsWith("http:"))
//			System.out.println("http");
//		else
//			System.out.println("http없음");

	}

}
