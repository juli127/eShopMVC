<%@ include file="includes/header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
	String userLine = (request.getParameter("user") == null)? "":
			"user, Вы выбрали следующие товары:</h2>";
%>
	<h2>${userLine}
	<h2>тут должны быть выбранные товары</h2>
<%@ include file="includes/footer.jsp" %>
