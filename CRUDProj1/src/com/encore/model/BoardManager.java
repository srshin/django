package com.encore.model;

import java.sql.Date;
import java.util.List;
import java.util.Vector;
public class BoardManager {
	public List<BoardVO> getBoardList() {
		Vector<BoardVO> data = new Vector<>();
		for(int i=1; i<=10; i++) {
		  data.add(new BoardVO(i, "제목"+i, "내용"+i, "작성자"+i, 		
				new Date(new java.util.Date().getTime()), 0));
		}
		return data;		
	}
}




