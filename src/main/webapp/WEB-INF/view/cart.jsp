<%@ include file="includes/header.jsp" %>

<c:set var="productsInCart" value="${cart.products}"/>
<c:set var="totalSum" value="${cart.totalSum}"/>

    <c:if test="${itemsCount > 0}">
        <div id="cart_content">
        <table id="cart" border=1>
                <tr id="tableTitle">
                    <td>Product's name</td>
                    <td>Price</td>
                    <td>Quantity</td>
                </tr>

            <c:forEach var="purchase" items="${productsInCart}">
                <tr>
                    <td><div id="productName"><c:out value="${purchase.key.name}"/></div></td>
                    <td><div id='price${purchase.key.id}'><c:out value="${purchase.key.price}"/> UAH</div></td>
                    <td><button onclick="deleteFromCart('${purchase.key.id}')">-</button>
                        <span id='q${purchase.key.id}'>${purchase.value}</span>
                        <button onclick="addToCart('${purchase.key.id}')">+</button></td>
                </tr>
            </c:forEach>
        </table><br>
        </div>

        <div id="summary_info">
            <div id="info">Total cart's sum: <span id="totalSumField">${totalSum==null?0:totalSum}</span> UAH</div><br>
            <span id="myButtonsFormatting"><button onclick="makeOrder('${cart.userId}')">Make order</button></span>
        </div>
    </c:if>

<%@ include file="includes/footer.jsp" %>
