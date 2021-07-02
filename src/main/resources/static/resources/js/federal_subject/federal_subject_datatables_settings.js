
var columnDefs = [{
    searchable: false,
    orderable: false,
    targets: [0, 3]
},
{
//    "targets": 2,
//    "data": "id",
//    "render": function ( data, type, row, meta ) {
//        return '<a text="Перейти" href="' + document.URL + '/' + data + '">Перейти</a>';
//    }
}];
var columns = [
    {
        "width":"2%",
         "className": 'details-control',
         "orderable": false,
         "data":null,
         "defaultContent": ''
     },
    {"data": "title", 
        "width": "30%",
        "defaultContent": "<i>Не указана</i>"

    },
    {"data": "federalSubjectType", 
        "width": "30%",
        "defaultContent": "<i>Не указана</i>"

    },
    {"data": "id", "width": "38%"}
];

var token = $("input[name='_csrf']").val();
    var header = "X-CSRF-TOKEN";
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
});
var t = $('#data_table').DataTable({
       "columns": columns,
       "columnDefs": columnDefs,
       "processing": true,
       "serverSide": true,
       "lengthChange": false,
        "order": [[1, 'asc']],
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
       }
});

$('#data_table tbody').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = t.row( tr );
        console.log(row.data());
        if ( row.child.isShown() ) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            row.child( format(row.data()) ).show();
            tr.addClass('shown');
        }
    } );
   
function format ( d ) {
//    var passport = d.passport;
//    if(passport.issuedBy === null){
//       passport.issuedBy = "Не указано кем выдан паспорт"; 
//    }
//    if(passport.issueDate === null){
//       passport.issueDate = "Дата выдачи не указана"; 
//    }
//    if(passport.passportID === null){
//       passport.passportID = "ID паспорта не указан"; 
//    }
//    
//    var passportRow = '<h3 class="card-header" style="border: 1px solid rgba(0,0,0,.125);" >Паспорт сотрудника</h3>' +
//            '<div class="card">' +
//    '<ul class="list-group list-group-flush">' +
//        '<li class="list-group-item">Паспорт ID: ' + passport.passportID + '</li>' +
//        '<li class="list-group-item">Кем выдан паспорт: ' + passport.issuedBy + '</li>' +
//        '<li class="list-group-item">Дата выдачи паспорта: ' + passport.issueDate + '</li>' +
//    '</ul>' +
//    '</div>';
//
//    var educations = d.educations;
//    var educationsRow = '<h3 class="card-header" style="border: 1px solid rgba(0,0,0,.125);">Образование сотрудника</h3>';
//    
//    if(Array.isArray(educations) && educations.length){
//        educationsRow = educationsRow + '<div class="card-deck justify-content-center">';
//        educations.forEach(function(education, i, educations) {
//            
//            if(education.specialty === null){
//                education.specialty = "Специальность не указана"; 
//            }
//            if(education.qualification === null){
//                education.qualification = "Квалификация не указана"; 
//            }
//            if(education.graduationDate === null){
//                education.graduationDate = "Дата выпуска не указана"; 
//            }
//            
//            educationsRow = educationsRow +
//            '<div class="card col-3">' +
//                '<div class="card-body">' +
//                    '<ul class="list-group list-group-flush">' +
//                        '<li class="list-group-item">Специальность: ' + education.specialty + '</li>' +
//                        '<li class="list-group-item">Квалификация: ' + education.qualification + '</li>' +
//                        '<li class="list-group-item">Дата выпуска: ' + education.graduationDate + '</li>' +
//                    '</ul>' +
//                '</div>' +
//            '</div>';
//            
//            if((i + 1)%3 === 0 && (i + 1)/3 >= 1){
//                educationsRow = educationsRow + '</div>';
//            }
//            if((i + 1)%3 === 0 && (i + 1)/3 >= 1){
//                educationsRow = educationsRow + '<div class="card-deck justify-content-center">';
//            }
//            
//        });
//    educationsRow = educationsRow + '</div>';
//    }else{
//        educationsRow = educationsRow +
//        '<div class="card-deck">'+
//            '<div class="card">' +
//                '<div class="card-body">' +
//                    '<h4 class="card-title">Образование сотрудника не указано</h4>' +
//                '</div>' +
//            '</div>'+
//        '</div>';
//    }
//    
//    var familyMembers = d.familyMembers;
//
//    var familyMembersRow = '<h3 class="card-header" style="border: 1px solid rgba(0,0,0,.125);">Члены семьи сотрудника</h3>';
//
//    if(Array.isArray(familyMembers) && familyMembers.length){
//        familyMembersRow = familyMembersRow + '<div class="card-deck justify-content-center">';
//        familyMembers.forEach(function(familyMember, i, familyMembers) {
//            if(familyMember.firstName === null){
//                familyMember.firstName = "Имя не указано"; 
//            }
//            if(familyMember.lastName === null){
//                familyMember.lastName = "Фамилия не указана"; 
//            }
//            if(familyMember.patronymic === null){
//                familyMember.patronymic = "Отчество не указано"; 
//            }
//            if(familyMember.relation === null){
//                familyMember.relation = "Родство не указано"; 
//            }
//            if(familyMember.birthDate === null){
//                familyMember.birthDate = "Дата рождения не указана"; 
//            }
//            
//            familyMembersRow = familyMembersRow +
//            '<div class="card col-3">' +
//                '<div class="card-body">' +
//                    '<ul class="list-group list-group-flush">' +
//                        '<li class="list-group-item">Имя: ' + familyMember.firstName + '</li>' +
//                        '<li class="list-group-item">Фамилия: ' + familyMember.lastName + '</li>' +
//                        '<li class="list-group-item">Отчество: ' + familyMember.patronymic + '</li>' +
//                        '<li class="list-group-item">Родство: ' + familyMember.relation + '</li>' +
//                        '<li class="list-group-item">Дата рождения: ' + familyMember.birthDate + '</li>' +
//                    '</ul>' +
//                '</div>' +
//            '</div>';
//            
//            if((i + 1)%3 === 0 && (i + 1)/3 >= 1){
//                familyMembersRow = familyMembersRow + '</div>';
//            }
//            if((i + 1)%3 === 0 && (i + 1)/3 >= 1){
//                familyMembersRow = familyMembersRow + '<div class="card-deck justify-content-center">';
//            }
//
//        });
//    familyMembersRow = familyMembersRow + '</div>';
//    }else{
//        familyMembersRow = familyMembersRow +
//        '<div class="card-deck">'+
//            '<div class="card">' +
//                '<div class="card-body">' +
//                    '<h4 class="card-title">Семья сотрудника не указана</h4>' +
//                '</div>' +
//            '</div>'+
//        '</div>';
//    }
//    
//  
//    var jobHistoryList = d.jobHistoryList;
//    var jobHistoryRow = '<h3 class="card-header" style="border: 1px solid rgba(0,0,0,.125);">Предыдущие должности сотрудника</h3>';
//
//    if(Array.isArray(jobHistoryList) && jobHistoryList.length){
//        jobHistoryRow = jobHistoryRow + '<div class="card-deck justify-content-center">';
//        jobHistoryList.forEach(function(job, i, jobHistoryList) {
//            if(job.jobTitle === null){
//                job.jobTitle = "Название должности не указано"; 
//            }
//            if(job.startDate === null){
//                job.startDate = "Дата вступления на должность не указана"; 
//            }
//
//            if(job.endDate === null){
//                job.endDate = "Дата выхода с должности не указана"; 
//            }
//            
//            jobHistoryRow = jobHistoryRow +
//            '<div class="card col-3">' +
//                '<div class="card-body">' +
//                    '<ul class="list-group list-group-flush">' +
//                        '<li class="list-group-item">Название должности: ' + job.jobTitle + '</li>' +
//                        '<li class="list-group-item">Дата вступления: ' + job.startDate + '</li>' +
//                        '<li class="list-group-item">Дата выхода: ' + job.endDate + '</li>' +
//                    '</ul>' +
//                '</div>' +
//            '</div>';
//            
//            if((i + 1)%3 === 0 && (i + 1)/3 >= 1){
//                jobHistoryRow = jobHistoryRow + '</div>';
//            }
//            if((i + 1)%3 === 0 && (i + 1)/3 >= 1){
//                jobHistoryRow = jobHistoryRow + '<div class="card-deck justify-content-center">';
//            }
//            
//        });
//    jobHistoryRow = jobHistoryRow + '</div>';
//    }else{
//        jobHistoryRow = jobHistoryRow +
//        '<div class="card-deck">'+
//            '<div class="card">' +
//                '<div class="card-body">' +
//                    '<h4 class="card-title">Предыдущие должности сотрудника не указаны</h4>' +
//                '</div>' +
//            '</div>'+
//        '</div>';
//    }
//    
//    var vacationsList = d.vacationsList;
//    var vacationRow = '<h3 class="card-header" style="border: 1px solid rgba(0,0,0,.125);">История отпусков сотрудника</h3>';
//
//    if(Array.isArray(vacationsList) && vacationsList.length){
//        vacationRow = vacationRow + '<div class="card-deck justify-content-center">';
//        vacationsList.forEach(function(vac, i, vacationsList) {
//            if(vac.vacationType === null){
//                vac.vacationType = "Тип отпуска не указан"; 
//            }
//            if(vac.startDate === null){
//                vac.startDate = "Дата выхода в отпуск не указана"; 
//            }
//
//            if(vac.endDate === null){
//                vac.endDate = "Дата выхода с отпуска  не указана"; 
//            }
//            console.log(vac.vacationType);
//            
//            vacationRow = vacationRow +
//            '<div class="card col-3">' +
//                '<div class="card-body">' +
//                    '<ul class="list-group list-group-flush">' +
//                        '<li class="list-group-item">Тип отпуска: ' + vac.vacationType + '</li>' +
//                        '<li class="list-group-item">Дата выхода в отпуск: ' + vac.startDate + '</li>' +
//                        '<li class="list-group-item">Дата выхода с отпуска: ' + vac.endDate + '</li>' +
//                    '</ul>' +
//                '</div>' +
//            '</div>';
//            
//            if((i + 1)%3 === 0 && (i + 1)/3 >= 1){
//                vacationRow = vacationRow + '</div>';
//            }
//            if((i + 1)%3 === 0 && (i + 1)/3 >= 1){
//                vacationRow = vacationRow + '<div class="card-deck justify-content-center">';
//            }
//            
//        });
//    vacationRow = vacationRow + '</div>';
//    }else{
//        vacationRow = vacationRow +
//        '<div class="card-deck">'+
//            '<div class="card">' +
//                '<div class="card-body">' +
//                    '<h4 class="card-title">История отпусков сотрудника не указана</h4>' +
//                '</div>' +
//            '</div>'+
//        '</div>';
//    }
    
//    return passportRow + educationsRow + familyMembersRow + jobHistoryRow + vacationRow;
    return "vacationRow";
}