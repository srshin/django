package com.encore.frontPattern;

import java.util.Map;

import com.encore.model.EmpService;

public class EmpListController implements CommonController{

	@Override
	public void execute(Map<String, Object> data) {
		EmpService service = new EmpService();
		data.put("dList", service.selectAllDept());
		data.put("jList", service.selectAllJob());
		data.put("mList", service.selectAllManager());
		data.put("emps", service.selectAll());
		
	}

}
