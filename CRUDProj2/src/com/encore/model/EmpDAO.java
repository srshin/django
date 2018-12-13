package com.encore.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.encore.util.OracleDBUtil;

public class EmpDAO {
	
	
	public List<ManagerDTO> selectAllManager() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = 
		"select distinct manager.employee_id, manager.first_name || ' ' || manager.last_name emp_name"
		+ " from employees emp,  employees manager "
		+ " where emp.manager_id = manager.employee_id "
		+ " order by 1 ";
		List<ManagerDTO> list = new ArrayList<>();

		conn = OracleDBUtil.dbConnect();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				ManagerDTO manager = new ManagerDTO(rs.getInt(1), rs.getString(2));
				list.add(manager);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleDBUtil.dbDisconnect(rs, st, conn);
		}
		return list;
	}	
	
	
	
	
	public List<JobDTO> selectAllJob() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "select * from jobs order by 1 ";
		List<JobDTO> list = new ArrayList<>();

		conn = OracleDBUtil.dbConnect();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				JobDTO job = new JobDTO(rs.getString(1), rs.getString(2));
				list.add(job);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleDBUtil.dbDisconnect(rs, st, conn);
		}
		return list;
	}

	
	public List<DeptDTO> selectAllDept() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "select * from departments order by 1 ";
		List<DeptDTO> list = new ArrayList<>();

		conn = OracleDBUtil.dbConnect();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				DeptDTO dept = new DeptDTO(rs.getInt(1), rs.getString(2));
				list.add(dept);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleDBUtil.dbDisconnect(rs, st, conn);
		}
		
		return list;
	}

	public List<EmpVO> selectAll() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "select * from employees order by 1 ";
		EmpVO emp = null;
		List<EmpVO> emplist = new ArrayList<>();

		conn = OracleDBUtil.dbConnect();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleDBUtil.dbDisconnect(rs, st, conn);
		}
		
		
		return emplist;
	}

	

	private EmpVO makeEmp(ResultSet rs) throws SQLException {
		int employee_id = rs.getInt(1);
		String first_name = rs.getString("first_name");
		String last_name = rs.getString("last_name");
		String email = rs.getString("email");
		String phone_number = rs.getString("phone_number");
		Date hire_date = rs.getDate("hire_date");
		String job_id = rs.getString("job_id");
		int salary = rs.getInt("salary");
		double commission_pct = rs.getDouble("commission_pct");
		int manager_id = rs.getInt("manager_id");
		int department_id = rs.getInt("department_id");
		EmpVO emp = new EmpVO(employee_id, first_name, last_name, email, 
				phone_number, hire_date, job_id, salary, commission_pct, 
				manager_id, department_id);
		return emp;
	}



	public EmpVO selectById(int empid) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "select * from employees where employee_id=" + empid;
		EmpVO emp = null;
		conn = OracleDBUtil.dbConnect();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()) {
				emp = makeEmp(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleDBUtil.dbDisconnect(rs, st, conn);
		}
		return emp;
	}

	public List<EmpVO> selectByDept(int deptid) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "select * from employees where department_id = "+ deptid;
		EmpVO emp = null;
		List<EmpVO> emplist = new ArrayList<>();

		conn = OracleDBUtil.dbConnect();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleDBUtil.dbDisconnect(rs, st, conn);
		}
		
		
		return emplist;
	}

	public List<EmpVO> selectBySalary(int salary) {
		//
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "select * from employees where salary >= "+ salary;
		EmpVO emp = null;
		List<EmpVO> emplist = new ArrayList<>();

		conn = OracleDBUtil.dbConnect();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleDBUtil.dbDisconnect(rs, st, conn);
		}
		
		
		return emplist;
	}

	public List<EmpVO> selectByDeptJob(int deptid, String jobid) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "select * from employees "
				+ "where department_id = ? and job_id = ?";
		EmpVO emp = null;
		List<EmpVO> emplist = new ArrayList<>();

		conn = OracleDBUtil.dbConnect();
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, deptid); //setX
			st.setString(2, jobid);
			rs = st.executeQuery();
			while(rs.next()) {
				emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleDBUtil.dbDisconnect(rs, st, conn);
		}
		
		
		return emplist; 
	}

	public int insertEmp(EmpVO emp) {
		int result = 0;
        Connection conn = null;
        PreparedStatement st = null;
        String sql = "insert into employees values(?,?,?,?,?,?,?,?,?,?,?) ";		
        conn = OracleDBUtil.dbConnect();
        try {
			st = conn.prepareStatement(sql);
			st.setInt(1, emp.getEmployee_id());
			st.setString(2, emp.getFirst_name());
			st.setString(3, emp.getLast_name());
			st.setString(4, emp.getEmail());
			st.setString(5, emp.getPhone_number());
			st.setDate(6, emp.getHire_date());
			st.setString(7, emp.getJob_id());
			st.setInt(8, emp.getSalary());
			st.setDouble(9, emp.getCommission_pct());
			st.setInt(10, emp.getManager_id());
			st.setInt(11, emp.getDepartment_id());
			result = st.executeUpdate();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleDBUtil.dbDisconnect(null, st, conn);
		}
        return result;
		
	}

	public int updateEmp(EmpVO emp) {
		int result = 0;
        Connection conn = null;
        PreparedStatement st = null;
        String sql = "update employees "
        		+ " set First_name=?,Last_name=?,Email=?,"
        		+ " Phone_number=?,Hire_date=?"
        		+ " where employee_id = ?";		
        conn = OracleDBUtil.dbConnect();
        try {
			st = conn.prepareStatement(sql);
			st.setInt(6, emp.getEmployee_id());
			st.setString(1, emp.getFirst_name());
			st.setString(2, emp.getLast_name());
			st.setString(3, emp.getEmail());
			st.setString(4, emp.getPhone_number());
			st.setDate(5, emp.getHire_date());
			 
			result = st.executeUpdate();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleDBUtil.dbDisconnect(null, st, conn);
		}
        return result;
	}

	public int updateEmpBySal(int salary) {
		int result = 0;
		String sql = "update employees "
				+ "set salary = salary*1.1 "
				+ "where salary >=?";
		Connection conn = OracleDBUtil.dbConnect();		
		PreparedStatement st = null;		
		try {
			//conn.setAutoCommit(false);
			st = conn.prepareStatement(sql);
			st.setInt(1, salary);
			result = st.executeUpdate();
			//conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			/*try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
		} finally {
			OracleDBUtil.dbDisconnect(null, st, conn);
		}
		return result;
	}

	public int deleteEmp(int empid) {
		int result = 0;
		String sql = "delete from employees "
				+ "where employee_id =?";
		Connection conn = OracleDBUtil.dbConnect();		
		PreparedStatement st = null;		
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, empid);
			result = st.executeUpdate();			
		} catch (SQLException e) {			
			e.printStackTrace();			
		} finally {
			OracleDBUtil.dbDisconnect(null, st, conn);
		}
		return result;
	}



	public Map<String, Object> executeTest(String sql) {
		Map<String, Object> data = new HashMap<String, Object>();
		Connection conn=null;
		Statement st = null;
		ResultSet rs = null;
		int result=0;
		
		conn = OracleDBUtil.dbConnect();
		try {
			st = conn.createStatement();
			boolean b = st.execute(sql);
			if(b) {
				//Select
				rs = st.getResultSet();
				List<EmpVO> emplist = new ArrayList<>();
				while(rs.next()){
					emplist.add(makeEmp(rs));
				}
				data.put("select", emplist);
			}else {
				//DML
				result = st.getUpdateCount();
				data.put("update", result);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleDBUtil.dbDisconnect(rs, st, conn);
		}
		return data;
	}



	public int[] addBatchTest(String[] jobs) {
		Connection conn = null;
		Statement st = null;
		int[] result = null;
		
		conn = OracleDBUtil.dbConnect();
		try {
			conn.setAutoCommit(false);
			st = conn.createStatement();
			for(String sql:jobs) {
			   st.addBatch(sql);
			}
			result = st.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			OracleDBUtil.dbDisconnect(null, st, conn);
		}
		
		
		
		return result;
	}



	public int plsqlTest(int sal, double rate) {
		int result = 0;
		Connection conn = null;
		CallableStatement st = null;
		String sql="{call sp_tax(?, ?, ?, ?)}";
		conn = OracleDBUtil.dbConnect();
		try {
			st = conn.prepareCall(sql);
			st.setInt(1, sal);
			st.setDouble(2, rate);
			st.registerOutParameter(3, Types.INTEGER);
			st.registerOutParameter(4, Types.VARCHAR);
			st.executeQuery();
			result = st.getInt(3);
			System.out.println(st.getString(4));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleDBUtil.dbDisconnect(null, st, conn);
		}
		
		return result;
	}


	public EmpVO loginCheck(String userid, String userpass) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "select * from employees where first_name = ? and last_name = ?";
		EmpVO emp = null;
		conn = OracleDBUtil.dbConnect();
		try {
			st = conn.prepareStatement(sql);
			st.setString(1, userid);
			st.setString(2, userpass);
			rs = st.executeQuery();
			if(rs.next()) {
				emp = makeEmp(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleDBUtil.dbDisconnect(rs, st, conn);
		}
		return emp;
	}

	public List<EmpVO>  selectByDeptJob(String deptid, String jobid, String salary) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "select * from employees where 1=1 ";
		if (!deptid.equals("all")) sql+=" and department_id="+deptid;
		if (!jobid.equals("all")) sql+=" and job_id= '"+jobid+"'";
		if (!salary.equals("")) sql +="and salary >=" +salary;
		EmpVO emp = null;
		List<EmpVO> emplist = new ArrayList<>();
		conn = OracleDBUtil.dbConnect();
		try {
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while(rs.next()) {
				emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleDBUtil.dbDisconnect(rs, st, conn);
		}
		
		
		return emplist; 
	}
}










