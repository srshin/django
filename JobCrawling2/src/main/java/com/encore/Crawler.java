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

public class Crawler {

	public static void main(String[] args) throws InterruptedException, IOException {
		System.setProperty("webdriver.chrome.driver", "D:\\BigData\\Download\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		/*
		 * driver.get("http://www.google.com"); WebElement element =
		 * driver.findElement(By.name("q"));
		 * 
		 * element.sendKeys("terminator\n"); new WebDriverWait(driver, 10).until(new
		 * Function<WebDriver, Object>() { public Object apply(WebDriver d) { return
		 * d.getTitle().toLowerCase().startsWith("terminator"); } });
		 * System.out.println("Title: " + driver.getTitle());
		 * 
		 * 
		 * // Close driver driver.quit();
		 */
		driver.get("https://www.jobkorea.co.kr/");

		Thread.sleep(2000); // Let the user actually see something!
		driver.findElement(By.cssSelector("p.btnBx > button:nth-child(1)")).click();
		WebElement searchBox = driver.findElement(By.name("stext"));
		searchBox.sendKeys("빅데이터");
		searchBox.submit();
		Thread.sleep(1000); // Let the user actually see something!
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
		String mTime = mSimpleDateFormat.format ( new Date () );
		String sort ="#smGiList > div.sort > dl > dd.lyWrap.type.tsLayerContainer > button";
		driver.findElement(By.cssSelector(sort)).click();
		String sort2="#smGiList > div.sort > dl > dd.lyWrap.type.tsLayerContainer > ul.list.devOrderList.tsLayerMenu > li > button[data-value='2']";
		driver.findElement(By.cssSelector(sort2)).click();
		

		int i=2; 
		int j=1;
		String itemTotalString = driver.findElement(By.cssSelector("#smGiList > h4 > span > em")).getText();
		int itemTotal = Integer.valueOf(itemTotalString);
		int itemCount = 0;
		String nextUrl = "";
		String quote = "\"";
		String delimiter = ",";
		String header = "date,corpName,corpHref,jobInfo,jobInfoHref,jobKeyword,jobSpec";
		String fileName = "jobKorea.csv";
		BufferedWriter bw = null;
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
		while (true) {
			int page = Integer.parseInt(driver.findElement(By.cssSelector("#smGiList > div.tplPagination  span[class='now']")).getText());
			System.out.println("page: "+ page);
			List<WebElement> rows = driver.findElements(By.cssSelector("#smGiList > div.list > ul > li"));
			//System.out.println(rows);
			System.out.println(rows.size());
			for (WebElement tr : rows) {
				try {
					Thread.sleep(500);
				String line="";
				line += quote+mTime+ quote+delimiter;
				line += quote+tr.findElement(By.cssSelector("span.corpName > a")).getText()+ quote+delimiter;
				line += quote+tr.findElement(By.cssSelector("span.corpName > a")).getAttribute("href")+ quote+delimiter;
				line += quote+tr.findElement(By.cssSelector("span.jobInfo > a")).getText()+ quote+delimiter ;
				//Thread.sleep(100);
				line += quote+tr.findElement(By.cssSelector("span.jobInfo > a")).getAttribute("href")+ quote+delimiter;
				line += quote+tr.findElement(By.cssSelector("span.detailInfo > p.gibInfo > a")).getText()+ quote+delimiter;
				//Thread.sleep(100);
				line += quote+tr.findElement(By.cssSelector("span.detailInfo > p.gibDesc > a")).getText()+ quote;
				//Thread.sleep(100);
				System.out.print(j++ +" :");
				System.out.println(line);
				
				bw.write(line); 
				bw.newLine();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("element 읽다가 오류 ");
					e.printStackTrace();
				}
			}
		
			itemCount += rows.size();
			System.out.println("i: "+ i);
			System.out.println("itemCount: "+itemCount+" of  "+ itemTotal);
			if (itemCount >= itemTotal)break;
			if (i ==11) {
				i=2;
				if(page <11) {
					nextUrl = "#smGiList > div.tplPagination > p > a";
				}
				else  {
					nextUrl = "#smGiList > div.tplPagination > p:nth-child(3) > a";	
				}
			}
			else {
				nextUrl = "#smGiList > div.tplPagination > ul > li:nth-child("+ i + ") > a";
				i++;
			}
			System.out.println(nextUrl);
			driver.findElement(By.cssSelector(nextUrl)).click();
			Thread.sleep(4000);
			
		}
		//driver.quit();
		System.out.println("프로그램을 종료합니다");
		bw.close(); 
	}
}