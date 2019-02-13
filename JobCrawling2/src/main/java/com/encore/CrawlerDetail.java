package com.encore;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

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
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CrawlerDetail {

	public static void main(String[] args) throws InterruptedException {
		String quote = "\"";
		String delimiter = ",";
		System.setProperty("webdriver.chrome.driver", "D:\\BigData\\Download\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		String header = "corpName,detail,period,corpDetail";
		String fileName = "jobKoreaDetail.csv";
		BufferedWriter bw = null;
		int j=0;
		String[] sites = {
				"http://www.jobkorea.co.kr/Recruit/GI_Read/27365704?Oem_Code=C1&view_type=01",
				"http://www.jobkorea.co.kr/Recruit/GI_Read/27625866?Oem_Code=C1&view_type=01",
				"http://www.jobkorea.co.kr/Recruit/GI_Read/27180101?Oem_Code=C1&view_type=01",
				"http://www.jobkorea.co.kr/Recruit/GI_Read/27599582?Oem_Code=C1&view_type=01"
		};
		
		
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true), "UTF-8"));
			System.out.println(header);
			bw.write(header); 
			bw.newLine();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException   e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		
		for (String site : sites) {
			try {
			String item="";
		driver.get(site);
			
		Thread.sleep(2000); // Let the user actually see something!
		String element0 = "#container > h1 > span";
		WebElement row0 = driver.findElement(By.cssSelector(element0));
		item +=quote + row0.getText() + quote+ delimiter;

		driver.switchTo().frame("gib_frame");
		String element = "body";
		WebElement row = driver.findElement(By.cssSelector(element));
		item +=quote + row.getText() + quote+ delimiter;

		driver.switchTo().parentFrame();
		String element1 = "article.artReadPeriod";
		WebElement row1 = driver.findElement(By.cssSelector(element1));
		item +=quote + row1.getText() + quote+ delimiter;

		String element3 = "article.artReadCoInfo.divReadBx";
		WebElement row3 = driver.findElement(By.cssSelector(element3));
		item +=quote + row3.getText() + quote;
		System.out.print(j++ + "  ");
		System.out.println(item);
			bw.write(item);
			bw.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("element 읽다가 오류 ");

		} 

		}
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("프로그램을 종료합니다");
	}
}