package selenium;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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

public class CrawlerDetailSelenium {

	public static void main(String[] args) throws InterruptedException, IOException {
		String driverLocation = "D:\\BigData\\Download\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", driverLocation);
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		String today = mSimpleDateFormat.format(new Date());
		BufferedWriter bw = openFile(today);
		WebDriver driver = new ChromeDriver();
		locateURL(driver, bw);
		//driver.quit();
		System.out.println("프로그램을 종료합니다");
		bw.close();

	}

	public static BufferedWriter openFile(String mTime) {
		String fileName = "jobKoreaDetail.csv";
		String dirName = "csvDetailFiles";
		String header = "idDetail, idNum, corpName, jobInfoHref, jobPeriod,corpDetail,jobInfoDetail";
		File folder = new File(dirName);
		BufferedWriter bw = null;

		if (!folder.exists()) {
			folder.mkdirs();
		}
		String fileFullName = dirName + File.separator + mTime + "_" + fileName;
		File file = new File(fileFullName);
		try {
			if (file.exists()) {
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
			} else {
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
				System.out.println(header);
				bw.write(header);
				bw.newLine();
			}
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bw;
	}

	public static void locateURL(WebDriver driver, BufferedWriter bw) {
		String[][] tagList = { { "#container > h1 > span", "corpName" }, { "article.artReadPeriod", "jobPeriod" },
				{ "article.artReadCoInfo.divReadBx", "corpDetail" }, { "gib_frame" }, { "body", "jobInfoDetail" } };
		String replceReg = "\\(|\\)|\\{|\\}|\\[|\\]|,|\"";
		String newlineReg = "\r\n|\r|\n|\n\r";
		CorpDAO dao = new CorpDAO();

		Entry<String, String> columnsSearch = new AbstractMap.SimpleEntry<String, String>("idNum", "jobInfoHref");
		Entry<String, String> columnsDetail = new AbstractMap.SimpleEntry<String, String>("idDetail", "jobInfoHref");
		List<Entry<String, String>> listSearch = dao.selectByColumns(columnsSearch);
		Map<String, String> listDetail = dao.selectByColumnsDetail(columnsDetail);

		// for (Entry<String, String>entry : listSearch) {
		// System.out.println(entry.getKey() + "="+ entry.getValue());
		// };

		int lineCount = 1;
		int currentLineDetail = dao.selectCurrentLine("idDetail")+1;
		System.out.println("currentLine:"+currentLineDetail);
		System.out.println(listDetail);
		System.out.println(listSearch);
		for (Entry<String, String> entrySearch : listSearch) {
			String idNum = entrySearch.getKey();
			String jobInfoHref = entrySearch.getValue();
			System.out.println(lineCount+"개째 페이지입니다");

			if (listDetail.values().contains(jobInfoHref)) {
				for (Entry<String, String> detailEntry : listDetail.entrySet()) {
					if (detailEntry.getValue().equals(jobInfoHref)) {
						String existIdDetail = detailEntry.getKey();
						System.out.println("중복 발견 " + idNum + ":  " + jobInfoHref + "detailId:  " + existIdDetail);
						dao.updateColumn(idNum, "idDetail", existIdDetail);
						break;
					}
				}
				continue;
			}
			if (lineCount++ == 50) {
				//driver.quit();
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("프로그램을 종료합니다");
				System.exit(0);
			}
			try {
				Thread.sleep(50000);

				String idDetail = String.valueOf(currentLineDetail);
				String corpName= null;
				String jobPeriod= null;
				String corpDetail = null;
				String jobInfoDetail = null; 

				driver.get(jobInfoHref);

				for (String[] tag : tagList) {
					if (tag[0].equals("gib_frame")) {
						driver.switchTo().frame("gib_frame");
						// driver.switchTo().parentFrame();
						continue;
					}
					WebElement row = driver.findElement(By.cssSelector(tag[0]));
					String text = row.getText().replaceAll(replceReg, " ").trim();
					text = text.replaceAll(newlineReg, " ").trim();
					text = text.replaceAll("\\s\\s+", " ");
					if (text.equals(""))
						text = "내용없음";
					switch (tag[1]) {
					case "corpName":
						corpName=text;
						break;
					case "jobPeriod":
						jobPeriod=text;
						break;
					case "corpDetail":
						corpDetail=text;
						break;
					case "jobInfoDetail":
						jobInfoDetail=text;
						break;
					default:
						System.out.println("error!");
					}
				}

				dao.updateColumn(idNum, "idDetail", idDetail);
				DetailVO vo = new DetailVO(idDetail, idNum, corpName, jobInfoHref, jobPeriod, corpDetail, jobInfoDetail);
				currentLineDetail++;
				dao.insertDetail(vo);
				bw.write(vo.toline());
				bw.newLine();
				bw.flush();
				System.out.println(vo);
				Thread.sleep(20000);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (org.openqa.selenium.UnhandledAlertException| org.openqa.selenium.NoSuchElementException e) {
				e.printStackTrace();
				dao.updateColumn(idNum, "idDetail", "0");
				System.out.println("해당 element가 없어서 에러입니다.계속 진행합니다");
			}
		}

	}

}