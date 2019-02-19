package com.encore.controller;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.encore.model.CorpDAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

public class CrawlerDetail2 {

	public static void main(String[] args) throws InterruptedException, IOException {
		String driverLocation = "D:\\BigData\\Download\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", driverLocation);
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		String today = mSimpleDateFormat.format(new Date());
		BufferedWriter bw = openFile(today);
		WebDriver driver = new ChromeDriver();
		locateURL(driver, bw);
		driver.quit();
		System.out.println("프로그램을 종료합니다");
		bw.close();

	}

	public static BufferedWriter openFile(String mTime) {
		String fileName = "jobKoreaDetail.csv";
		String dirName = "csvFiles";
		String header = "searchlist.id, searchlist.jobInfoHref, idNum, corpName,period,corpDetail,infoDetail";
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(dirName + File.separator + mTime + "_" + fileName, true), "UTF-8"));
			System.out.println(header);
			bw.write(header);
			bw.newLine();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bw;
	}
//	create table detailList(
//			 idDetail varchar2(200),
//			 idNum varchar2(200),
//			 corpName varchar2(100) ,
//			 jobInfoHref  varchar2(500),
//			 jobPeriod varchar2(1000), 
//			 corpDetail varchar2(4000), 
//			 jobInfoDetail   varchar2(4000)
//			 );
	public static void locateURL(WebDriver driver, BufferedWriter bw) {
		String[] tagList = { "#container > h1 > span", "article.artReadPeriod", "article.artReadCoInfo.divReadBx",
				"gib_frame", "body" };

		String quote = "\"";
		String delimiter = ",";
		int currentLine = 1;
		List<String> columns =new ArrayList<>();
		columns.add("idNum");
		columns.add("jobInfoHref");
		CorpDAO dao = new CorpDAO();
		List<List<String>> list = dao.selectByColumns(columns);
		List<List<String>> mylist= list.subList(0, 5);

		for (List<String> line  : mylist) {
			String item = "";
			item +=line.get(0)+",";
			item +=line.get(1)+",";
					
			driver.get(line.get(1));
			item += currentLine++ + ",";
			try {
				Thread.sleep(10000); // Let the user actually see something!
				for (String tag : tagList) {
					if (tag.equals("gib_frame")) {
						driver.switchTo().frame("gib_frame");
						// driver.switchTo().parentFrame();
						continue;
					}


					WebElement row = driver.findElement(By.cssSelector(tag));
					item += quote + row.getText() + quote + delimiter;

					Thread.sleep(300); // Let the user actually see something!

				}
				System.out.println(item);
				bw.write(item);
				bw.newLine();

			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("element 읽다가 오류 ");
			}
		}

	}

}