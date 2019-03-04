package com.encore.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.encore.model.CorpDAO;
import com.encore.model.CorpVO;

public class CompareSearchList {
	static String dirName = "csvFiles";
	static String filePostfix = "jobKorea.csv";

	public static void main(String[] args) {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		String today = mSimpleDateFormat.format(new Date());
		String file1 = "2019-02-25";
		String file2 = "2019-02-26";
		List<CorpVO>  list1 = readFile(file1);
		List<CorpVO>  list2 = readFile(file2);
		int count=0;
		for (CorpVO vo1 : list1) {
			for (CorpVO vo2: list2 ) {
				if (vo1.getJobInfoHref().equals(vo2.getJobInfoHref())) {
					System.out.println("count : " + count++ + vo1.getCorpName());
					break;
				}
			}
		}
	}

	public static List<CorpVO> readFile(String fileName) {
		CorpDAO dao = new CorpDAO();

		String path = dirName + "\\" + fileName + "_" + filePostfix;

		List<CorpVO> list = new ArrayList<CorpVO>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));) {

			String line;

			// 컬럼 제목 읽어서 갯수 계산
			int columeNum = br.readLine().split(",").length;

			while (true) {
				line = br.readLine();
				if (line == null)
					break;
				//System.out.println(line);
				String[] data = line.split(",", columeNum);
				for (String str : data)
					if (str == null)
						str = " ";
				if (data.length < columeNum)
					continue;
				CorpVO corp = new CorpVO(data);
				list.add(corp);
				//System.out.println(corp);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return list; 
	}

}