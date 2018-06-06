<%@page import="kantor.LoginData"%>  
<%@page import="kantor.DB" %>

<jsp:useBean id="obj" class="kantor.LoginData"/>  
  
<jsp:setProperty property="*" name="obj"/>  

<%
    if (DB.login(obj.getLogin(), obj.getPassword()))
	{
		session.setAttribute("LoggedIn", true);
		response.sendRedirect("Adminboard.jsp");
	}
    else
    {
    	response.sendRedirect("Login.jsp?err=wrong_input");
    }
%>