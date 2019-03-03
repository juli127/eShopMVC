<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<c:set var="username" value="${sessionScope.userName}"/>
<span userId='${sessionScope.user.id}'></span>
<br><br>
<%--<br>header: session: ${session}--%>
<br>header: sessionScope.userId: ${sessionScope.user.id}
<br>header: sessionScope.user: ${sessionScope.user}
<br>header: sessionScope.userName: ${sessionScope.userName}
<br>header: sessionScope.cartSize: ${sessionScope.cartSize}
<br>header: sessionScope.totalSum: ${sessionScope.totalSum}
<br>header: sessionScope.message: ${sessionScope.message}
<br>header: sessionScope.regErrors: ${sessionScope.regErrors}
<br>header: sessionScope.productsInCart: ${sessionScope.productsInCart}
<br>header: header.values(): ${header.values()}

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
                            <li><a href="products">All categories</a></li>
                            <c:choose>
                                <c:when test="${username != null}">
                                    <li><a href="logout">Logout</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li><a href="login">Login</a></li>
                                    <li><a href="registration">Registration</a></li>
                                </c:otherwise>
                            </c:choose>
                            <li><a href="cart">Cart</a></li>
                        </ul>
                    </div>
                </td>
            </tr>
        </table>
    </div>

    <c:if test="${username != null}">
    <div id="autoriz">
            ${greeting} your cart has <span id="goodsCount">${sessionScope.cartSize==null?0:sessionScope.cartSize}</span></> items
</div>
</c:if>

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