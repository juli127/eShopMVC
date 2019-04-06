<%@ include file="includes/header.jsp" %>

<center>
    <form action='./admin/products/add' method='post'>
        <h4><div id="infoGreen"> Enter the new product's characteristics:</div></h4>
        <table id="myTableFormatting" border=0>
            <tr>
                <td>Name: </td>
                <td><input type='text' name='name' value='' autofocus required/></td>
            </tr><br>
            <tr>
                <td>Category: </td>
                <td><select name="category">
                    <option value="dress" selected>dress</option>
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
                <td><input type='textarea' name='description' value='' maxlength="300"/></td>
            </tr><br>
            <tr>
                <td>Image: </td>
                <td><input type='text' name='image' value='' placeholder="file name from /webapp/static/images"/></td>
            </tr><br>

            <td></td>
            <td align='right'><div id="myButtonsFormatting"><input type='submit' value='Submit'/></div></td>
            </tr>
        </table>
        <input type="text" name="action" value="addProduct" hidden="true"/>
    </form>
</center>

<%@ include file="includes/footer.jsp" %>