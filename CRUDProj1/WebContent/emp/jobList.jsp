<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

</head>
<body>
<!-- HTML의 기본경로는 http://ip:port    -->
<!-- 서블릿은 http://ip:port/educaion  -->
<jsp:include page="/header.jsp"></jsp:include><br>
<hr>

<h1>직책 목록</h1>
<ol> 
 <c:forEach items="${jobList}" var="job">
   <li>${job.job_id} ${job.job_title }</li>
 </c:forEach>
</ol>

</body>
</html>
