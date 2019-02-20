<%@ include file="includes/header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<form action='ProductServlet' method='post'>
	<div>
		<center>
			<select id="selectedCateg" name="selectedCateg">
				<option value="All categories" selected>All categories</option>
				<c:forEach var="i" begin="0" end="${categories.size() - 1}">
					<option value="${categories[i]}">${categories[i]}</option>
				</c:forEach>
			</select>
			<input type='submit' value='select'/>
		</center>
	</div>
	<br>

	<center><h3>List of products:</h3></center>
	<br>
	<center>
		<table border='1'>
			<tr>
				<td>#</td>
				<td>name</td>
				<td>category</td>
				<td>price</td>
				<td>description</td>
				<td>image</td>
			</tr>
			<c:forEach var="i" begin="0" end="${products.size() - 1}">
				<tr>
					<td>${i+1}</td>
					<td><c:out value="${products[i].name}"/></td>
					<td><c:out value="${products[i].category}"/></td>
					<td><c:out value="${products[i].price}"/></td>
					<td><c:out value="${products[i].description}"/></td>
					<td> <img src="${products[i].image}" width='100' height='100'></td>
				</tr>
			</c:forEach>
		</table>
	</center>
</form>
	<%--<div id="table">--%>
	<%--<table border = 1>--%>
        <%--<tr><td>--%>
			<%--<table border = 0>--%>
			<%--<tr><td><img src="resources/images/dusty-blue-400x650_3400.jpg" alt="" width="140" height="200"/></td></tr>--%>
			<%--<tr><td height="40">Some goods' Some goods' descriptionSome goods' description</td></tr>--%>
			<%--<tr><td><div id="price">Price: 4566 грн</div></td></tr>--%>
			<%--<tr><td><a href="./cart?productID=${productID}></a><input type="submit" name="buy" value="buy"></td></tr>--%>
			<%--</table>--%>
			<%--</td>--%>
		<%----%>
		<%--<td>--%>
			<%--<table border = 0>--%>
			<%--<tr><td><img src="resources/images/evening_dress_felicia_4500.jpg" alt="" width="140" height="200"/></td></tr>--%>
			<%--<tr><td height="40">Some goods' description Some goods' descriptionSome goods' descriptionSome goods' description</td></tr>--%>
			<%--<tr><td><div id="price">Price: 2345 грн</div></td></tr>--%>
			<%--</table>--%>
			<%--</td>--%>
		<%--<td>	--%>
			<%--<table border = 0>--%>
			<%--<tr><td><img src="resources/images/evening_dress_f1_2300.jpg" alt="" width="140" height="200"/></td></tr>--%>
			<%--<tr><td height="40">Some goods' description Some goods' descriptionSome goods' description</td></tr>--%>
			<%--<tr><td><div id="price">Price: 5 885 грн</div></td></tr>--%>
			<%--</table>--%>
			<%--</td>--%>
		<%--</tr>--%>
	<%--</table>--%>
	<%--</div>--%>

<%@ include file="includes/footer.jsp" %>