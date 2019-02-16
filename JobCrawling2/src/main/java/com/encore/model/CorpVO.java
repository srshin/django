package com.encore.model;

public class CorpVO {
	public int idNum;
	public String regDate;
	public String corpName;
	public String corpHref;
	public String jobInfo;
	public String jobInfoHref;
	public String jobKeyword;
	public String jobSpec;
	
	
	
	public CorpVO() {
		super();
		// TODO Auto-generated constructor stub
	}



	public CorpVO(int idNum, String regDate, String corpName, String corpHref, String jobInfo, String jobInfoHref, String jobKeyword,
			String jobSpec) {
		super();
		this.idNum = idNum; 
		this.regDate = regDate;
		this.corpName = corpName;
		this.corpHref = corpHref;
		this.jobInfo = jobInfo;
		this.jobInfoHref = jobInfoHref;
		this.jobKeyword = jobKeyword;
		this.jobSpec = jobSpec;
	}



	public int getIdNum() {
		return idNum;
	}

	public String getRegDate() {
		return regDate;
	}



	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}



	public String getCorpName() {
		return corpName;
	}



	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}



	public String getCorpHref() {
		return corpHref;
	}



	public void setCorpHref(String corpHref) {
		this.corpHref = corpHref;
	}



	public String getJobInfo() {
		return jobInfo;
	}



	public void setJobInfo(String jobInfo) {
		this.jobInfo = jobInfo;
	}



	public String getJobInfoHref() {
		return jobInfoHref;
	}



	public void setJobInfoHref(String jobInfoHref) {
		this.jobInfoHref = jobInfoHref;
	}



	public String getJobKeyword() {
		return jobKeyword;
	}



	public void setJobKeyword(String jobKeyword) {
		this.jobKeyword = jobKeyword;
	}



	public String getJobSpec() {
		return jobSpec;
	}



	public void setJobSpec(String jobSpec) {
		this.jobSpec = jobSpec;
	}



	@Override
	public String toString() {
		return "CorpVO [idNum="+idNum + "regDate=" + regDate + ", corpName=" + corpName + ", corpHref=" + corpHref + ", jobInfo="
				+ jobInfo + ", jobInfoHref=" + jobInfoHref + ", jobKeyword=" + jobKeyword + ", jobSpec=" + jobSpec
				+ "]";
	}
	
	
	
	
}








