package com.encore.frontPattern;

import java.util.Map;

import com.encore.model.EmpService;

public class EmpDeleteController implements CommonController{

	@Override
	public void execute(Map<String, Object> data) {
		EmpService service = new EmpService();
			int empid = Integer.valueOf((String)(data.get("empid")));
			data.put("message", service.deleteEmp(empid)>0?"삭제성공":"삭제실패");
	}

}
