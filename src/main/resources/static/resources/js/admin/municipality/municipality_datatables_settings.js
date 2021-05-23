var columnDefs = [{
    orderable: false,
    targets: [2]
},
{
    "targets": 2,
    "data": "id",
    "render": function ( data, type, row, meta ) {
        return '<a text="Перейти" href="' + document.URL + '/' + data + '">Перейти</a>';
    }
}];
var columns = [
    {"data": "id", "width": "10%"},
    {"data": "title", 
        "width": "80%",
        "defaultContent": "<i>Не указана</i>"

    },
    {"data": "id", "width": "10%"}
];