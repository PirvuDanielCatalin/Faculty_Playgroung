// Book Show
$(function () {
    configRating();
    configComments();

    $(".edit-book-btn").on("click", function () {
        let BookId = $(".add-product-btn").attr("book-id");
        window.location = "/Books/Edit/" + BookId;
    });

    $('#confirmDeleteBook').on('click', function () {
        let BookId = $(".add-product-btn").attr("book-id");
        $.ajax({
            type: "DELETE",
            url: "/Books/Delete/" + BookId,
            success: function (response) {
                console.log(response);
                window.location = "/Books"
            },
            error: function () {
                console.log('Eroare la ștergerea cărții!');
            }
        });
    });

    $(".add-product-btn").on('click', function () {
        if ($(this).attr("isAuthenticated") == "false")
            window.location = "/Account/Login";
        let BookId = $(this).attr('book-id');
        if (sessionStorage.getItem(BookId)) {
            let newValue = parseInt(sessionStorage.getItem(BookId)) + 1;
            sessionStorage.setItem(BookId, newValue.toString());
        } else {
            sessionStorage.setItem(BookId, '1');
        }
    });
});

function addToSession() {
    let BookId = $(this).attr('book-id');
    if (sessionStorage.getItem(BookId)) {
        let newValue = parseInt(sessionStorage.getItem(BookId)) + 1;
        sessionStorage.setItem(BookId, newValue.toString());
    } else {
        sessionStorage.setItem(BookId, '1');
    }
}

function configRating() {
    $('.star').on("click", function () {
        $('.star').removeClass('star-selected');
        var count = $(this).attr('name');
        for (var i = 0; i < count; i++) {
            $('.star').eq(i).addClass('star-selected');
        }
        let data = {
            BookId: $(".add-product-btn").attr("book-id"),
            Value: count
        }
        $.ajax({
            type: "POST",
            url: "/Rating/NewOrEdit",
            data: data,
            success: function (response) {
                console.log(response);
            },
            error: function () {
                console.log('Eroare la prelucrarea ratingului!');
            }
        });
    });
}

function configComments() {
    $(".comment-textarea").textareaAutoSize();

    // Afisare textarea pt scris comentariu nou
    $(".comments-add-btn").on("click", function () {
        $(".comments-add-input-div").css("display", "flex");
    });

    // Salvare comentariu nou dupa validare 
    $(".comment-post-btn").on("click", function () {
        let $textarea = $(this).closest(".comments-add-input-div").find("textarea");
        let text = $textarea.val();

        if (text.trim() != "") {
            $textarea.css("border-color", "rgb(162, 162, 162)");
            let data = {
                BookId: $(".add-product-btn").attr("book-id"),
                Comentariu: text
            }
            $.ajax({
                type: "POST",
                url: "/BookComments/New",
                data: data,
                success: function (response) {
                    console.log(response);
                    if (response == "Succes" || response == "NoRight")
                        window.location.reload();
                    else {
                        $textarea.css("border-color", "red");
                        $textarea.attr("placeholder", "A apărut o eroare!");
                    }
                },
                error: function () {
                    console.log('Eroare la salvarea comentariului!');
                }
            });
        } else {
            $textarea.css("border-color", "red");
            $textarea.attr("placeholder", "Câmpul este obligatoriu!");
        }
        return false;
    });

    // Schimbare elemente pt editarea comentariului
    $(".comment-edit-btn").on("click", function () {
        $(this).closest(".comment-div").find(".comment-text p").css("display", "none");
        $(this).closest(".comment-div").find(".comment-text form").css("display", "flex");
    });

    // Salvare comentariu modificat dupa validare
    $(".replacement-textarea-save-btn").on("click", function () {
        let $textarea = $(this).closest("form").find("textarea");
        let text = $textarea.val();
        let BookCommentId = $(this).closest(".comment-div").find("input[name=bookCommentId]").val();

        if (text.trim() != "") {
            $textarea.css("border-color", "rgb(162, 162, 162)");
            $textarea.attr("placeholder", "");
            let data = {
                BookId: $(".add-product-btn").attr("book-id"),
                Comentariu: text
            }
            $.ajax({
                type: "PUT",
                url: "/BookComments/Edit/" + BookCommentId,
                data: data,
                success: function (response) {
                    console.log(response);
                    if (response == "Succes" || response == "NoRight")
                        window.location.reload();
                    else {
                        $textarea.css("border-color", "red");
                        $textarea.attr("placeholder", "A apărut o eroare!");
                    }
                },
                error: function () {
                    console.log('Eroare la modificarea comentariului!');
                }
            });
        } else {
            $textarea.css("border-color", "red");
            $textarea.attr("placeholder", "Câmpul este obligatoriu!");
        }
        return false;
    });

    // Setare modal pt stergere comentariu
    $(".comment-delete-btn").on("click", function () {
        let BookCommentId = $(this).closest(".comment-div").find("input[name=bookCommentId]").val();
        $("#confirmDeleteComment").attr("comment-id", BookCommentId);
    });

    // Stergere comentariu
    $('#confirmDeleteComment').on('click', function () {
        let BookCommentId = $(this).attr("comment-id");
        $.ajax({
            type: "DELETE",
            url: "/BookComments/Delete/" + BookCommentId,
            success: function (response) {
                console.log(response);
                window.location.reload();
            },
            error: function () {
                console.log('Eroare la ștergerea comentariului!');
            }
        });
    });
}