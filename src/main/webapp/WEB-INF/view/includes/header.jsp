<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<c:set var="user" value="${sessionScope.user}"/>
<c:set var="username" value="${user.name}"/>
<c:set var="cart" value="${sessionScope.userCart}"/>
<c:set var="itemsCount" value="${cart.itemsCount}"/>

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
                            <li><a href="products">All categories</a></li>
                            <c:choose>
                                <c:when test="${user != null}">
                                    <li><a href="logout">Logout</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li><a href="login">Login</a></li>
                                    <li><a href="registration">Registration</a></li>
                                </c:otherwise>
                            </c:choose>
                            <li><a href="cart">Cart</a></li>
                            <li><a href="order">Order</a></li>
                            <c:if test="${sessionScope.isAdmin}">
                                <li><a href="admin">Admin</a></li>
                            </c:if>
                        </ul>
                    </div>
                </td>
            </tr>
        </table>
    </div>

    <div id="autoriz">
        <c:choose>
            <c:when test="${user != null && username.length() > 0}">
                ${username}, your cart has <span id="itemsCountField">${itemsCount==null?0:itemsCount}</span> items
            </c:when>
            <c:otherwise>
            <font color=red>You should register or login before shopping or see your cart/order!</font>
            </c:otherwise>
        </c:choose>
    </div>

<%--<p>--%>
<div class="page" id="page">
    <div id="sidebar">
        <table>
            <tr>DRESSES<a href="products?selectedCategory=1"><img src="static/images/evening_dresses_small.jpg"
                                                                  alt="" width="120" height="120"
                                                                  title="DRESSES"/></a></tr>
            <tr>SHOES<a href="products?selectedCategory=2"><img src="static/images/evening_shoes_small.jpg"
                                                                alt="" width="120" height="120" title="SHOES"/></a>
            </tr>
            <tr>ACCESSORIES<a href="products?selectedCategory=3"><img src="static/images/aksess1.jpg" alt=""
                                                                      width="120" height="120"
                                                                      title="ACCESSORIES"/></a></tr>
        </table>
    </div>
    <!-- end #header -->

    <div id="content">
