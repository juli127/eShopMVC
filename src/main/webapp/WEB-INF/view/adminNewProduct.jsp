<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="includes/header.jsp" %>

<center>
    <h2>Enter the new product's characteristics:</h2>
    <form action='./admin/products/add' method='post'>
        <table id="myTableFormatting" border=0>
            <tr>
                <td>Name: </td>
                <td><input type='text' name='name' value='' autofocus required/></td>
            </tr><br>
            <tr>
                <td>Category: </td>
                <td><select name="category">
                    <option value="dress" selected>please select:</option>
                    <option value="shoes">shoes</option>
                    <option value="accessories">accessories</option>
                </select></td><br>
            </tr>
            <tr>
                <td>Price: </td>
                <td><input type='number' name='price' value='' required/></td>
            </tr><br>
            <tr>
                <td>Description: </td>
                <td><input type='textarea' name='description' value=''/></td>
            </tr><br>
            <tr>
                <td>Image: </td>
                <td><input type='text' name='image' value='' placeholder="file name from /webapp/static/images"/></td>
            </tr><br>

            <td></td>
            <td align='right'><div id="myButtonsFormatting"><input type='submit' value='Submit'/></div></td>
            </tr>
        </table>
    </form>
</center>

<%@ include file="includes/footer.jsp" %>