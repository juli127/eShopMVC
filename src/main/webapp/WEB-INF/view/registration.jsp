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

<form method="POST">
    Login: <input type='email' required name='login' value="" id="regLogin"/>
    Password: <input type='password' name='password'  value="" id="regPassword"/>
    Retype Password:<input type='password' name='repassword' value="" id="regRepassword"/>
    Name:<input type='text' name='name' value="" id="regName"/>
    Address:<input type='text' name='address' value="" id="regAddress"/>
    Comment:<input type='text' name='comment' value="" id="regComment"/>
    <input type='button' value='Submit'/>
</form>

<%@ include file="includes/footer.jsp" %>
