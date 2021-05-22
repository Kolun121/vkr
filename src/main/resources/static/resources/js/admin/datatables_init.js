var token = $("input[name='_csrf']").val();
    var header = "X-CSRF-TOKEN";
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
});

var t = $('#data_table').DataTable({
       "columnDefs": columnDefs,
       "processing": true,
       "serverSide": true,
       "lengthChange": false,
       "language": {
            "info":           "Отображается от _START_ до _END_ из _TOTAL_ записей" ,
            "infoPostFix":    "",
            "thousands":      ",",
            "lengthMenu":     "Show _MENU_ entries",
            "infoEmpty":      "Найдено 0 записей",
            "loadingRecords": "Загрузка...",
            "processing":     "Загрузка...",
            "search":         "Поиск:",
            "zeroRecords":    "Записей не найдено",
            "paginate": {
                "first":      "Первая",
                "last":       "Последняя",
                "next":       "След.",
                "previous":   "Пред."
            },
            "aria": {
                "sortAscending":  ": activate to sort column ascending",
                "sortDescending": ": activate to sort column descending"
            }
        },
       "ajax": {
           "url": document.URL,
           "type": "POST",
           "dataType": "json",
           "contentType": "application/json",
           "data": function (d) {
               console.log(d);
               return JSON.stringify(d);

           }
       },
       "columns": columns
});


$('#data_table tbody').on( 'click', 'tr', function () {
    $(this).toggleClass('selected');
    
    if(t.rows('.selected')[0].length !== 0){
        $('#delete').prop('disabled', false);
    } else{
        $('#delete').prop('disabled', true);
    }

} );

$('#create').on( 'click', function () {
    $.ajax({
        url: document.URL + '/new',
        type: "POST",
        success: function(result){
            t.ajax.reload();
        }
    });  
});  

$('#delete').on( 'click', function () {
    var dataArr = [];
    t.rows('.selected').every( function ( rowIdx, tableLoop, rowLoop ) {
        var d = this.data();
        dataArr.push(d);
    } );

    $.ajax({
        url: document.URL + '/delete',
        type: "DELETE",
        data: JSON.stringify(dataArr),
        contentType : 'application/json; charset=utf-8',
        success: function(result){
            t.ajax.reload();
            $('#delete').prop('disabled', true);
        }
    });  
});  