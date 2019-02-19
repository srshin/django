package com.encore.model;
//
//create table detailList(
//		 idDetail varchar2(200),
//		 idNum varchar2(200),
//		 corpName varchar2(100) ,
//		 jobInfoHref  varchar2(500),
//		 jobPeriod varchar2(1000), 
//		 corpDetail varchar2(4000), 
//		 jobInfoDetail   varchar2(4000)
//		 );
public class DetailVO {
	public String idDetail;
	public String idNum;
	public String corpName;
	public String jobInfoHref;
	public String jobPeriod;
	public String corpDetail;
	public String jobInfoDetail;
	
	
	public DetailVO(String[] data) {
		super();
		this.idDetail = data[0];
		this.idNum = data[1];
		this.corpName = data[2];
		this.jobInfoHref = data[3];
		this.jobPeriod = data[4];
		this.corpDetail = data[5];
		this.jobInfoDetail = data[6];
	}
	public DetailVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getIdDetail() {
		return idDetail;
	}
	public void setIdDetail(String idDetail) {
		this.idDetail = idDetail;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public String getJobInfoHref() {
		return jobInfoHref;
	}
	public void setJobInfoHref(String jobInfoHref) {
		this.jobInfoHref = jobInfoHref;
	}
	public String getJobPeriod() {
		return jobPeriod;
	}
	public void setJobPeriod(String jobPeriod) {
		this.jobPeriod = jobPeriod;
	}
	public String getCorpDetail() {
		return corpDetail;
	}
	public void setCorpDetail(String corpDetail) {
		this.corpDetail = corpDetail;
	}
	public String getJobInfoDetail() {
		return jobInfoDetail;
	}
	public void setJobInfoDetail(String jobInfoDetail) {
		this.jobInfoDetail = jobInfoDetail;
	}
	@Override
	public String toString() {
		return "DetailVO \nidDetail=" + idDetail + ", \nidNum=" + idNum + ", \ncorpName=" + corpName + ", \njobInfoHref="
				+ jobInfoHref + ",\njobPeriod=" + jobPeriod + ", \ncorpDetail=" + corpDetail + ", \njobInfoDetail="
				+ jobInfoDetail;
	}
	
	
}








