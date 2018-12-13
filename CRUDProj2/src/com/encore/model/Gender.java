package com.encore.model;

public enum Gender {
	M(1, "Male","����"),
	F(2, "Female","����");
	
	public String engName;
	public String korName;
	public int su;
	Gender(int su, String engName, String korName){
		this.su = su;
		this.engName = engName;
		this.korName = korName;
	}
    
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return korName + "(" + engName + ")";
	}



	public String getEngName() {
		return engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public String getKorName() {
		return korName;
	}

	public void setKorName(String korName) {
		this.korName = korName;
	}
	
	
	
}
