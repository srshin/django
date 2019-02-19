package com.encore.controller;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.encore.model.CorpDAO;
import com.encore.model.DetailVO;

public class CrawlerDetail {

	public static void main(String[] args) throws InterruptedException, IOException {
		String driverLocation = "D:\\BigData\\Download\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", driverLocation);
		WebDriver driver = new ChromeDriver();
		locateURL(driver);
		driver.quit();
		System.out.println("프로그램을 종료합니다");

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
	public static void locateURL(WebDriver driver) {
		String[][] tagList = { {"#container > h1 > span", "corpName"}, 
				{"article.artReadPeriod","jobPeriod"}, 
				{"article.artReadCoInfo.divReadBx","corpDetail"},
				{"gib_frame"}, 
				{"body", "jobInfoDetail"} 
				};
		String replceReg = "\\(|\\)|\\{|\\}|\\[|\\]|,|\"";
		String newlineReg = "\r\n|\r|\n|\n\r";


		int currentLine = 1;
		Entry<String, String> columns= new AbstractMap.SimpleEntry<String, String>("idNum","jobInfoHref");
		Entry<String, String> columnsDetail= new AbstractMap.SimpleEntry<String, String>("idDetail","jobInfoHref");
		CorpDAO dao = new CorpDAO();
		List<Entry<String, String>>  listCorp = dao.selectByColumns(columns);
//		for (Entry<String, String>entry : listCorp) { 
//			System.out.println(entry.getKey() + "="+ entry.getValue());
//		};
		Map<String, String>  listDetail = dao.selectByColumnsDetail(columnsDetail);
		currentLine = listDetail.size()+1;
		System.out.println(listDetail);
		for (Entry<String,String> entry  : listCorp) {
			if (listDetail.values().contains(entry.getValue())) {
				System.out.println("중복 발견 "+ entry.getKey() +":  " + entry.getValue());
				continue; 
			}
			if (currentLine == 10) {
				driver.quit();
				System.out.println("프로그램을 종료합니다");
				System.exit(0);

			}
			DetailVO vo = new DetailVO();
			vo.setIdDetail(String.valueOf(currentLine++));
			vo.setIdNum(entry.getKey());
			vo.setJobInfoHref(entry.getValue());
			driver.get(entry.getValue());
				for (String[] tag : tagList) {
					if (tag[0].equals("gib_frame")) {
						driver.switchTo().frame("gib_frame");
						// driver.switchTo().parentFrame();
						continue;
					}
					WebElement row = driver.findElement(By.cssSelector(tag[0]));

					String text = row.getText().replaceAll(replceReg, " ").trim();
					text = text.replaceAll(newlineReg, " ").trim();

					switch(tag[1]) {
					case "corpName": vo.setCorpName(text);break;
					case "jobPeriod": vo.setJobPeriod(text);break;
					case "corpDetail" : vo.setCorpDetail(text);break;
					case "jobInfoDetail": vo.setJobInfoDetail(text);break;
					default: System.out.println("error!");
					}
				}
				System.out.println(vo);
				dao.insertDetail(vo);
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // Let the user actually see something!
		}			

	}

}