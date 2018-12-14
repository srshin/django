package com.encore.frontPattern;

import java.util.Map;

import com.encore.model.EmpService;
import com.encore.model.EmpVO;

public class EmpInsertController implements CommonController{

	@Override
	public void execute(Map<String, Object> data) {
		String method = (String) data.get("method");
		EmpService service = new EmpService();
		if (method.equals("get")) {
			data.put("dList", service.selectAllDept());
			data.put("jList", service.selectAllJob());
			data.put("mList", service.selectAllManager());
		} else {
		EmpVO emp = (EmpVO) data.get("emp");
		data.put("result", service.insertEmp(emp));
		}
		
	}
	
	

}
