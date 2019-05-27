<%@ include file="includes/header.jsp" %>

<c:set var="users" value="${usersList}"></c:set>
<c:set var="products" value="${productsList}"></c:set>

<c:if test="${users != null}">
    <div id="info">All users:</div><br><br>
    <table id="myTable" border=1>
        <tr id="tableTitle">
            <td>Login</td>
            <td>Name</td>
            <td>Address</td>
        </tr>

        <c:forEach var="user" items="${users}">
            <tr>
                <td><c:out value="${user.login}"/></td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.address}"/></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<br><line/><br>

<c:if test="${products != null}">
    <div id="info">All products:</div>
    <a href="/admin/products/add">Add new product</a>
    <br>
    <table id="myTable" border=1>
        <tr id="tableTitle">
            <td>Name</td>
            <td>Category</td>
            <td>Price</td>
            <td>Image</td>
            <td></td>
        </tr>

        <c:forEach var="product" items="${products}">
            <tr>
                <td><c:out value="${product.name}"/></td>
                <td><c:out value="${product.category}"/></td>
                <td><c:out value="${product.price}"/></td>
                <td><c:out value="${product.image}"/></td>
                <td><a href="/admin/products/delete?${product.productId}">delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<br>


<%@ include file="includes/footer.jsp" %>
