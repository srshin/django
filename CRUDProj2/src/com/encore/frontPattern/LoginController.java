package com.encore.frontPattern;

import java.util.Map;

import com.encore.model.EmpService;
import com.encore.model.EmpVO;

public class LoginController implements CommonController {

	@Override
	public void execute(Map<String, Object> data) {
		System.out.println("execute");
		System.out.println(data.get("method"));
		String userid = (String) data.get("userid");
		String userpass = (String) data.get("userpass");
		EmpService service = new EmpService();
		if (data.get("method").equals("get")) return;
		EmpVO emp=service.loginCheck(userid, userpass);
		data.put("loginResult", emp== null? "no":"yes");
		data.put("emp", emp);
	}

}
