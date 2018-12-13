<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
   h1, h2 { color:orange;}
   <!--     >         자식 -->
    <!--   한칸 띄기 -> 자손 -->

   table tr:nth-child(2n) {
   	background:silver;
   }

   table tr:nth-child(2n+1) {
   	background:lightgray;
   }
   <!-- . class  --> 
   table tr.empheader {
   	background:yellow;   
   }
   #here {
   border:5px dotted red; 
   }
</style>
<script>
function call(empid) {
	ret = confirm("삭제하시겠습니까? ");
	if(ret)
	location.href='empDelete.go?empid='+empid;
}

function retreive() {
	  var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	      document.getElementById("here").innerHTML = this.responseText;
	    }
	  };
	var dept = document.getElementById("department_id").value;
	var job = document.getElementById("job_id").value;
	var salary = document.getElementById("salary").value;
	var param = "deptid="+dept+"&jobid="+job+"&salary="+salary;
	alert(param);
	  xhttp.open("GET", "emplist2.go?"+param);
	  xhttp.send();
	
	
}

</script>
</head>
<body>
<c:set var="path" value="${pageContext.request.contextPath }"></c:set>
<!-- HTML의 기본경로는 http://ip:port    -->
<!-- 서블릿은 http://ip[:port/educaion  -->
<jsp:include page="/header.jsp"></jsp:include><br>

<a href="${path }/emp/empInsert.go">직원등록</a><br>
<hr>
부서번호 : 
<select name="department_id" id="department_id">
		<option value="all">부서전체</option>
	<c:forEach var="d" items="${dList }">
		<option value="${d.department_id }">${d.department_name }</option>
	</c:forEach>
</select>
직책 :  
<select name="job_id" id="job_id">
		<option value="all">직책전체</option>
	<c:forEach var="j" items="${jList }">
		<option value="${j.job_id }">${j.job_title }</option>
	</c:forEach>
</select>
Salary :
<input type="number" name="salary" id="salary" value="0"/>
매니저 : 
<select name="manager_id" id="manager_id">
	<c:forEach var="m" items="${mList }">
		<option value="${m.employee_id }">${m.emp_name }</option>
	</c:forEach>
</select>

<button onclick="retreive();">조회</button>


<hr>
<h1>직원 목록</h1>
<h2>조회화면</h2>
<div id="here"> 
<table>
 <tr class="empheader">
   <td>직원번호</td><td>이름</td><td>성</td><td>이메일</td><td>이메일2</td><td>전화번호</td><td>직급</td><td>입사일</td>
   <td>부서</td><td>급여</td><td>메니져</td><td>커미션</td><td>삭제기능</td>
   
 </tr>
 <c:forEach items="${emps}" var="emp">
 <tr>
   <td><a href="empById.go?empid=${emp.employee_id}">${emp.employee_id}</a></td>
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
</div>


</body>
</html>






