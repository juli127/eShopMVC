<%@ include file="includes/header.jsp" %>

<form method="POST" action="<spring:url value="/admin/users/add"/>"/>

<br><div id="info">Add new user:</div><br>
<table id="myTable">
    <tr>
        <td>Login</td>
        <td><input type='email' required name='login' value='${login}' autofocus placeholder="enter e-mail as login"/></td>
    </tr>
    <tr>
        <td>Password</td>
        <td><input type='password' name='password' value="" placeholder="minimum 4 symbols"/></td>
    </tr>
    <tr>
        <td>Retype Password</td>
        <td><input type='password' name='repassword' value=""/></td>
    </tr>
    <tr>
        <td>Name</td>
        <td><input type='text' name='name' value='${name}' placeholder="required"/></td>
    </tr>
    <tr>
        <td>Address</td>
        <td><input type='text' name='address' value='${address}' placeholder="required"/></td>
    </tr>
    <tr>
        <td>Comment</td>
        <td><input type='text' name='comment' value='${comment}'/></td>
    </tr>
    <tr>
        <td colspan = "2" align="right">
            <input type = "submit" value = "Submit"/>
        </td>
    </tr>
</table>
</form>

<br><span id="errorMsgText"><c:if test="${errorsMsg != null}">${errorsMsg}</c:if></span></br>
<%@ include file="includes/footer.jsp" %>
