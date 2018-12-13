<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html >

<h1>저장 결과</h1>
<%-- <p>${emp }</p> --%>
<p>${message }</p>
직원번호 : ${emp.employee_id}<br>
이름 : ${emp.first_name}<br>
성 : ${emp.last_name}<br>
이메일 :${emp.email}<br>
전화번호 : ${emp.phone_number}<br>
커미션 : ${emp.commission_pct}<br>
부서번호 : ${emp.department_id}<br>
직책 : ${emp.job_id}<br>
입사일 : ${emp.hire_date}<br>
급여 : ${emp.salary}<br>
메니져 : ${emp.manager_id}<br>

tml>