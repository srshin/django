package bitthumJsoup;



import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class bitthumb {
	
	public static void call2() throws IOException {

		
		URL url = new URL("https://api.bithumb.com/public/ticker/ALL");
		HttpsURLConnection conn = null;
		conn = (HttpsURLConnection)url.openConnection();

		InputStream a = conn.getInputStream();
		InputStreamReader b = new InputStreamReader(a, "UTF-8");
		BufferedReader br = new BufferedReader(b);

		
		FileOutputStream aFile = new FileOutputStream("bithumb.csv", true);
		OutputStreamWriter bFile = new OutputStreamWriter(aFile, "UTF-8");		
		BufferedWriter bw = new BufferedWriter( bFile );
		
		
		String line="";
		String json="";
		while((line = br.readLine())!=null) {
			json += line;
		}
		//System.out.println(json);
		
		System.out.println("==========parsing============");
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(json);
		System.out.println(jsonObject);
		
		//JsonElement str = jsonObject.get("status");
		//System.out.println(str);
		JsonObject jsonObject1 = jsonObject.getAsJsonObject("data");
		System.out.println(jsonObject1);
		
		String[] coinTypes= { "BTC", "ETH", "DASH", "LTC"};

		String[] keys = {"opening_price", "closing_price", 
				"min_price", "max_price", "average_price", 
				"units_traded", "volume_1day", "volume_7day",
				"buy_price", "sell_price", "24H_fluctate", "24H_fluctate_rate"};
		System.out.println("coinType,  ");
	  	bw.write("coinType,  ");

		for (String key : keys) {
			System.out.print(key+", ");
			bw.write(key+",");
		}
		System.out.println();
		bw.newLine();
		for (String coin: coinTypes) {
			JsonObject jsonObject2 = jsonObject1.getAsJsonObject(coin);
			System.out.print(coin+",");
			bw.write(coin+",");
			for(String key : keys) {
				 System.out.print(jsonObject2.get(key)+", ");
				 bw.write(jsonObject2.get(key)+", ");
			}
			System.out.println();
			bw.newLine();
		}
		
		bw.close();
		bFile.close();
		aFile.close();

		
	}

	
	public static void call() throws IOException {
		FileOutputStream a = new FileOutputStream("bithumb.csv", true);
		OutputStreamWriter b = new OutputStreamWriter(a, "UTF-8");		
		BufferedWriter bw = new BufferedWriter( b );
		
		Document doc = Jsoup.connect("https://www.bithumb.com/").get();
		Elements rows = doc.body().select("#tableAsset > tbody > tr");
		
		for(int i=0; i<rows.size(); i++) {
			String s = "";
		  	Elements cols = rows.eq(i).select("td");
		  	for(Element e:cols) {
		  		 s += e.select("strong").text() + ",";
		  	}	  	
		  	bw.write(s);
		  	bw.newLine();
		}	
		bw.close();
		b.close();
		a.close();
	}		
	public static void main(String[] args) throws IOException {
		call2();

	}

}
