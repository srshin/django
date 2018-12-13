package com.encore.model;

import java.util.List;

public class MySQLService {
    MySQLDAO dao = new MySQLDAO();
	public List<MySQLVO> selectAll(int count){
		return dao.selectAll(count);
	}
}
