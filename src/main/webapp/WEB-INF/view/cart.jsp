<%@ include file="includes/header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%--<br>cart.jsp: tmp: ${tmp}--%>

<c:set var="userLine" value=""/>
<c:set var="userId" value="${sessionScope.userId}"/>
<c:if test="${username != null && sessionScope.cartSize > 0}">
    <c:set var="userLine" value="${username}, Вы выбрали следующие товары:"/>
</c:if>
<c:set var="productsInCart" value="${sessionScope.productsInCart}"/>
<br><br>
<h3><div id="info">${userLine}</div> </h3>
<%--<c:set var="total" value="${sessionScope.totalSum}"/>--%>
<c:set var="total" value="0"/>

<br>
<h3>${message}</h3>

<script>var sum =0;</script>
<c:if test="${sessionScope.cartSize > 0}">
    <div id="cart">
        <table border=1>
            <tr><td>Наименование товара</td><td>цена</td><td>Количество</td>
            <c:forEach var="purchase" items="${productsInCart}">
                <tr>
                    <td><div id="productName"><c:out value="${purchase.key.name}"/></div></td>
                    <td><div id="price"><c:out value="${purchase.key.price}"/> грн.</div></td>
                    <td><button onclick="minus('${purchase.key.id}')">-</button>
                        <span id='q${purchase.key.id}'>${purchase.value}</span>
                        <button onclick="plus('${purchase.key.id}')">+</button>
                    </td>
                </tr>
            <button type="button" onclick="document.write((${purchase.key.price}) * (document.getElementById('q'+ ${purchase.key.id}).innerHTML))">calc!</button>
            <script>function  sum() {var totalsum = (${purchase.key.price}) * (document.getElementById('q'+ ${purchase.key.id}).innerHTML);} </script>
            <button type="button" id="totalLocal"></button>
            <%--<br><button id="sum"  onclick="totalSum('${userId}')"><font size ="3">sum</font></button>--%>
            </c:forEach>
        </table>
        <h3>Общая сумма заказа: <div id="sum">0</div> грн.</h3>
        <br><button onclick="sum()"><font size ="2" style="shape-rendering: crispEdges">Пересчитать</font></button>
        <br><button onclick="makeOrder('${userId}')"><font size ="2" style="shape-rendering: crispEdges">Оформить заказ</font></button>

    </div>
</c:if>

<%@ include file="includes/footer.jsp" %>

<script src="static/js/jquery-3.3.1.js"></script>
<%--<script src="static/js/updateCart.js"></script>--%>

<script>
    function totalSum(purchaseId) {
        var quantity = document.getElementById('q' + purchaseId).innerHTML;
        var price = document.getElementById('price').innerHTML;
        var total = quantity * price;
        console.warn("quantity:" +quantity);
        document.getElementById('sum').innerHTML = total;

    }

    function minus(purchaseId) {
        var elem = document.getElementById('q' + purchaseId);
        if (elem.innerHTML > 0) {
            elem.innerHTML = +elem.innerHTML - 1;
            $.ajax({
                type : "GET",
                url : "./cart",
                data : "removePurchaseId=" + (purchaseId + ":" + qnt),
                success : function(response) {
                    alert('Товар удален');
                }
            });
        }
    }

    function plus(purchaseId) {
        var elem = document.getElementById('q' + purchaseId);
        var qnt = +elem.innerHTML + 1;
        elem.innerHTML = qnt;
        alert('1 Товар добавлен');
        $.ajax({
            type: "POST",
            url: "./cart",
            data: "addPurchaseId=" + purchaseId + "&qnt=" + qnt,
            success : function(response) {
                alert('2 Товар добавлен');
            }
        });
    }

    function makeOrder(userId) {
        var qnt = document.getElementById('q' + userId).innerHTML;
        $.ajax({
            type : "POST",
            url : "./order",
            data : "orderUserID=" + (myId),
            success : function(response) {
                alert('Заказ оформлен');
            }
        });
    }
</script>