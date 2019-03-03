<%@ include file="includes/header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<c:set var="userLine" value=""/>
<c:set var="userId" value="${sessionScope.user.id}"/>
<c:if test="${username != null && sessionScope.cartSize > 0}">
    <c:set var="userLine" value="${username}, your cart:"/>
</c:if>
<c:set var="productsInCart" value="${sessionScope.productsInCart}"/>
<c:set var="totalSum" value="${sessionScope.totalSum}"/>

<br><br>
<h3>
    <div id="info">${userLine}</div>
</h3>

<h3>${message}</h3>

<c:if test="${sessionScope.cartSize > 0}">
    <table id="cart" border=1>
        <tr><span id="tableTitle">
            <td>Product's name</td>
            <td>Price</td>
            <td>Quantity</td></span></tr>
        <c:forEach var="purchase" items="${productsInCart}">
            <tr>
                <td>
                    <div id="productName"><c:out value="${purchase.key.name}"/></div>
                </td>
                <td>
                    <div id='price${purchase.key.id}'><c:out value="${purchase.key.price}"/> UAH</div>
                </td>
                <td>
                    <button onclick="deleteFromCart('${purchase.key.id}')">-</button>
                    <span id='q${purchase.key.id}'>${purchase.value}</span>
                    <button onclick="addToCart('${purchase.key.id}')">+</button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <h3>Total cart's sum: <span id="TotalSum">${totalSum==null?0:totalSum}</span> UAH</h3>
    <%--<br>--%>
    <%--<button onclick="recount()"><font size="2" style="shape-rendering: crispEdges">Пересчитать</font></button>--%>
    <%--<br>--%>
    <button onclick="makeOrder('${userId}')"><font size="2" style="shape-rendering: crispEdges">Make order</font>
    </button>

</c:if>
<%@ include file="includes/footer.jsp" %>
