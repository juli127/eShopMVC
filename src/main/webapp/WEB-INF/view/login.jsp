<%@ include file="includes/header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%--<br>login: user: ${sessionScope.user}--%>
<%--<br>login: showLoginForm: ${sessionScope.showLoginForm}--%>
<%--<br>login: userName: ${sessionScope.userName}--%>
<%--<br>login: user: ${sessionScope.user}--%>
<%--<br>login: message: ${sessionScope.message}--%>

<c:if test="${showLoginForm}">
    <h3>Log-in</h3>
    <center>
        <form action='login' method='post'>
            <table border=0>
                <tr>
                    <td>Введите e-mail в качестве логина</td>
                    <td><input type='text' required name='login' value='<%=""%>'/>
                    </td>
                </tr>
                <br>
                <tr>
                    <td>Введите пароль</td>
                    <td><input type='password' required name='password'/></td>
                </tr>
                <td></td>
                <td align='right'><input type='submit' value='Submit'/></td>
                </tr>
            </table>
        </form>
    </center>
</c:if>

<c:if test="${message != null}">
    ${message}
</c:if>
<%@ include file="includes/footer.jsp" %>