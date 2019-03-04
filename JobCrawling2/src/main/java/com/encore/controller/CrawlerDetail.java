package com.encore.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.encore.model.CorpDAO;
import com.encore.model.DetailVO;

public class CrawlerDetail {

	public static void main(String[] args) throws InterruptedException, IOException {
		locateURL();
		System.out.println("프로그램을 종료합니다");
	}

	public static String getDigit(String url) {
		String digits = null;
		Pattern pattern = Pattern.compile("([\\d]{5,})");
		Matcher m = pattern.matcher(url);
		if (m.find())
			digits = m.group();
		return digits;
	}

	public static String getFilePath(String id, String fileNum) {
		String filePath = null;
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		// String today = mSimpleDateFormat.format(new Date());
		String today = "2019-02-25";

		String fileName = "detail.csv";
		String dirName = "csvDetailFiles";
		String dirPath = dirName + File.separator + today;
		File folder = new File(dirPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		filePath = dirPath + File.separator + id + "_" + fileNum + "_" + fileName;
		return filePath;

	}

	public static BufferedWriter openFile(String fileFullName) {
		BufferedWriter bw = null;

		File file = new File(fileFullName);
		System.out.println(fileFullName);
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("파일 오류로 프로그램을 종료합니다");
			System.exit(0);
		}
		return bw;
	}

	public static Response getIframe(String jobInfoHref, String url, Map<String, String> cookies) {
		Response response = null;

		try {
			response = Jsoup.connect("http://www.jobkorea.co.kr"+url)
					.header("Accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
					.header("Accept-Encoding", "gzip, deflate")
					.header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4").header("Connection", "keep-alive")
					.cookies(cookies).header("Host", "www.jobkorea.co.kr").header("Referer", jobInfoHref)
					.header("Upgrade-Insecure-Requests", "1")
					.userAgent(
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
					.method(Connection.Method.GET)
					.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (response.statusCode() != 200) {
			System.out.println("response error로 프로그램 종료됩니다");
			System.exit(0);
		}
		return response;
	}

	public static void locateURL() {
		String replceReg = "\\(|\\)|\\{|\\}|\\[|\\]|,|\"";
		String newlineReg = "\r\n|\r|\n|\n\r";

		CorpDAO dao = new CorpDAO();
		int count = 0;

		Entry<String, String> columnsSearch = new AbstractMap.SimpleEntry<String, String>("idNum", "jobInfoHref");
		Entry<String, String> columnsDetail = new AbstractMap.SimpleEntry<String, String>("idDetail", "jobInfoHref");
		List<Entry<String, String>> listSearch = dao.selectByColumns(columnsSearch);
		Map<String, String> listDetail = dao.selectByColumnsDetail(columnsDetail);

		// for (Entry<String, String>entry : listSearch) {
		// System.out.println(entry.getKey() + "="+ entry.getValue());
		// };

		int idDetailNum = dao.selectCurrentLine("idDetail") + 1;
		System.out.println("idDetail:" + idDetailNum);
		// System.out.println(listDetail);
		// System.out.println(listSearch);
		for (Entry<String, String> entrySearch : listSearch) {
			String idNum = entrySearch.getKey();
			String jobInfoHref = entrySearch.getValue();
			System.out.println("idNum: " + idNum + " jobInfoHref : " + jobInfoHref);

			try {
				if (listDetail.values().contains(jobInfoHref)) {
					for (Entry<String, String> detailEntry : listDetail.entrySet()) {
						if (detailEntry.getValue().equals(jobInfoHref)) {
							String existIdDetail = detailEntry.getKey();
							System.out.println("중복 발견 " + idNum + ":  " + jobInfoHref + "detailId:  " + existIdDetail);
							//dao.updateColumn(idNum, "idDetail", existIdDetail);
							break;
						}
					}
					continue;
				}

				Response response = Jsoup.connect(jobInfoHref).method(Connection.Method.GET).execute();
				if (response.statusCode() != 200) {
					System.out.println("response error로 프로그램 종료됩니다");
					System.exit(0);
				}
				Document doc = response.parse();
				String idDetail = Integer.toString(idDetailNum++);
				String ref = getDigit(jobInfoHref);
				String filePath = getFilePath(idDetail, ref);
				if (jobInfoHref.contains("www.jobkorea.co.kr")) {
					String corpName = doc.select("#container > h1 > span").text().replaceAll(newlineReg, " ")
							.replaceAll("\\s\\s+", " ").replaceAll(newlineReg, " ").trim();
					String jobPeriod = doc.select("article.artReadPeriod").text().replaceAll(newlineReg, " ")
							.replaceAll("\\s\\s+", " ").replaceAll(newlineReg, " ").trim();
					String corpDetail = doc.select("article.artReadCoInfo.divReadBx").text().replaceAll(newlineReg, " ")
							.replaceAll("\\s\\s+", " ").replaceAll(newlineReg, " ").trim();
//					<iframe src="http://www.work.go.kr/outOffer/empInfo/empDetailView.do?wantedAuthNo=K170051902140035" 
//							name="gib_frame" id="gib_frame" width="670" height="450" noresize="" 
//							scrolling="yes" frameborder="0" marginheight="0" marginwidth="0" title="상세요강"></iframe>
					String iframeSrc = doc.selectFirst("#gib_frame").attr("src");
					System.out.println("framesrc : " +iframeSrc);

					Map<String, String> cookies = response.cookies();
					response = getIframe(jobInfoHref, iframeSrc, cookies);
					//doc = response.parse();
					DetailVO vo = new DetailVO(idDetail, idNum, jobInfoHref, corpName, jobPeriod, corpDetail, filePath);
					//dao.insertDetail(vo);
				} else {
					DetailVO vo = new DetailVO(idDetail, idNum, jobInfoHref, "회사이름", "기간정보", "회사정보", filePath);
					//dao.insertDetail(vo);
				}
				System.out.println(doc.text());
				BufferedWriter bw = openFile(filePath);
				bw.write(doc.text());
				bw.flush();
				bw.close();
				//dao.updateColumn(idNum, "idDetail", idDetail);
				TimeUnit.SECONDS.sleep(30);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("에러 발생하여 프로그램 종료합니다. ");
				System.exit(0);
			}
			if (count++ == 5)
				break;
		}
	}
}