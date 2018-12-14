package com.encore.model;

import java.sql.Date;

public class MySQLVO {
	int emp_no;
	String first_name;
	String last_name;
	Gender gender;
	Date hire_date;
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MySQLVO [emp_no=").append(emp_no).append(", first_name=").append(first_name)
				.append(", last_name=").append(last_name).append(", gender=").append(gender).append(", hire_date=")
				.append(hire_date).append(", birth_date=").append(birth_date).append("]");
		return builder.toString();
	}
	Date birth_date;
	public MySQLVO(int emp_no, String first_name, 
			String last_name, Gender gender, Date hire_date, Date birth_date) {
		super();
		this.emp_no = emp_no;
		this.first_name = first_name;
		this.last_name = last_name;
		this.gender = gender;
		this.hire_date = hire_date;
		this.birth_date = birth_date;
	}
	
	
	
}
