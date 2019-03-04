package com.encore.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Crawler {

	public static void main(String[] args) throws InterruptedException, IOException {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		String today = mSimpleDateFormat.format(new Date());
		BufferedWriter bw = openFile(today);
		makeList(bw, today);
		System.out.println("프로그램을 종료합니다");
		bw.close();
	}

	public static BufferedWriter openFile(String mTime) {
		String header = "idNum,regDate,corpName,corpHref,jobInfo,jobInfoHref,jobKeyword,jobSpec,idDetail";
		String dirName = "csvFiles";
		String fileName = "jobKorea.csv";
		BufferedWriter bw = null;
		File folder = new File(dirName);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(dirName + File.separator + mTime + "_" + fileName, false), "UTF-8"));
			System.out.println(header);
			bw.write(header);
			bw.newLine();
			bw.flush();
	
		} catch ( IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.out.println("파일입출력 에러 발생으로 프로그램을 종료합니다");
			if (bw !=null)
				try {
					bw.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			System.exit(0);
		}
		return bw;
	}

	public static void makeList(BufferedWriter bw, String mTime) {
		String delimiter = ",";
		String replceReg = "\\(|\\)|\\{|\\}|\\[|\\]|,|\"";
		String newlineReg = "\r\n|\r|\n|\n\r";
		// String lineSep = System.getProperty("line.separator");
		String site = "http://www.jobkorea.co.kr";
		Document doc = null;
		String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36";
		String referer = "http://www.jobkorea.co.kr/Search/?stext=%EB%B9%85%EB%8D%B0%EC%9D%B4%ED%83%80";
		String url = "http://www.jobkorea.co.kr/Search/AjaxSearchList";
		String[] tagList = { "span.corpName > a", "span.jobInfo > a", "span.detailInfo > p.gibInfo > a",
				"span.detailInfo > p.gibDesc > a" };
		String nextPage="1";
		int currentLine = 1;
		while (true) {
			try {
				TimeUnit.SECONDS.sleep(3);
				doc = Jsoup.connect(url).header("Accept-Encoding", "gzip, deflate")
						.header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,la;q=0.6")
						.header("Connection", "keep-alive").header("Content-Length", "120")
						.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
						.header("Host", "www.jobkorea.co.kr").header("Origin", "http://www.jobkorea.co.kr")
						.header("Referer", referer).userAgent(userAgent).data("pageType", "GI").data("stext", "빅데이타")
						.data("pageSize", "60").data("ord", "2").data("IsCoInfoSC", "false").data("IsRecruit", "false")
						.data("page", nextPage).data("Order", "1").method(Connection.Method.POST).post();
				nextPage = Integer.toString(Integer.parseInt(nextPage)+1);
			} catch (IOException | InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//System.out.println(doc.toString());

			int itemTotal = Integer.valueOf(doc.select("#smGiList > h4 > span > em").text());
			System.out.println("itemTotal: " + itemTotal);
			List<Element> rows = doc.select("#smGiList > div.list > ul > li");
			//System.out.println("rows" + rows);
			System.out.println(rows.size());
			if (rows.size()==0) break; 
			for (Element tr : rows) {
				System.out.println("tr : " + tr);
				String line = "";
				line += currentLine++ + delimiter;
				line += mTime + delimiter;
				try {
					for (String tag : tagList) {
						line += tr.select(tag).text().replaceAll(replceReg, " ").trim() + delimiter;
						if ((tag.equals(tagList[0])) || tag.equals(tagList[1])) {
							String ref = tr.select(tag).attr("href");
							if (ref.startsWith("http:"))
								line += ref + delimiter;
							else 
								line += site + ref + delimiter;
						}
					}
					line += "no";
					line = line.replaceAll(newlineReg, " ").trim();
					System.out.println(line);
					bw.write(line);
					bw.newLine();
					bw.flush();
					
				} catch (Exception e) {
					System.out.println("element 읽다가 오류 ");
					e.printStackTrace();
				}
			}
		}
	}
}