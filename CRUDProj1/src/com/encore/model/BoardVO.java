package com.encore.model;

import java.sql.Date;

public class BoardVO {

	private int bnum;
	private String title;
	private String contents;
	private String writer;
	private Date regdate;
	private int count;
	public BoardVO() {
		
	}
	public BoardVO(int bnum, String title, String contents, String writer, Date regdate, int count) {
		super();
		this.bnum = bnum;
		this.title = title;
		this.contents = contents;
		this.writer = writer;
		this.regdate = regdate;
		this.count = count;
	}

	public int getBnum() {
		return bnum;
	}

	public void setBnum(int bnum) {
		this.bnum = bnum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BoardVO [bnum=").append(bnum).append(", title=").append(title).append(", contents=")
				.append(contents).append(", writer=").append(writer).append(", regdate=").append(regdate)
				.append(", count=").append(count).append("]");
		return builder.toString();
	}
	
	
}
