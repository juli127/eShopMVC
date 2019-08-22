<%@ include file="includes/header.jsp" %>

<c:if test="${products.size() > 0}">
    <c:forEach var="product" items="${products}">
        <div class="productsTable">
            <table border="1">
                <td>
                    <img src="<spring:url value="${resourcesPath}/images/${product.image}"/>" alt="" width="230" height="300">
                    <div class="productName" id="productName"><c:out value="${product.name}"/></div>
                    <div id="price"><c:out value="${product.price}"/> UAH</div>
                    <div class="productDescription">
                        <input type='button' onclick="minus('${product.productId}')" value='-' />
                        <span id='pq${product.productId}'>1</span>
                        <input type='button' onclick="plus('${product.productId}')" value='+' />
                        <input type='button' onclick="buy('${user.userId}', '${product.productId}')" value='Buy'/>
                    </div>
                </td>
            </table>
        </div>
    </c:forEach>
</c:if>

<%@ include file="includes/footer.jsp" %>

