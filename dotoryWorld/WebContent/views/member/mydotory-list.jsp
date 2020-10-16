<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>my dotory list</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
	<div align="center">
		<h2>My Dotories List</h2>
		<hr>
		<form action="">
			<table border="1">
				<tr>
					<th>id</th>
				</tr>
				<c:forEach items="${requestScope.myDotoryList}" var="friend">
				<tr>
					<td>${friend.id}</td>
				</tr>
				</c:forEach>
				
			</table>
		</form>
	</div>
</body>
</html>






