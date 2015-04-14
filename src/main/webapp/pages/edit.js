function initTimer(table) {
    setInterval(function(){
        table.ajax.reload();
    }, 60000);
}

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

$(document).ready(function() {
    var table = $('#grid').DataTable( {
        "ajax": "/online/rest/outages/" + getParameterByName("id"),
        "pagingType": "full",
        dom: 'T<"clear">lfrtip',
        tableTools: {
            "sRowSelect": "single",
            aButtons: []
        },
        "ordering": false,
        "columns": [
            { "data": "start" },
            { "data": "end" },
            { "data": "duration" }
        ],
        "createdRow": function(row, data, index) {
            console.log(data);
            if (data.end === "") {
                $("td", row).addClass("systemOutageRow");
            }
        }
    });

    initTimer(table);
});
