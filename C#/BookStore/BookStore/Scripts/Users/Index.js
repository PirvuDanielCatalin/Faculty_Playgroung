// Users Index
$(function () {
    $(".save-roles-btn").on("click", function () {
        let $usersIds = $("td.tbl-col-1");
        let $usersRoles = $("td.tbl-col-4 select");
        let $data = {};
        for (let i = 0; i < $usersIds.length; i++) {
            $data[$usersIds.eq(i).text()] = $usersRoles.eq(i).val();
        }
        $.ajax({
            type: "POST",
            url: "/Users/UpdateUsersRoles",
            data: { data: JSON.stringify($data) },
            success: function (response) {
                if (response == "Succes")
                    window.location.reload();
                else
                    alert(response);
            },
            error: function () {
                alert('Eroare la preluarea listei!');
            }
        });
    });
});