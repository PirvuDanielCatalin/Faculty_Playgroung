// Books Index - Table Style
$(function () {
    // Redirect catre pagina cartii (Show)
    $(".book-show-btn").on("click", function () {
        let BookId = $(this).closest("tr").find("input[name=BookId]").val();
        window.location = "/Books/Show/" + BookId;
    });

    // Redirect catre pagina cartii (Edit)
    $(".book-edit-btn").on("click", function () {
        let BookId = $(this).closest("tr").find("input[name=BookId]").val();
        window.location = "/Books/Edit/" + BookId;
    });

    $("#books-table").DataTable();
});