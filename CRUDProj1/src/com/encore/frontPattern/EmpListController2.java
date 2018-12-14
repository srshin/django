package com.encore.frontPattern;

import java.util.List;
import java.util.Map;

import com.encore.model.EmpService;
import com.encore.model.EmpVO;

public class EmpListController2 implements CommonController{

	@Override
	public void execute(Map<String, Object> data) {
		EmpService service = new EmpService();
		List<EmpVO> emplist= service.selectByDeptJob(
				(String) data.get("deptid"), 
				(String) data.get("jobid"),
				(String) data.get("salary")); 
		data.put("emps", emplist);
	}

}
