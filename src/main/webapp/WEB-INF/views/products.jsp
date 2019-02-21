<%@ include file="includes/header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<c:if test="${products.size() > 0}">
    <table border=1>
        <%--<div class="productsTable">--%>
            <c:forEach var="product" items="${products}">
                <td>
                <%--<div class="eachProduct">--%>
                    <table border=0>
                        <td><img src="resources/images/${product.image}" alt="" width="200" height="280"></td>
                        <tr><td><div id="productName"><c:out value="${product.name}"/></div></td></tr>
                        <tr><td><div id="price"><c:out value="${product.price}"/> грн.</div></td></tr>
                    </table>
                <%--</div>--%>
                </td>
            </c:forEach>
        <%--</div>--%>
    </table>
</c:if>

<%@ include file="includes/footer.jsp" %>