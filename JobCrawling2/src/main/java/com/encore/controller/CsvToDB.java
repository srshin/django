package com.encore.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.encore.model.CorpDAO;
import com.encore.model.CorpVO;

public class CsvToDB {
	static String dirName = "csvFiles";
	static String fileName = "jobKorea.csv";
	static String today = "2019-02-16";
	static int columeNum = 8;

	public static void main(String[] args) {
		CorpDAO dao = new CorpDAO();
		String path = dirName + "\\" + today + "_" + fileName;
		List<CorpVO> list = new ArrayList<CorpVO>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));) {

			String line;
			
			br.readLine(); // 컬럼 제목 부분 버리기
			while (true) {
				line = br.readLine();
				if (line == null)	break;
				System.out.println(line);
				String[] data = line.split(",", columeNum);
				if (data.length <columeNum ) continue; 
				CorpVO corp = new CorpVO(Integer.parseInt(data[0]), data[1], data[2], 
						data[3], data[4], data[5], data[6], data[7]);
				list.add(corp);
				System.out.println(corp);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		dao.insertAll(list);
	}

}