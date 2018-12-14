<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
function func() {
	  var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	      document.getElementById("demo").innerHTML = this.responseText;
	    }
	  };
	  var param = "empid="+ document.getElementById("empid").value;
	  //alert(param);
	  xhttp.open("GET", "getEmpInfo.go?"+param);
	  xhttp.send();
	
	
}
</script>
</head>
<body>
<h1> ajax test</h1>
<form action="getEmpInfo.go">
<input type="number" name="empid" id="empid">
<input type="button" value="이름얻기" onclick="func();">
</form>

<hr>
직원이름 : <div id="demo">여기~~</div>
</body>
</html>