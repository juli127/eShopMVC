<%@ include file="includes/header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<c:set var="userLine" value=""/>
<c:if test="${username != null}">
	<c:set var="userLine" value="${username}, Вы выбрали следующие товары:"/>
</c:if>
<c:set var="cart" value="${sessionScope.cart}"/>
<c:if test="${cart != null}">
	<%--<c:set var="selectedGoods" value="${cart.produc}, Вы выбрали следующие товары:"/>--%>
</c:if>

<h2>${userLine}</h2>
<h2>message:${message}</h2>
<%--<c:if test="${selectedGoods.size() > 0}">--%>
	<%--<table border=1>--%>
			<%--&lt;%&ndash;<div class="productsTable">&ndash;%&gt;--%>
		<%--<c:forEach var="product" items="${products}">--%>
			<%--<td>--%>
					<%--&lt;%&ndash;<div class="eachProduct">&ndash;%&gt;--%>
				<%--<table border=0>--%>
					<%--<td><img src="resources/images/${product.image}" alt="" width="200" height="280"></td>--%>
					<%--<tr><td><div class="productName"><c:out value="${product.name}"/></div></td></tr>--%>
					<%--<tr><td><div class="price"><c:out value="${product.price}"/> грн.</div></td></tr>--%>
				<%--</table>--%>
					<%--&lt;%&ndash;</div>&ndash;%&gt;--%>
			<%--</td>--%>
		<%--</c:forEach>--%>
			<%--&lt;%&ndash;</div>&ndash;%&gt;--%>
	<%--</table>--%>
<%--</c:if>--%>

<%@ include file="includes/footer.jsp" %>
<script>

</script>

