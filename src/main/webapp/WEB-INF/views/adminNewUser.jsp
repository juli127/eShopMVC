<%@ include file="includes/header.jsp" %>

<br><div id="info">Add new user:</div><br>
<form:form action="${contextPath}/admin/users/add" method="post" modelAttribute="userForm">
    <table id="myTable">
    <tr>
        <td>Login</td>
        <td><form:input type='email' required="true" path="login" value='${login}' autofocus="true" placeholder="enter e-mail as login"/></td>
        <form:errors path="login" />
    </tr>
    <tr>
        <td>Password</td>
        <td><form:password path="password" required="true" value="" placeholder="minimum 4 symbols"/></td>
    </tr>
    <tr>
        <td>Retype Password</td>
        <td><input type='password' name='repassword' value=""/></td>
    </tr>
    <tr>
        <td>Name</td>
        <td><form:input path="name" value='${name}' required="true" placeholder="required"/></td>
    </tr>
    <tr>
        <td>Address</td>
        <td><form:input path="address" value='${address}' required="true" placeholder="required"/></td>
    </tr>
    <tr>
        <td>Comment</td>
        <td><form:input path="comment" value='${comment}'/></td>
    </tr>
    <tr>
        <td colspan = "2" align="right">
            <input type = "submit" value = "Submit"/>
        </td>
    </tr>
    </table>
</form:form>

<br><span id="errorMsgText"><c:if test="${errorsMsg != null}">${errorsMsg}</c:if></span>
<%@ include file="includes/footer.jsp" %>
