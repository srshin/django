package com.encore.frontPattern;

import java.util.Map;

import com.encore.model.EmpService;
import com.encore.model.EmpVO;

public class EmpUpdateController implements CommonController{

	@Override
	public void execute(Map<String, Object> data) {
		EmpService service = new EmpService();
		String method = (String) data.get("method");
		if (method.equals("get")) {
			int empid = Integer.valueOf((String)(data.get("empid")));
			EmpVO emp = service.selectById(empid);
			data.put("emp", emp);
			data.put("dList", service.selectAllDept());
			data.put("jList", service.selectAllJob());
			data.put("mList", service.selectAllManager());
			data.put("message", emp.getFirst_name());
		}else {
			EmpVO emp = (EmpVO) data.get("emp");
			data.put("message", service.updateEmp(emp)>0?"수정성공":"수정실패");
			
		}
	}
	
	

}
