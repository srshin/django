package com.encore.frontPattern;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/* http://localhost:9090/education/emp/emplist.go 
 * 
 */
import javax.servlet.http.HttpSession;

import com.encore.model.EmpVO;
import com.encore.util.EmpUtil;

@WebServlet("*.go")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String uri = request.getRequestURI();/// education/emp/emplist.go
		String path = request.getContextPath(); // education
		String requestURI = uri.substring(path.length(), uri.length() - 3);
		System.out.println(requestURI);
		CommonController controller = null;
		Map<String, Object> data = new HashMap<>();
		String page = null;
		String method = request.getMethod().toLowerCase();
		data.put("method", method);
		System.out.println(method);
		Object sessionObj = session.getAttribute("emp");
		if (sessionObj==null && !requestURI.equals("/login/sign"))  {
			response.sendRedirect(path+"/login/sign.go");
			return; 
		}
		if(requestURI.equals("/login/signOut")) {
			session.invalidate();
			response.sendRedirect("../index.jsp");
			return ;
		}

		switch (requestURI) {
		case "/emp/deptList" :
			controller = new DeptListController();
			page = "deptList.jsp";
			break;
		case "/emp/jobList" :
			controller = new JobListController();
			page = "jobList.jsp";
			break;
		case "/emp/managerList" :
			controller = new ManagerListController();
			page = "managerList.jsp";
			break;
		case "/login/sign":
			System.out.println("login/sign request");
			controller = new LoginController();
			if (method.equals("get")) {
				page = "sign.jsp";
			}else {
				System.out.println(request.getParameter("userid"));
				System.out.println(request.getParameter("userpass"));
				data.put("userid", request.getParameter("userid"));
				data.put("userpass", request.getParameter("userpass"));
			}
			break;
			
		case "/emp/emplist2":
			controller = new EmpListController2();
			data.put("deptid", request.getParameter("deptid"));
			data.put("jobid", request.getParameter("jobid"));
			data.put("salary", request.getParameter("salary"));
			
			page = "emplist2.jsp";
			break;
		case "/emp/emplist":
			controller = new EmpListController();
			page = "emplist.jsp";
			break;
		case "/emp/empInsert":
			controller = new EmpInsertController();
			if (method.equals("get")) {
				System.out.println("get - insert");
				page = "empInsert.jsp";
			} else {
				data.put("emp", EmpUtil.makeEmp(request));
				page = "result.jsp";
			}
			break;
		case "/emp/empDelete":
			controller = new EmpDeleteController();
			data.put("empid", request.getParameter("empid"));
			page = "result.jsp";
			break;
		case "/emp/getEmpInfo":
			controller = new EmpUpdateController();
			data.put("empid", request.getParameter("empid"));
			page = "result2.jsp";
			break;

		case "/emp/empById":
			controller = new EmpUpdateController();
			if (method.equals("get")) {
				data.put("empid", request.getParameter("empid"));
				page = "empDetail.jsp";
			} else {
				EmpVO emp = EmpUtil.makeEmp(request);
				data.put("emp", emp);
				page = "result.jsp";
			}

			break;
		default:
			System.out.println("URI error발견");
			System.exit(0);
			break;
			

		}
		controller.execute(data);
		Object result = data.get("loginResult");
		if(result != null ) {
			String yesNo = (String) result;
			if(yesNo.equals("yes")) {
				//인증되면
				
				session.setAttribute("emp", data.get("emp"));
				session.setAttribute("signMessage", "");
				response.sendRedirect(path+"/index.jsp");
			} else {
				//인증이 안되면 
				session.setAttribute("signMessage", "인증실패--fail");
				response.sendRedirect("sign.go");
				
			}
			
			
		} else {
		for (String key : data.keySet()) {
			request.setAttribute(key, data.get(key));
		}
		// response.setCharacterEncoding("utf-8");
		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);
		}
	}

}
