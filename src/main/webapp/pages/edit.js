var edit_dataTable;

function initTimer() {
    setInterval(function(){
        edit_dataTable.ajax.reload();
    }, 60000);
}

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

$(document).ready(function() {
    edit_dataTable = $('#grid').dataTable( {
        "ajax": "/online/rest/outages/" + getParameterByName("id"),
        "pagingType": "full",
        dom: 'T<"clear">lfrtip',
        tableTools: {
            "sRowSelect": "single",
            aButtons: []
        },
        "columns": [
            { "data": "id" },
            { "data": "name" },
            { "data": "outage" },
            { "data": "lastSeen" },
            { "data": "created" }
        ],
        createdRow: function(row, data, index) {
            if (data[2] == "1") {
                $("td", row).addClass("systemOutageRow");
            }
        }
    });

    $('#grid tbody').on('dblclick', 'tr', function() {
	DataTablesExt.editDoubleClick(this);
    });

    initTimer();
});
