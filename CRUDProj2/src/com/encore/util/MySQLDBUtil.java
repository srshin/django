package com.encore.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDBUtil {

	//DB����
	public static Connection dbConnect() {
		Connection conn = null;
		String url="jdbc:mysql://192.168.2.82:3306/employees?useSSL=true&verifyServerCertificate=false";  
				 
		String user="root", password="1234";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;	
	}
	
	//DB��������
	public static void dbDisconnect(ResultSet rs, 
			Statement st, Connection conn) {
		try {
			
			if(rs!=null)rs.close();
			if(st!=null)st.close();
			if(conn!=null)conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}













