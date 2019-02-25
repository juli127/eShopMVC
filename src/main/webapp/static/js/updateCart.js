function minus(myId) {
    var elem = document.getElementById('q' + myId);
    //out.write("+elem.innerHTML:  " + elem.innerHTML);
    if (+elem.innerHTML > 1) {
        elem.innerHTML = +elem.innerHTML - 1;
    }
    elem.innerHTML = "YOU CLICKED ME! ---";
}

function plus(myId) {
    var elem = document.getElementById('q' + myId);
   // out.write("+elem.innerHTML:  " + elem.innerHTML);
    elem.innerHTML = +elem.innerHTML + 1;
    // alert(+elem.innerHTML);
    elem.innerHTML = "YOU CLICKED ME! +++";
}

function buy(myId) {
    var qnt = document.getElementById('q' + myId).innerHTML;
    $.ajax({
        type : "GET",
        url : "./cart",
        data : "selectedProductID=" + (myId + ":" + qnt),
        success : function(response) {
            alert('Товар добавлен в корзину');
        }
    });
}

function order(myId) {
    var qnt = document.getElementById('q' + myId).innerHTML;
    $.ajax({
        type : "GET",
        url : "./order",
        data : "orderUserID=" + (myId),
        success : function(response) {
            alert('Заказ оформлен');
        }
    });
}