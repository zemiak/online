/* global DataTablesExt */

function initTimer(table) {
    setInterval(function(){
        table.ajax.reload();
    }, 60000);
}

$(document).ready(function() {
    var table = $('#grid').DataTable( {
        "ajax": "/online/rest/protected-systems",
        "pagingType": "full",
        dom: 'T<"clear">lfrtip',
        tableTools: {
            "sRowSelect": "single",
            aButtons: []
        },
        "ordering": false,
        "columns": [
            { "data": "id" },
            { "data": "name" },
            { "data": "outage" },
            { "data": "lastSeen" },
            { "data": "created" }
        ],
        "createdRow": function(row, data, index) {
            if (data.outage === "YES") {
                $("td", row).addClass("systemOutageRow");
            }
        }
    });

    $('#grid tbody').on('dblclick', 'tr', function() {
	DataTablesExt.editDoubleClick(this);
    });

    initTimer(table);
});
