package com.encore.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.encore.util.OracleDBUtil;


public class CorpDAO {

	public static String DBTableName = "searchList"; 
	public static String DBTableDetail = "detailList"; 
	
	
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
									vo.getJobSpec() +"' , '" +
									vo.getIdDetail() + "')";
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
				String[] data = {rs.getString("idNum"),
						rs.getString("regDate"), 
						rs.getString("corpName"),
						rs.getString("corpHref"),
						rs.getString("jobInfo"),
						rs.getString("jobInfoHref"),
						rs.getString("jobKeyword"),
						rs.getString("jobSpec"),
						rs.getString("idDetail")
				};
				CorpVO vo = new CorpVO(data);
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
		System.out.println(sql);
		conn = OracleDBUtil.dbConnect();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String string = rs.getString(column);
				if (string == null ) string =" ";
				System.out.println(string);
				list.add(string);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleDBUtil.dbDisconnect(rs, st, conn);
		}

		return list;
	}

	public List<Entry<String, String>> selectByColumns(Entry<String, String> columns2) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		List<Entry<String, String>> list = new ArrayList<>();
//		String sql = "select " + columns2.getKey() + ", " + columns2.getValue() +  " from " + DBTableName +
//				" where idDetail is null " +
//				" order by to_number("+columns2.getKey() +")";
		String sql = "select idNum, jobInfoHref from searchList where IDDETAIL='no' order by to_number(idNum)";
		System.out.println(sql);
		conn = OracleDBUtil.dbConnect();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String key= rs.getString(columns2.getKey());
				String value = rs.getString(columns2.getValue());
				//System.out.println(key+value);
				Entry<String, String> item= new AbstractMap.SimpleEntry<String, String>(key,value);
				list.add(item);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleDBUtil.dbDisconnect(rs, st, conn);
		}
		return list;
	}

	public int updateColumn(String idNum, String column, String value) {
		Connection conn = null;
		int result = 0;
		PreparedStatement st = null;
		String sql = "update " +  DBTableName + " set " + column + " =? " + " where idNum =?";
		conn = OracleDBUtil.dbConnect();
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, value);
			st.setString(2, idNum);

			result = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("update중 에러 발생 ");
		} finally {
			OracleDBUtil.dbDisconnect(null, st, conn);
		}
		return result;
	}

	public int insertDetail(DetailVO vo) {
		Connection conn = null;
		int result = 0;
		PreparedStatement st = null;
		String sql = "insert into " + DBTableDetail + 
				" (idDetail, idNum, corpName, jobInfoHref,  jobPeriod, corpDetail,  jobInfoDetail) values(?, ?, ?, ?, ?, ?, ?)";

		conn = OracleDBUtil.dbConnect();
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, vo.getIdDetail());
			st.setString(2, vo.getIdNum());
			st.setString(3, vo.getCorpName());
			st.setString(4, vo.getJobInfoHref());
			st.setString(5, vo.getJobPeriod());
			st.setString(6, vo.getCorpDetail());
			st.setString(7, vo.getJobInfoDetail());

			result = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			OracleDBUtil.dbDisconnect(null, st, conn);
		}
		return result;
	}

	public List<DetailVO> selectAllDetail() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		List<DetailVO> list = new ArrayList<>();
		String sql = "select * from " + DBTableDetail;
		conn = OracleDBUtil.dbConnect();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String[] data = {rs.getString("idDetail"),
						rs.getString("idNum"), 
						rs.getString("corpName"),
						rs.getString("jobInfoHref"),
						rs.getString("jobPeriod"),
						rs.getString("jobDetail"),
						rs.getString("jobInfoDetail")
				};
				DetailVO vo = new DetailVO(data);
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleDBUtil.dbDisconnect(rs, st, conn);
		}

		return list;
	}

	public Map<String, String> selectByColumnsDetail(Entry<String, String> columns2) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Map<String, String> map = new HashMap<>();
		String sql = "select " + columns2.getKey() + ", " + columns2.getValue() +  " from " + DBTableDetail;
		System.out.println(sql);
		conn = OracleDBUtil.dbConnect();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				map.put(rs.getString(columns2.getKey()),rs.getString(columns2.getValue()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleDBUtil.dbDisconnect(rs, st, conn);
		}

		return map;
	}

	public int selectCurrentLine(String column) {
		int max = 0; 
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "select max(to_number( " + column + " )) max from  " + DBTableDetail;
		System.out.println(sql);
		conn = OracleDBUtil.dbConnect();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				max =rs.getInt("max");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			OracleDBUtil.dbDisconnect(rs, st, conn);
		}
		return max;
	}
	


}










