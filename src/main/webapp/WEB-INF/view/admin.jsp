<%@ include file="includes/header.jsp" %>

<c:set var="users" value="${sessionScope.usersList}" ></c:set>

<c:if test="${users != null}">
    <div id="info">All users:</div><br><br>
    <table id="cart" border=1>
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

<%@ include file="includes/footer.jsp" %>
