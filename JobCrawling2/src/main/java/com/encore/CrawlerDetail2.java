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

public class CrawlerDetail2 {

	public static void main(String[] args) throws InterruptedException {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
		String quote = "\"";
		String delimiter = ",";
		//String element = "body > div > table > tbody > tr > td > div > div > div.jkGEN_content > div:nth-child(1) > table > tbody > tr";
		System.setProperty("webdriver.chrome.driver", "D:\\BigData\\Download\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
//		String site = "http://www.jobkorea.co.kr/Recruit/GI_Read/27365704?Oem_Code=C1&view_type=01";
//		String site = "http://www.jobkorea.co.kr/Recruit/GI_Read/27625866?Oem_Code=C1&view_type=01";
//		String site = "http://www.jobkorea.co.kr/Recruit/GI_Read/27180101?Oem_Code=C1&view_type=01";
		String site = "http://www.jobkorea.co.kr/Recruit/GI_Read/27599582?Oem_Code=C1&view_type=01";
		driver.get(site);
			
		Thread.sleep(2000); // Let the user actually see something!

		
		//WebElement iFrame= driver.findElement(By.tagName("iframe"));
		//driver.switchTo().frame(iFrame);
		driver.switchTo().frame("gib_frame");
		String element = "body";
		WebElement row = driver.findElement(By.cssSelector(element));
		System.out.println(row.getText());

		//#detailArea
		//#gib_frame
		//body > div > table > tbody > tr > td > div > div > div.jkGEN_content
		//body > div > table > tbody > tr > td > div > div > div.jkGEN_content > div:nth-child(1) > table > tbody > tr:nth-child(1) > td.jkGEN_th.jkGEN_th01
		

		//String element ="body > div > table > tbody > tr > td > div > div > div.jkGEN_content > div:nth-child(1)";
		//String element ="body > div > table > tbody > tr > td > div > div > div.jkGEN_content > div:nth-child(1) > table > tbody";
		//String element = "body > div > table > tbody > tr > td > div > div > div.jkGEN_content > div:nth-child(1) > table > tbody > tr";
		//                  body > div > table > tbody > tr > td > div > div.jkGEN_recruit > div.jkGEN_sec.jkGEN_req > div > table > tbody > tr
		//                  body > div > table > tbody > tr > td > div > div.jkGEN_recruit > div.jkGEN_sec.jkGEN_req > div > table.jkGEN_tbl > tbody > tr 
		                  //<table class="jkGEN_tbl" cellspacing="0" cellpadding="0"><caption><span class="jkGEN_skip">모집분야 및 자격요건</span></caption><thead> <tr> <th class="jkGEN_fst" style="width: 19.91%;" scope="col">모집분야</th> <th style="width: 19.91%;" scope="col">담당업무</th> <th style="width: 39.83%;" scope="col">자격요건</th> <th style="width: 20.34%;" scope="col">모집인원</th> </tr> </thead> <tbody> <tr> <td class="jkGEN_fst jkGEN_center" style="text-align: center;">빅데이터<br>분석 및 모델링<br>전문가</td> <td>- Project 수행<br><span style="color: rgb(255, 255, 255);">- </span>(Project PM, PL)</td> <td>- 학력 : 대졸이상<br>- 경력 : 경력2년↑<br><br>［우대사항］<br>- 석사학위 수여자, 박사학위 수여자<br>- 프로젝트 경험 PM, PL 우대<br>- SAS, R, Python 활용능력 필수</td> <td class="jkGEN_center">0명</td> </tr> </tbody> </table>
//		List<WebElement> rows = driver.findElements(By.cssSelector(element));
//
//		for (WebElement tr : rows) {
//			try {
//				Thread.sleep(500);
//			String line="";
//			line += tr.getText();
//			//line += quote+tr.findElement(By.cssSelector("span.corpName > a")).getText()+ quote+delimiter;
//			System.out.println(line);
//			
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				System.out.println("element 읽다가 오류 ");
//				e.printStackTrace();
//			}
//		}
		driver.switchTo().parentFrame();
		//String element1 = "#tab02 > div > article.artReadPeriod > div > dl.date > dd:nth-child(2) > span";
		String element1 = "article.artReadPeriod";
		WebElement row1 = driver.findElement(By.cssSelector(element1));
		System.out.println(row1.getText());
//		
//		String element2 = "#tab02 > div > article.artReadPeriod > div > dl.date > dd:nth-child(4) > span";
//		WebElement row2 = driver.findElement(By.cssSelector(element2));
//		System.out.println(row2.getText());
		
		//String element3 = "#tab03 > article.artReadCoInfo.divReadBx > div > div.tbCol.coInfo > dl";
		String element3 = "article.artReadCoInfo.divReadBx";
		WebElement row3 = driver.findElement(By.cssSelector(element3));
		System.out.println(row3.getText());

		System.out.println("프로그램을 종료합니다");
	}
}