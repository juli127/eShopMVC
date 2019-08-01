<%@ include file="includes/header.jsp" %>

<br><div id="info">Enter the new product's characteristics:</div><br>
<form:form action="${contextPath}/admin/products/add" method="post" modelAttribute="productForm">
        <table id="myTable">
            <tr>
                <td>Name: </td>
                <td><form:input type='text' path='name' value='' autofocus="true" required="true"/></td>
            </tr><br>
            <tr>
                <td>Category: </td>
                <td><form:select path="category" items="${categoryMap}" /></td><br>
            </tr>
            <tr>
                <td>Price: </td>
                <td><form:input path='price' value='' required="true"/></td>
            </tr><br>
            <tr>
                <td>Description: </td>
                <td><form:textarea path='description' value='' maxlength="300"/></td>
            </tr><br>
            <tr>
                <td>Image: </td>
                <td><form:input path='image' value='' placeholder="file name from /webapp/resources/images"/></td>
            </tr><br>

            <td></td>
            <td align='right'><div id="myButtonsFormatting"><input type='submit' value='Submit'/></div></td>
            </tr>
        </table>
    </form:form>

<%@ include file="includes/footer.jsp" %>