<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>     <!-- stylesheet -->     
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/c3/0.4.11/c3.min.css"/> </head> 
<body>     <!-- javascript -->     <script src="https://d3js.org/d3.v3.min.js"></script>     
<script src="https://cdnjs.cloudflare.com/ajax/libs/c3/0.4.11/c3.min.js"></script> 
<script>
var chart = c3.generate({
	  bindto: "#linechart",
	  data: {
	    columns: [
	      ['data1', 30, 200, 100, 400, 150, 250],
	      ['data2', 50, 20, 10, 40, 15, 25]
	    ]
	  }
	});
</script>
<div id="linechart"></div>

</body>

</html>