<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<link href="style/style.css" rel="stylesheet" >
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Logowanie </title>
</head>
<body>
	Logowanie do panelu admina 
	<hr>
	<form action="action_login.jsp" method="post">
		<p> Login: <input type="text" name="login" /> </p>
		<p> Haslo: <input type="password" name="password" /> </p>
		
		<button type="submit"> Zaloguj </button>
	</form>
	<%
		String errorCode = request.getParameter("err");
		if (errorCode != null && errorCode.equals("wrong_input"))
		{
			out.println("<h3 class='err'> Wprowadzono nieprawidlowe dane logowania. Sprobuj ponownie </h3>");
		}
	%>
</body>
</html>