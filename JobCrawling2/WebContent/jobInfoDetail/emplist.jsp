<%@page import="com.encore.model.EmpDAO"%>
<%@page import="com.encore.model.EmpVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.encore.model.DeptDTO"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%
	List<EmpVO> list = new ArrayList<>();
EmpDAO dao = new EmpDAO();
String empId = request.getParameter("empId");
String deptId = request.getParameter("deptId");
System.out.print("|"+empId+"|");
System.out.print("|"+deptId+"|");

if (empId==null || empId=="")  {
	if (deptId == null || deptId =="")
		list = dao.selectAll();
	else 
		list = dao.selectByDept(Integer.parseInt(deptId));
}
else {
	EmpVO empVo= dao.selectById(Integer.parseInt(empId));
	list.add(empVo);
}	
pageContext.setAttribute("list", list);
%>
<style>
   table tr:nth-child(2n) {
   	background:silver;
   	cursor:pointer
   }
   table tr:nth-child(2n+1) {
   	background:lightgray;
   	cursor:pointer
   }
   table tr.empheader {
   	background:yellow;   
   	cursor:default;
   }
</style>
<table>
 <tr class="empheader">
   <td>직원번호</td><td>이름</td><td>성</td><td>이메일</td><td>이메일2</td><td>전화번호</td><td>직급</td><td>입사일</td>
   <td>부서</td><td>급여</td><td>메니져</td><td>커미션</td><td>삭제기능</td>
   
 </tr>
 <c:forEach items="${list}" var="emp">
 <tr onclick="detailView(${emp.employee_id});">
   <td >${emp.employee_id}</td>
   <td>${emp.first_name }</td>
   <td>${emp.last_name }</td>
   <td>${emp.email }</td>
   <td> ${fn:substring(emp.email,0,2)}</td>
   <td>${emp.phone_number }</td>
   <td>${emp.job_id }</td>
   <td>
      <fmt:formatDate pattern="yyy/MM/dd hh:mm:ss" 
      value="${emp.hire_date}" />
   </td>
   <td>${emp.department_id }</td>
   <td>
	   <fmt:setLocale value="en_US"/>
	   <fmt:formatNumber value="${emp.salary }" type="currency" />
   </td>
   <td>${emp.manager_id }</td>
   <td>${emp.commission_pct }</td>
   <td><button onclick="call(${emp.employee_id});">삭제</button></td>
 
 </tr>
 </c:forEach>

</table>