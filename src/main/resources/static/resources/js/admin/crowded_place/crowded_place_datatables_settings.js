var columnDefs = [{
    orderable: false,
    targets: [0,1]
},
{
    "targets": 1,
    "data": "id",
    "render": function ( data, type, row, meta ) {
        return '<a text="Перейти" href="' + document.URL + '/' + data + '">Перейти</a>';
    }
}];
var columns = [
    {"data": "title", 
        "width": "90%",
        "defaultContent": "<i>Не указан</i>"

    },
    {"data": "id", "width": "10%"}
];

$('#create').on( 'click', function () {
    $.ajax({
        url: document.URL + '/new',
        type: "POST",
        success: function(result){
            t.ajax.reload();
        }
    });  
});