<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1> 직원관리 </h1>
<c:set var="path" value="${pageContext.request.contextPath }"></c:set>


<jsp:include page="/header.jsp"></jsp:include><br>

<c:if test ="${sessionScope.emp!=null }">
<ol>
<li><a href="${path }/emp/emplist.go">직원조회</a></li>
<li><a href="${path }/emp/empInsert.go">직원등록</a></li>
<li><a href="${path }/emp/deptList.go">부서조회</a></li>
<li><a href="${path }/emp/jobList.go">직책조회</a></li>
<li><a href="${path }/emp/managerList.go">매니저조회</a></li>
</ol>
</c:if>
<img alt="" src="${path }/images/200x200bb.jpg" >

</body>
</html>