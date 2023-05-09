// Shopping Cart
$(function () {
    initTableData();

    $(".pay-btn-confirm-msg").on("click", function () {
        sessionStorage.clear();
        window.location.reload();
    });
});

function initTableData() {
    let BookIds = Object.keys(sessionStorage);
    if (BookIds.length == 0) {
        $(".table-notempty").hide();
        $(".table-empty").show();
    }
    else {
        $(".table-notempty").show();
        $(".table-empty").hide();
        let totalPrice = 0;
        for (let i = 0; i < BookIds.length; i++) {
            var amount = sessionStorage.getItem(BookIds[i]);
            $.ajax({
                type: "POST",
                url: "/General/GetBookInfo/" + BookIds[i],
                success: function (response) {
                    let json = JSON.parse(response.split("'").join('"'));
                    totalPrice += (parseInt(amount) * parseInt(json.pret));
                    let row = $("<tr><td>" + BookIds[i] + "</td>" +
                        "<td>" + json.title + "</td>" +
                        "<td>" + amount + "</td>" +
                        "<td>" + (parseInt(amount) * parseInt(json.pret)) + "</td></tr>");
                    $("#session-table tbody").append(row);
                    $(".total-price").text("Total: " + totalPrice);
                },
                error: function () {
                    alert('Eroare la preluarea listei!');
                }
            });
        }
    }

}