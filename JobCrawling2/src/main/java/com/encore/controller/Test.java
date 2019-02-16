package com.encore.controller;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//String c = "!\"#$%&(사람인){}@`*:+;-.<>,^~|'[]"; 
		String c = "(사람인) [잡코리아]  {빅데이터} machine. "; 
	    //c = c.replaceAll("!\"#[$]%&\\(\\)\\{\\}@`[*]:[+];-.<>,\\^~|'\\[\\]", ""); 
	    c = c.replaceAll("\\(|\\)|\\]|\\[|\\{|\\}|\\.", ""); 
	    
	    String d ="\\[|\\]";
	    System.out.println(c);
	    String s= "10350. 비씨카드 차세대-정보계 JAVA-  -장기SI";
		String sep = "\\s*\\:\\s*|\\s*\\/\\s*|\\s+\\-\\s+|\\s+&\\s+|\\.\\s+|\\s+";
		String[] words = s.trim().split(sep);
		for (String word:words) {

	    word = word.replaceAll("-$|^-", "");
		//word = word.trim().replaceAll("^-", "");
		System.out.println(word);
		}


		
	}

}
