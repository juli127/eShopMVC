<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="includes/header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<c:if test="${sessionScope.regErrors.size() > 0}">
    <c:forEach var="regError" items="${sessionScope.regErrors}">
        <script>
            var elem = document.getElementById("regError.getKey()");
            elem.innerText = regError.getValue();
            elem.color = '#930';
        </script>
    </c:forEach>
</c:if>

<form method="POST" action="/registration">
    <fieldset>
        <legend>Registration form</legend>
        <p><label for="login">Login <em>*</em></label>
            <input type="email" id="login" value="" autocomplete="on" required autofocus/>
            <span id='regLogin'></span>
        </p>
        <p><label for="passw">Password <em>*</em></label>
            <input type="password" id="passw" autocomplete="off" required value=""/>
            <span id='regPassword'></span>
        </p>
        </p>
        <p><label for="repassword">Retype Password <em>*</em></label>
            <input type="password" id="repassword" autocomplete="off" required value=""/>
            <<span id='regRepassword'></span>
        </p>
        </p>
            <label for="name">Name <em>*</em></label>
            <input type="text" id="name" value="" required autocomplete="on"/>
            <span id='regName'></span>
        </p>
        </p>
            <label for="address">Address <em>*</em></label>
            <input type="text" id="address" value="" required autocomplete="on"/>
            <span id='regAddress'></span>
        </p>
        </p>
            <label for="comment">Comment </label>
             <input type="text" id="comment" value="" required autocomplete="on"/>
            <span id='regComment'></span>
        </p>
        </p>
    </fieldset>
    <p><input type="submit" value="Submit"></p>
    <%--Login: <input type='email' required name='login' value="" autofocus/><span id='regLogin'></span>--%>
    <%--Password: <input type='password' name='password'  value=""/><span id='regPassword'></span>--%>
    <%--Retype Password:<input type='password' name='repassword' value=""/><span id='regRepassword'></span>--%>
    <%--Name:<input type='text' name='name' value=""/><span id='regName'></span>--%>
    <%--Address:<input type='text' name='address' value=""/><span id='regAddress'></span>--%>
    <%--Comment:<input type='text' name='comment' value=""/><span id='regComment'></span>--%>
    <%--<input type='button' value='Submit'/>--%>
</form>

<%@ include file="includes/footer.jsp" %>
