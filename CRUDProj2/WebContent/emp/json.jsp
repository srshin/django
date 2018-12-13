<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
function call() {
	//json - JavaScript Object Notation
	var obj={"name":"아메리카노", "price":20000};
	here.innerHTML = obj.name+"<br>" + obj.price+"<br>";
	
	here.innerHTML += JSON.stringify(obj);
	var str = JSON.stringify(obj);
	var obj2 = JSON.parse(str);	
	here.innerHTML +=  "<br>"+ obj2.name +"<br>" + obj2.price +"<br>";
}


</script>
</head>
<body>
<h1>JSON</h1>
<button onclick="call();">JSON얻기</button>
<div id="here">여기여기</div>

</body>
</html>