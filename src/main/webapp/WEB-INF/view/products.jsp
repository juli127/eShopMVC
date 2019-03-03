<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="includes/header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%--<br>products.jsp: userName: ${sessionScope.userName}--%>
<%--<br>products.jsp: selectedCateg: ${sessionScope.selectedCateg}--%>

<c:if test="${sessionScope.products.size() > 0}">
    <c:forEach var="product" items="${sessionScope.products}">
        <div class="productsTable">
            <table border="1">
                <td>
                    <img src="static/images/${product.image}" alt="" width="230" height="300">
                    <div class="productName" id="productName"><c:out value="${product.name}"/></div>
                    <div id="price"><c:out value="${product.price}"/> UAH</div>
                    <div class="productDescription">
                        <input type="hidden" id="productId" value="${product.id}"/>
                        <input type='button' onclick="minus('${product.id}')" value='-' />
                        <span id='productQnt${product.id}'>1</span>
                        <input type='button' onclick="plus('${product.id}')" value='+' />
                        <input type='button' onclick="buy('${product.id}')" value='Buy'/>
                    </div>
                </td>
            </table>
        </div>
    </c:forEach>
</c:if>

<%@ include file="includes/footer.jsp" %>

<script src="static/scripts/jquery-3.3.1.js"></script>
<script src="static/scripts/toCart.js"></script>