var leaderboard_cached = new Map();

$(".leaderboard-player").click(function () {

    var elt = $(this);

    var gameId = elt.attr("data-game-id");
    var userId = elt.attr("data-user-id");

    target = "#score-" + userId + "-" + gameId + "-content";
    console.log(target);


    if (elt.hasClass("open")) {
        elt.removeClass("open");
        elt.html("See details");
    } else {
        elt.addClass("open");
        elt.html("Hide details")
    }

    console.log(leaderboard_cached.has(gameId));
    if (!leaderboard_cached.has(gameId)) {
        console.log("Retrieving game info view for #" + gameId + " from server");
        $.ajax({
                url: "/game/" + gameId,
                method: "POST",
                success: function (data) {
                    console.log(data);
                    leaderboard_cached.set(gameId, data);
                    $(target).html(leaderboard_cached.get(gameId));
                }
            }
        );
    }
    else {
        console.log("Game info already in cache");
        $(target).html(leaderboard_cached.get(gameId));
    }

});

function sortTable() {
    var table, rows, switching, i, x, y, shouldSwitch;
    table = document.getElementById("myTable");
    switching = true;
    /* Make a loop that will continue until
    no switching has been done: */
    while (switching) {
        // Start by saying: no switching is done:
        switching = false;
        rows = table.getElementsByTagName("TR");
        /* Loop through all table rows (except the
        first, which contains table headers): */
        for (i = 1; i < (rows.length - 1); i++) {
            // Start by saying there should be no switching:
            shouldSwitch = false;
            /* Get the two elements you want to compare,
            one from current row and one from the next: */
            x = rows[i].getElementsByTagName("TD")[0];
            y = rows[i + 1].getElementsByTagName("TD")[0];
            // Check if the two rows should switch place:
            if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                // I so, mark as a switch and break the loop:
                shouldSwitch = true;
                break;
            }
        }
        if (shouldSwitch) {
            /* If a switch has been marked, make the switch
            and mark that a switch has been done: */
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
        }
    }
}