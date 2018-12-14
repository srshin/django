package com.encore.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class CrawlingTest {
	
	public static void main(String[] args) throws IOException {
		call2();
	}
	
	public static void call2() throws IOException   {
		URL url = new URL("https://api.bithumb.com/public/ticker/ALL");
		HttpsURLConnection conn = null;
		conn = (HttpsURLConnection)url.openConnection();
		InputStream a = conn.getInputStream();
		InputStreamReader b = new InputStreamReader(a, "UTF-8");
		BufferedReader br = new BufferedReader(b);
		String line = "";
		String result = "";
		while((line = br.readLine())!= null) {
			System.out.println(line);
			result+=line;
		}
		JsonParser parser = new JsonParser();
		JsonObject json = new JsonObject();
		JsonElement je = parser.parse(result);
		
		json = je.getAsJsonObject().getAsJsonObject("data").getAsJsonObject("BTC");
		System.out.println("=========================");
		System.out.println(json);
		String keys[]= {"opening_price", "closing_price"};
		for (String key: keys)
		System.out.println(json.get(key));
	}
	
	public static void call1() throws IOException  {
		FileWriter writer = new FileWriter("bitsum.csv",false);
		BufferedWriter bw = new BufferedWriter(writer);
		
		Document doc = Jsoup.connect("https://www.bithumb.com/").get();
		Elements rows =doc.body().select("#tableAsset > tbody > tr");
		for (int i=0; i<rows.size(); i++) {
			String s="";
			Elements cols = rows.eq(i).select("td");
			for (Element e:cols) {
				s+=e.select("strong").text()+",";
			}
			bw.write(s);
			System.out.println(s);
			bw.newLine();
		}
		bw.close();
	}
}
