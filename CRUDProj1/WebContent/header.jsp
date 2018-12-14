<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

 
<c:if test="${sessionScope.emp==null}">
   <a href="${pageContext.request.contextPath}/login/sign.go">로그인</a>
</c:if>
<c:if test="${sessionScope.emp!=null}">
	${sessionScope.emp.first_name } <br>
   <a href="${pageContext.request.contextPath}/login/signOut.go">로그아웃</a>
</c:if>
	<hr>

 


