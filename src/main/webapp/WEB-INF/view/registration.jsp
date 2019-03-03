<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="includes/header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<c:if test="${sessionScope.regErrors.size() > 0}" >
    <c:forEach var="regError" items="${sessionScope.regErrors}">
        <script>
            var elem = document.getElementById("regError.getKey()");
            elem.innerText = regError.getValue();
            elem.color = '#930';
        </script>
    </c:forEach>
</c:if>

<form method="POST" action="/registration">
    <table>
        <tr>
            <td>Login</td>
            <td><input type='email' required name='login' value="" autofocus/></td>
            <td><span id='regLogin'></span></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type='password' name='password'  value=""/></td>
            <span id='regPassword'></span>
        </tr>
        <tr>
            <td>Retype Password</td>
            <td><input type='password' name='repassword' value=""/></td>
            <td><span id='regRepassword'></span></td>
        </tr>
        <tr>
            <td>Name</td>
            <td><input type='text' name='name' value=""/></td>
            <td><span id='regName'></span></td>
        </tr>
        <tr>
            <td>Address</td>
            <td><input type='text' name='address' value=""/></td>
            <td><span id='regAddress'></span></td>
        </tr>
        <tr>
            <td>Comment</td>
            <td><input type='text' name='comment' value=""/></td>
            <td><span id='regComment'></span></td>
        </tr>
        <tr>
            <td colspan = "2">
                <input type = "submit" value = "Submit"/>
            </td>
        </tr>
    </table>
    <%--Login: <input type='email' required name='login' value="" autofocus/><span id='regLogin'></span>--%>
    <%--Password: <input type='password' name='password'  value=""/><span id='regPassword'></span>--%>
    <%--Retype Password:<input type='password' name='repassword' value=""/><span id='regRepassword'></span>--%>
    <%--Name:<input type='text' name='name' value=""/><span id='regName'></span>--%>
    <%--Address:<input type='text' name='address' value=""/><span id='regAddress'></span>--%>
    <%--Comment:<input type='text' name='comment' value=""/><span id='regComment'></span>--%>
    <%--<input type='button' value='Submit'/>--%>
</form>

<%@ include file="includes/footer.jsp" %>
