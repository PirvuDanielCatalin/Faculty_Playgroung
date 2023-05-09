// Partner Requirement Index
$(function () {
    configForRoleColaborator();
    configForRoleAdmin();
    $("#requests-table").DataTable();
});

function configForRoleColaborator() {
    // Redirect catre pagina cartii (New)
    $(".request-add-btn").on("click", function () {
        window.location = "/Books/New";
    });

    // Redirect catre pagina cartii (Show)
    $(".request-show-btn").on("click", function () {
        let BookId = $(this).closest("tr").find(".tbl-col-4").text();
        window.location = "/Books/Show/" + BookId;
    });

    // Redirect catre pagina cartii (Edit)
    $(".request-edit-btn").on("click", function () {
        let BookId = $(this).closest("tr").find(".tbl-col-4").text();
        window.location = "/Books/Edit/" + BookId;
    });

    // Setare modal pt stergere cerere
    $(".request-delete-btn").on("click", function () {
        let RequestId = $(this).closest("tr").find("input[name=IdCerere]").val();
        $("#confirmDeleteRequest").attr("request-id", RequestId);
    });

    // Stergere cerere
    $('#confirmDeleteRequest').on('click', function () {
        let RequestId = $(this).attr("request-id");
        $.ajax({
            type: "DELETE",
            url: "/PartnerRequirements/Delete/" + RequestId,
            success: function (response) {
                console.log(response);
                window.location.reload();
            },
            error: function () {
                console.log('Eroare la ștergerea cererii!');
            }
        });
    });
}

function configForRoleAdmin() {
    $(".request-accept-btn, .request-reject-btn").on("click", function () {
        let RequestId = $(this).closest("tr").find("input[name=IdCerere]").val();
        let newStatus = "";
        if ($(this).hasClass("request-accept-btn"))
            newStatus = "Accept";
        else if ($(this).hasClass("request-reject-btn"))
            newStatus = "Reject";

        $.ajax({
            type: "POST",
            url: "/PartnerRequirements/ChangeRequirementStatus/" + RequestId,
            data: { newStatus: newStatus },
            success: function (response) {
                console.log(response);
                window.location.reload();
            },
            error: function () {
                console.log('Eroare la prelucrarea ratingului!');
            }
        });
    });
}