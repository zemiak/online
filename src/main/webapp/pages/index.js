var index_dataTable;

function initTimer() {
    setInterval(function(){
        index_dataTable.ajax.reload();
    }, 60000);
}

$(document).ready(function() {
    index_dataTable = $('#grid').dataTable( {
        "ajax": "/online/rest/protected-systems",
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
