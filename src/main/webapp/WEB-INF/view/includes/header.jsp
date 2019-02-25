<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<c:set var="username" value="${sessionScope.userName}"/>
<br><br>
<%--<br>jsp: attempt: ${attempt}--%>
<%--<br>header: user: ${user}--%>
<%--<br>header: userName: ${userName}--%>
<%--<br>header: session: ${session}--%>

<c:choose>
    <c:when test="${username != null}">
        <c:set var="greeting" value="${username},"/>
    </c:when>
    <c:otherwise>
        <c:set var="greeting" value=""/>
    </c:otherwise>
</c:choose>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <link href="static/css/style.css" rel="stylesheet" type="text/css" media="screen"/>
</head>

<body>
<div id="wrapper">
    <div id="header">
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
                            <c:choose>
                                <c:when test="${username != null}">
                                    <li><a href="logout">Logout</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li><a href="login">Login</a></li>
                                    <li><a href="registration">Регистрация</a></li>
                                </c:otherwise>
                            </c:choose>
                            <li><a href="cart">Корзина</a></li>
                        </ul>
                    </div>
                </td>
            </tr>
        </table>
    </div>

    <div id="autoriz">
        ${greeting} в Вашей корзине товаров: ${sessionScope.cartSize==null?0:sessionScope.cartSize}</>
    </div>

    <%--<p>--%>
    <div class="page" id="page">
        <div id="sidebar">
            <table>
                <tr>ОДЕЖДА<a href="products?selectedCategory=1"><img src="static/images/evening_dresses_small.jpg"
                                                                     alt="" width="120" height="120"
                                                                     title="Одежда"/></a></tr>
                <tr>ОБУВЬ<a href="products?selectedCategory=2"><img src="static/images/evening_shoes_small.jpg"
                                                                    alt="" width="120" height="120" title="Обувь"/></a>
                </tr>
                <tr>АКСЕССУАРЫ<a href="products?selectedCategory=3"><img src="static/images/aksess1.jpg" alt=""
                                                                         width="120" height="120"
                                                                         title="Аксессуары"/></a></tr>
            </table>
        </div>
        <!-- end #header -->

        <div id="content">