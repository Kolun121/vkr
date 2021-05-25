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

$('#create').on( 'click', function () {
    
    $.ajax({
        url: document.URL + '/new',
        type: "POST",
        success: function(result){
            window.location.replace(document.URL + '/' + result);
        }
    });  
});