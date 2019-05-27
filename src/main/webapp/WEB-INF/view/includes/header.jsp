<%@ page contentType="text/html; charset=UTF-8" language="java"  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<c:set var="user" value="${user}"/>
<c:set var="username" value="${user.name}"/>
<c:set var="cart" value="${cart}"/>
<c:set var="itemsCount" value="${cart.itemsCount}"/>
<c:set var="warning" value="${warning}"/>
<c:set var="users" value="${usersList}" ></c:set>
<c:set var="products" value="${products}"></c:set>
<c:set var="totalSum" value="${cart.totalSum}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
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
                            <li><a href="product">All categories</a></li>
                            <c:choose>
                                <c:when test="${user != null && username != null}">
                                    <li><a href="logout">Logout</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li><a href="login">Login</a></li>
                                    <li><a href="registration">Registration</a></li>
                                </c:otherwise>
                            </c:choose>
                            <li><a href="cart">Cart</a></li>
                            <li><a href="order">Order</a></li>
                            <c:if test="${isAdmin}">
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
            <c:when test="${warning.length() > 0}">
                <font color=red>${warning}</font>
            </c:when>
            <c:otherwise>
            <c:choose>
            <c:when test="${user != null && username.length() > 0}">
                ${username}, your cart has <span id="itemsCountField">${itemsCount==null?0:itemsCount}</span> items
            </c:when>
            <c:otherwise>
                <font color=red>You should <a href="registration">register</a> or <a href="login">login</a> before shopping or see your cart/order!</font>
                </c:otherwise>
            </c:choose>
            </c:otherwise>
        </c:choose>
    </div>

<%--<p>--%>
<div class="page" id="page">
    <div id="sidebar">
        <table>
            <tr>DRESSES<a href="product?selectedCategory=1"><img src="static/images/evening_dresses_small.jpg"
                                                                  alt="" width="120" height="120"
                                                                  title="DRESSES"/></a></tr>
            <tr>SHOES<a href="product?selectedCategory=2"><img src="static/images/evening_shoes_small.jpg"
                                                                alt="" width="120" height="120" title="SHOES"/></a>
            </tr>
            <tr>ACCESSORIES<a href="product?selectedCategory=3"><img src="static/images/aksess1.jpg" alt=""
                                                                      width="120" height="120"
                                                                      title="ACCESSORIES"/></a></tr>
        </table>
    </div>
    <!-- end #header -->

    <div id="content">
