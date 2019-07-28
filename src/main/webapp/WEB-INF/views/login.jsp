<%@ include file="includes/header.jsp" %>

<c:if test="${showLoginForm}">
    <center>
        <form action='./login' method='post'>
            <table id="myTableFormatting" border=0>
                <tr>
                    <td>Login: </td>
                    <td><input type='text' name='login' value='' autofocus placeholder="enter e-mail as login" required/>
                    </td>
                </tr>
                <br>
                <tr>
                    <td>Password: </td>
                    <td><input type='password' name='password' placeholder="minimum 4 symbols" min="4" required/></td>
                </tr>
                <td></td>
                <td align='right'><div id="myButtonsFormatting"><input type='submit' value='Submit'/></div></td>
                </tr>
            </table>
        </form>
    </center>
</c:if>


<c:if test="${message != null}">
    <span id="RegMsg">${message}</span>
</c:if>

<%@ include file="includes/footer.jsp" %>
