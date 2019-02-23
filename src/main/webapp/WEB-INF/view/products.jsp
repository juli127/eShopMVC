<%@ include file="includes/header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%--<br>products.jsp: user: ${sessionScope.user}--%>
<%--<br>products.jsp: userName: ${sessionScope.userName}--%>
<%--<br>products.jsp: selectedCateg: ${sessionScope.selectedCateg}--%>


<c:if test="${sessionScope.products.size() > 0}">
    <table border=1>
        <c:forEach var="product" items="${sessionScope.products}">
            <%--<div class="product">--%>
                <td>
                    <table border=0>
                        <td><img src="resources/images/${product.image}" alt="" width="200" height="280"></td>
                        <tr><td><div class="productName"><c:out value="${product.name}"/></div></td></tr>
                        <tr><td><div class="price"><c:out value="${product.price}"/> грн.</div></td></tr>
                    </table>
                </td>
            <%--</div>--%>
        </c:forEach>
    </table>
</c:if>

<%@ include file="includes/footer.jsp" %>