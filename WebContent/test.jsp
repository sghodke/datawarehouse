<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test</title>
</head>
<body>

	<form action="QueryServlet" id="testQuery" method="post">
	   
	   QueryNo: <input type="text" name="queryNo" />
	    <br>
	    Query-1
		Description: <input type="text" name="description" />
		Name: <input type="text" name="name" />
		Type: <input type="text" name="type" />
		<br>
		Query-2
		Description: <input type="text" name="description2" />
		<br>
		Query-4
		GO Id: <input type="text" name="goIdFour" />
		Disease Name: <input type="text" name="dsName" />
		<br />
		Query-5
		GO Id: <input type="text" name="goId" />
		Disease Name 1: <input type="text" name="dsNameOne" />
		Disease Name 2: <input type="text" name="dsNameTwo" />
		Disease Name 3: <input type="text" name="dsNameThree" />
		Disease Name 4: <input type="text" name="dsNameFour" />
		<br>
		Query-6 
		<br>
		Go-Id: <input type="text" name="goIdSix" />
		<br>
		<input type="submit" value="Get Results" />
	</form>

</body>
</html>