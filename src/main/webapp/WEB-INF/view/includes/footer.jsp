<%@ page contentType="text/html; charset=UTF-8" %>
</div><%--for id="content" --%>
</div><%--for id="page" --%>
<%--</p>--%>

<br><br>
<div id="footer" class="footer">
	<p>Copyright (c) by Kramarenko Iuliia</p>
</div>

</div> <%--for id="wrapper"--%>
</body>
</html>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">

	function sum() {
		// ???? как перебрать все строки в таблице????
		//  (${purchase.key.price}) * (document.getElementById('q'+ ${purchase.key.id}).innerHTML);
		var total = 0;
		alert("enter sum() with" + productsIds.toString());
		for (i=0; i< productsIds.size(); i++) {
			alert('prod:' + prod);
			var quantity = document.getElementById('q${productsIds[i]}').innerHTML;
			var price = document.getElementById('price${productsIds[i]}').innerHTML;
			var total = total + quantity * price;
			alert('quantity:' + quantity, ", price:" + price + ", total: " + total);
		}
		console.log("total: " + total);
		alert("finally: total: " + total);
		document.getElementById('sum').innerHTML = total;
	}

	function plus(purchaseId) {
		var elem = document.getElementById('q' + purchaseId);
		var qnt = +elem.innerHTML + 1;
		elem.innerHTML = qnt;
		alert('1 Товар добавлен');
		$.ajax({
			type: "POST",
			url: "./cart",
			data: { addPurchase : purchaseId + ":" + qnt },
			success: function (response) {
				alert('2 Товар добавлен');
			}
		});
	}

	function minus(purchaseId) {
		var elem = document.getElementById('q' + purchaseId);
		var qnt = +elem.innerHTML;
		if (elem.innerHTML > 0) {
			elem.innerHTML = qnt - 1;
			$.ajax({
				type: "POST",
				url: "./cart",
				data: "removePurchase=" + (purchaseId + ":" + qnt),
				success: function (response) {
					alert('Товар удален');
				}
			});
		}
	}
</script>
<%--<script src="./js/updateCart.js"></script>--%>
<%--<script src="./../../js/updateCart.js"></script>--%>
