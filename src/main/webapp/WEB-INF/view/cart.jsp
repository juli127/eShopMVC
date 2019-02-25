<%@ include file="includes/header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<c:set var="userLine" value=""/>
<c:set var="userId" value="${sessionScope.userId}"/>
<c:if test="${username != null && sessionScope.cartSize > 0}">
    <c:set var="userLine" value="${username}, Вы выбрали следующие товары:"/>
</c:if>
<c:set var="productsInCart" value="${sessionScope.productsInCart}"/>
<br><br>
<h3><div id="info">${userLine}</div> </h3>
<c:set var="totalSum" value="${sessionScope.totalSum}"/>


<br>
<h3>${message}</h3>

<script>var sum =0;</script>
<c:if test="${sessionScope.cartSize > 0}">
        <table id="cart" border=1>
            <tr id="title"><td>Наименование товара</td><td>цена</td><td>Количество</td>
            <c:forEach var="purchase" items="${productsInCart}">
                <tr>
                    <td><div id="productName"><c:out value="${purchase.key.name}"/></div></td>
                    <td><div id='price${purchase.key.id}'><c:out value="${purchase.key.price}"/> грн.</div></td>
                    <td><button onclick="minus('${purchase.key.id}')">-</button>
                        <span id='q${purchase.key.id}'>${purchase.value}</span>
                        <button onclick="plus('${purchase.key.id}')">+</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <h3>Общая сумма заказа: <span id="sum">${totalSum==null?0:totalSum}</span> грн.</h3>
        <br><button onclick="sum()"><font size ="2" style="shape-rendering: crispEdges">Пересчитать</font></button>
        <br><button onclick="makeOrder('${userId}')"><font size ="2" style="shape-rendering: crispEdges">Оформить заказ</font></button>

</c:if>

<%@ include file="includes/footer.jsp" %>

<script src="static/js/jquery-3.3.1.js"></script>
<%--<script src="static/js/updateCart.js"></script>--%>

<script>
    <%--(${purchase.key.price}) * (document.getElementById('q'+ ${purchase.key.id}).innerHTML);--%>
    function sum() {
       //* ???? как перебрать все строки в таблице???? for ( ){  */
            var quantity = document.getElementById('q${purchase.key.id}').innerHTML;
            var price = document.getElementById('price${purchase.key.id}').innerHTML;
            var total = quantity * price;
            console.log("quantity:" +quantity + ", price:" + price + ", total: " + total);
            document.getElementById('sum').innerHTML = total;
     //   }
    }

    function plus(purchaseId) {
        var elem = document.getElementById('q' + purchaseId);
        var qnt = +elem.innerHTML + 1;
        elem.innerHTML = qnt;
        alert('1 Товар добавлен');
        $.ajax({
            type: "POST",
            url: "./cart",
            data: "addPurchase=" + purchaseId + ":" + qnt,
            success : function(response) {
                alert('2 Товар добавлен');
            }
        });
    }

    function minus(purchaseId) {
        var elem = document.getElementById('q' + purchaseId);
        if (elem.innerHTML > 0) {
            elem.innerHTML = +elem.innerHTML - 1;
            $.ajax({
                type : "POST",
                url : "./cart",
                data : "removePurchase=" + (purchaseId + ":" + qnt),
                success : function(response) {
                    alert('Товар удален');
                }
            });
        }
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