package com.encore.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.encore.util.OracleDBUtil;

public class CorpDAO {

	public static String DBTableName = "searchlist"; 
	public int insertAll(List<CorpVO> list) {
		Connection conn = null;
		Statement st = null;
		int[] result = null;
		conn = OracleDBUtil.dbConnect();
		try {
			conn.setAutoCommit(false); // auto commit false
			st = conn.createStatement();
			for (CorpVO vo : list) {
				String sql = " insert into "+ DBTableName + " values( "  + 
									vo.getIdNum() +  ",  '" + 
									vo.getRegDate() +  "' , '" + 
									vo.getCorpName() + "' , '" +
									vo.getCorpHref() + "' , '" +
									vo.getJobInfo() + "' , '" +
									vo.getJobInfoHref() + "' , '" +
									vo.getJobKeyword()  + "' , '" +
									vo.getJobSpec() + "')";
				System.out.println(sql);
				st.addBatch(sql);
			}
			result = st.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			OracleDBUtil.dbDisconnect(null, st, conn);
		}
		System.out.println(result);
		int count = 0;
		for (int i : result) {
			count += i;
		}
		System.out.println(count);
		return count;
	}

	public List<CorpVO> selectAll() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		List<CorpVO> list = new ArrayList<>();
		String sql = "select * from " + DBTableName;
		conn = OracleDBUtil.dbConnect();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				CorpVO vo = new CorpVO(
						rs.getInt("idNum"),
						rs.getString("regDate"), 
						rs.getString("corpName"),
						rs.getString("corpHref"),
						rs.getString("jobInfo"),
						rs.getString("jobInfoHref"),
						rs.getString("jobKeyword"),
						rs.getString("jobSpec")
						);
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleDBUtil.dbDisconnect(rs, st, conn);
		}

		return list;
	}

	public List<String> selectByColumn(String column) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<>();
		String sql = "select " + column + " from " + DBTableName;
		conn = OracleDBUtil.dbConnect();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String string = new String(rs.getString(column)); 
				list.add(string);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleDBUtil.dbDisconnect(rs, st, conn);
		}

		return list;
	}
	
}










