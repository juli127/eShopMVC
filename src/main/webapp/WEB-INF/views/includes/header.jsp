<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored ="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link href="resources/css/style.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>

<% String username = (request.getParameter("user") == null)? "": request.getParameter("user");
String greeting = (username.equals("")?"": (username + ", "));
%>

<div class="wrapper">
<div id="header" class="container">
<table>
	<tr>
		<td>
			<div id="logo"> 
				<h1>ModnaShafa</h1>
			</div>
		</td>
		<td>
			<div id="menu">
				<ul>
					<li><a href="products">Каталог товаров</a></li>
					<li><a href="login">Логин</a></li>
					<li><a href="registration">Регистрация</a></li>
					<li><a href="cart">Корзина</a></li>
				</ul>
			</div>
		</td>
	</tr>
</table>
</div>

<div id="autontif" class="autontif">
	${greeting} в Вашей корзине ${sessionscope.cart.size} товаров
</div>

<p>
<div class="page">
	<div id="sidebar">		
	<table>
        <tr>ОДЕЖДА<a href="products"><img src="resources/images/evening_dresses_small.jpg" alt="" width="120" height="120" title="Одежда" /></a></tr>
		<tr>ОБУВЬ<a href="products"><img src="resources/images/evening_shoes_small.jpg" alt="" width="120" height="120" title="Обувь" /></a></tr>
		<tr>АКСЕССУАРЫ<a href="products"><img src="resources/images/aksess1.jpg" alt="" width="120" height="120" title="Аксессуары" /></a></tr>
        
	</table>
	</div>
<!-- end #header -->
	<div id="content">
