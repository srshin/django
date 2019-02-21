<%@page import="com.encore.model.EmpDAO"%>
<%@page import="com.encore.model.DeptDTO"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	EmpDAO dao = new EmpDAO();
List<DeptDTO> dlist = dao.selectAllDept();
pageContext.setAttribute("dlist", dlist);
%>


<select name="department_id" id="department_id" >
	<c:forEach var="d" items="${dlist }">
		<option value="${d.department_id }">${d.department_name }</option>
	</c:forEach>
</select>