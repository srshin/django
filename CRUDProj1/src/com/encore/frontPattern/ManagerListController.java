package com.encore.frontPattern;

import java.util.Map;

import com.encore.model.EmpDAO;
import com.encore.model.EmpService;

public class ManagerListController implements CommonController {

	@Override
	public void execute(Map<String, Object> data) {
		EmpService service = new EmpService();
		data.put("managerList", service.selectAllManager());

	}

}
