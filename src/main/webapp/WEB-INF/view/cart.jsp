<%@ include file="includes/header.jsp" %>

<c:set var="productsInCart" value="${cart.products}"/>

    <c:if test="${itemsCount > 0}">
        <br><div id="infoGreen">Your cart includes:</div>
        <br><br>

        <div id="cart_content">
        <table id="myTable" border=1>
                <tr id="tableTitle">
                    <td>Product's name</td>
                    <td>Price</td>
                    <td>Quantity</td>
                </tr>

            <c:forEach var="purchase" items="${productsInCart}">
                <tr>
                    <td><div id="productName"><c:out value="${purchase.key.name}"/></div></td>
                    <td><div id='price${purchase.key.productId}'><c:out value="${purchase.key.price}"/> UAH</div></td>
                    <td><button onclick="deleteFromCart('${purchase.key.productId}')">-</button>
                        <span id='q${purchase.key.productId}'>${purchase.value}</span>
                        <button onclick="addToCart('${purchase.key.productId}')">+</button></td>
                </tr>
            </c:forEach>
        </table><br>
        </div>

        <div id="summary_info">
            <div id="info">Total cart's sum: <span id="totalSumField">${totalSum==null?0:totalSum}</span> UAH</div><br>
            <span id="myButtonsFormatting"><button onclick="makeOrder('${user.userId}')">Make order</button></span>
        </div>
    </c:if>

<%@ include file="includes/footer.jsp" %>
