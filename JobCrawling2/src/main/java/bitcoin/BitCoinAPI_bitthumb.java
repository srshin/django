package bitcoin;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;


import javax.net.ssl.HttpsURLConnection;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BitCoinAPI_bitthumb extends Thread {
	static String[] subMenu = Conf.subMenu;
	static String[] coinMenu = Conf.coinMenu;
	static String fileName = Conf.fileName;
	static String fileCount = Conf.fileCount;
	static String realFileSuffix = Conf.realFileSuffix;
	static String completeFileSuffix = Conf.completeFileSuffix;
	
		public void run(){
			
			//System.out.println("## BitCoinAPI_bitthumb Call Start !! ##");
			
			BufferedReader rd = null;
			BufferedWriter bw = null;
			String line = "";
			String result = "";
			HttpsURLConnection huc = null;
			
			//bitthumb Header
			// CoinCode , opening_price,closing_price,min_price,max_price,average_price,units_traded,volume_1day,volume_7day,buy_price,sell_price,24H_fluctate,24H_fluctate_rate,day_time
			
			try {
				String filePath = getFilePath(fileName);

				//Coinone Api Call
				//URL url = new URL("https://api.coinone.co.kr/ticker/?currency=all&format=json");
				//Coinnest Api Call
				//URL url = new URL("https://api.coinnest.co.kr/api/pub/tickerall");
				//BitThumb Api Call
				
				URL url = new URL("https://api.bithumb.com/public/ticker/ALL");
				
				huc = (HttpsURLConnection)url.openConnection();
				huc.addRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
				huc.setRequestMethod("GET");
				
				rd = new BufferedReader(new InputStreamReader(huc.getInputStream(),"UTF-8"));
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true), "UTF-8"));		
				
				while ((line = rd.readLine()) != null) {
					 result += line+"\n";;
				 }
				 
				if(result.length() != 0){
					 JsonObject json = jsonTransper(result);
					 //System.out.println(json);
					 jsonToCSV(json,bw);
				}else {
					 System.out.println("Text Data--length 0");
				}
				bw.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("에러가 발생하여 시스템을 종료합니다");
				System.exit(1);
			}
		}
		
		
		private static JsonObject jsonTransper(String jsonText){
			
			JsonParser parser = new JsonParser();
			JsonObject json = new JsonObject();
			JsonElement je = null;
			
			try {
			
				je = parser.parse(jsonText);
				json = je.getAsJsonObject().getAsJsonObject("data");
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("에러가 발생하여 시스템을 종료합니다");
				System.exit(1);
			}
			return json;
		}
		
		
		private static String jsonToCSV (JsonObject json, BufferedWriter bw){
				
				String csv = "";
				
				try {
				
					for (int i = 0; i < coinMenu.length; i++) {
						
						csv = coinMenu[i]+",";
						JsonObject jo = json.getAsJsonObject(coinMenu[i]);
						if (jo != null) {
						for (int j = 0; j < subMenu.length; j++) {
							String subMenuS =jo.get(subMenu[j]).toString().replaceAll("^\"|\"$", "");
							csv += subMenuS+",";
							if(j == subMenu.length-1){
								csv += json.get("date").toString().replaceAll("^\"|\"$", "");
								//System.out.println(json.get("date"));
								json.get("date");
								bw.write(csv);
								bw.newLine();
							}
							}
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("에러가 발생하여 시스템을 종료합니다");
					System.exit(1);
				}
				return csv;
			}
		
		public static String getFilePath(String s){
			int fileNumber = 0;
			String realFilePath = "";
			String completeFilePath = "";
			File realFile = null;
			File completeFile = null;
			FileReader fr = null;
			BufferedReader br = null;
			BufferedWriter bw = null;
			
			try {
				
				fr = new FileReader(fileCount);
				br = new BufferedReader(fr);
				fileNumber = Integer.parseInt(br.readLine());
				br.close();
				realFilePath = s+fileNumber+realFileSuffix;
				completeFilePath = s+fileNumber+completeFileSuffix;
				realFile = new File(realFilePath);
				completeFile = new File(completeFilePath);

				if(realFile.exists()&&realFile.length() > 12428){
					realFile.renameTo(completeFile);
					System.out.print(completeFilePath);
					System.out.println("size : " + completeFile.length());
					fileNumber++;
					//System.out.println(s);
					//System.out.println(fileNumber);
					realFilePath = s+fileNumber+realFileSuffix;
					bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileCount)));
					bw.write(String.valueOf(fileNumber));
					bw.close();
				} 
				/*else if (!realFile.exists()) {
					System.out.println(realFilePath);
					realFile.createNewFile();
				}*/
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("에러가 발생하여 시스템을 종료합니다");
				System.exit(1);
			}
//			System.out.println(realFilePath);
			return realFilePath;
			
		}		
	
}
