<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="UTF-8"%>

<% 
	Object obj = session.getAttribute("LoggedIn");
	if (obj == null || obj.equals(false))
	{
		response.sendRedirect("index.jsp");
	}
%>

<!DOCTYE HTML>
<html>
	<head>
		<link href="style/style.css" rel="stylesheet" >
		<script	src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
		<script src="script/script.js"> </script>
	</head>
	
	<body onLoad="AdminInit()">
		<h1> Panel Admina </h1>
		<hr>
		<h2> Historia zmian walut </h2>
		<table border=1 >
			<thead>
				<tr>
					<th> Ikona </th>
					<th> Nazwa </th>
					<th> Kod </th>
					<th> Kupno </th>
					<th> Sprzedaz </th>
					<th> Zmiana </th>
				</tr>
			</thead>
			<tbody id="history">
			
			</tbody>
		</table>
		<h2></h2>
		<hr>
		<h2> Waluty </h2>
		<table border="1">
			<thead> 
				<tr>
					<th> Flaga </th>
					<th> Nazwa waluty </th>
					<th> Kod </th>
					<th> Kupno </th>
					<th> Sprzedaz </th>
					<th> Ostatnia aktualizacja </th>
					<th> Dzialania </th>
				</tr>
			</thead>
			<tbody id="content_table">
			
			</tbody>
		
		</table>
		<hr>
		<h2> Dodaj nowa walute </h2>
		<form >
		Nazwa: <input type="text" /> 
		Kod: <input type="text" /> 
		Ikona: <input type="text" />
		Kupno: <input type="text" />
		Sprzedaz: <input type="text" />
		</form>
	</body>

</html>