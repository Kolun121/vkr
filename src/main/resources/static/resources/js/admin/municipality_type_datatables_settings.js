var columnDefs = [{
    orderable: false,
    targets: [2]
},
{
    "targets": 2,
    "data": "id",
    "render": function ( data, type, row, meta ) {
        console.log(data);
        return '<a text="Перейти" href="' + document.URL + '"/"' + data + '">Перейти</a>';
    }
}];
var columns = [
    {"data": "id", "width": "10%"},
    {"data": "lastName", 
        "width": "80%",
        "defaultContent": "<i>Не указана</i>"

    },
    {"data": "id", "width": "10%"}
];