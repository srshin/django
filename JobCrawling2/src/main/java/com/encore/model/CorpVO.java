package com.encore.model;

public class CorpVO {
	public String idNum;
	public String regDate;
	public String corpName;
	public String corpHref;
	public String jobInfo;
	public String jobInfoHref;
	public String jobKeyword;
	public String jobSpec;
	public String idDetail;
	
	
	public CorpVO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CorpVO(String[] data) {
		super();
		
		this.idNum = data[0];
		this.regDate = data[1];
		this.corpName = data[2];
		this.corpHref = data[3];
		this.jobInfo = data[4];
		this.jobInfoHref = data[5];
		this.jobKeyword = data[6];
		this.jobSpec = data[7];
		if (data.length > 8 )
			this.idDetail = data[8];
		else 
			this.idDetail = "no";

	}


	public String getIdNum() {
		return idNum;
	}


	public void setIdNum(String idNum) {
		this.idNum = idNum;
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


	public String getIdDetail() {
		return idDetail;
	}


	public void setIdDetail(String idDetail) {
		this.idDetail = idDetail;
	}


	@Override
	public String toString() {
		return "CorpVO [idNum=" + idNum + ", regDate=" + regDate + ", corpName=" + corpName + ", corpHref=" + corpHref
				+ ", jobInfo=" + jobInfo + ", jobInfoHref=" + jobInfoHref + ", jobKeyword=" + jobKeyword + ", jobSpec="
				+ jobSpec + ", idDetail=" + idDetail + "]";
	}

	
}








