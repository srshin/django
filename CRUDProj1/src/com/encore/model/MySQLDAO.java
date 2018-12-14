package com.encore.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.encore.util.MySQLDBUtil;

public class MySQLDAO {

	public List<MySQLVO> selectAll(int count) {
		List<MySQLVO> emplist = new LinkedList<>();
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "select * from employees where gender is not null limit ?";
		conn = MySQLDBUtil.dbConnect();
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, count);
			rs = st.executeQuery();
			while(rs.next()) {
				emplist.add(makeEmp(rs) );
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			MySQLDBUtil.dbDisconnect(rs, st, conn);
		}
		return emplist;
	}

	private MySQLVO makeEmp(ResultSet rs) throws SQLException {
		int emp_no = rs.getInt("emp_no");
		String first_name = rs.getString("first_name");
		String last_name = rs.getString("last_name");
		Gender gender = Gender.valueOf(rs.getString("gender"));
		Date hire_date = rs.getDate("hire_date");
		Date birth_date = rs.getDate("birth_date");
		MySQLVO emp = new MySQLVO(emp_no, first_name, last_name, 
				gender, 
				hire_date, birth_date);
		return emp;
	}
	
	
	
}





