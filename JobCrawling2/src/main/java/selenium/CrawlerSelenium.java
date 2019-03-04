package selenium;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CrawlerSelenium {
	static String header = "idNum,regDate,corpName,corpHref,jobInfo,jobInfoHref,jobKeyword,jobSpec,idDetail";
	static String dirName = "csvFiles";
	static String fileName = "jobKorea.csv";

	public static void main(String[] args) throws InterruptedException, IOException {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		String today = mSimpleDateFormat.format(new Date());
		WebDriver driver = locateURL();
		BufferedWriter bw = openFile(today);
		makeList(driver, bw, today);
		driver.quit();
		int columeNum = header.split(",").length;
		System.out.println("column 수는 " + columeNum);
		System.out.println("프로그램을 종료합니다");
		bw.close();
	}

	public static WebDriver locateURL() {
		String url = "https://www.jobkorea.co.kr/";
		String driverLocation = "D:\\BigData\\Download\\chromedriver.exe";
		String keyword = "빅데이터";

		System.setProperty("webdriver.chrome.driver", driverLocation);
		WebDriver driver = new ChromeDriver();
		driver.get(url);
		try {
			Thread.sleep(2000);
			driver.findElement(By.cssSelector("p.btnBx > button:nth-child(1)")).click();
			WebElement searchBox = driver.findElement(By.name("stext"));
			searchBox.sendKeys(keyword);
			searchBox.submit();
			Thread.sleep(1000); // Let the user actually see something!
			String sort = "#smGiList > div.sort > dl > dd.lyWrap.type.tsLayerContainer > button";
			driver.findElement(By.cssSelector(sort)).click();
			Thread.sleep(500);
			String sort2 = "#smGiList > div.sort > dl > dd.lyWrap.type.tsLayerContainer > ul.list.devOrderList.tsLayerMenu > li > button[data-value='2']";
			driver.findElement(By.cssSelector(sort2)).click();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Let the user actually see something!
		return driver;

	}

	public static BufferedWriter openFile(String mTime) {
		BufferedWriter bw = null;
		File folder = new File(dirName);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(dirName + File.separator + mTime + "_" + fileName, true), "UTF-8"));
			System.out.println(header);
			bw.write(header);
			bw.newLine();
			bw.flush();
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

	public static void makeList(WebDriver driver, BufferedWriter bw, String mTime) {
		String delimiter = ",";
		String replceReg = "\\(|\\)|\\{|\\}|\\[|\\]|,|\"";
		String newlineReg = "\r\n|\r|\n|\n\r";
		// String lineSep = System.getProperty("line.separator");
		String[] tagList = { "span.corpName > a", "span.jobInfo > a", "span.detailInfo > p.gibInfo > a",
				"span.detailInfo > p.gibDesc > a" };

		String nextUrl = "";
		int currentPage = 2;
		int currentLine = 1;
		int itemTotal = Integer.valueOf(driver.findElement(By.cssSelector("#smGiList > h4 > span > em")).getText());
		int itemCount = 0;

		while (true) {
			int page = Integer.parseInt(
					driver.findElement(By.cssSelector("#smGiList > div.tplPagination  span[class='now']")).getText());
			System.out.println("page: " + page);
			List<WebElement> rows = driver.findElements(By.cssSelector("#smGiList > div.list > ul > li"));
			// System.out.println(rows);
			System.out.println(rows.size());
			for (WebElement tr : rows) {
				String line = "";
				line += currentLine++ + delimiter;
				line += mTime + delimiter;
				try {
					for (String tag : tagList) {
						Thread.sleep(100);
						String name = tr.findElement(By.cssSelector(tag)).getText();
						name = name.replaceAll(replceReg, " ").trim();
						line += name + delimiter;
						if ((tag.equals(tagList[0])) || tag.equals(tagList[1]))
							line += tr.findElement(By.cssSelector(tag)).getAttribute("href") + delimiter;
						// Thread.sleep(100);
						line = line.replaceAll(newlineReg, " ").trim();
					}
					line +="no";
					System.out.println(line);
					bw.write(line);
					bw.newLine();
					bw.flush();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("element 읽다가 오류 ");
					e.printStackTrace();

				}
			}
			itemCount += rows.size();
			System.out.println("currentPage: " + currentPage);
			System.out.println("itemCount: " + itemCount + " of  " + itemTotal);
			if (itemCount >= itemTotal)
				break;
			if (currentPage == 11) {
				currentPage = 2;
				if (page < 11) {
					nextUrl = "#smGiList > div.tplPagination > p > a";
				} else {
					nextUrl = "#smGiList > div.tplPagination > p:nth-child(3) > a";
				}
			} else {
				nextUrl = "#smGiList > div.tplPagination > ul > li:nth-child(" + currentPage + ") > a";
				currentPage++;
			}
			System.out.println(nextUrl);
			driver.findElement(By.cssSelector(nextUrl)).click();
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}