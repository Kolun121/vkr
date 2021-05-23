var form = document.getElementById("form");

$('#optionsbox').bootstrapDualListbox({
    nonSelectedListLabel: 'Доступные субъекты РФ',
    filterTextClear: 'показать все',
    infoTextEmpty: 'Список пуст', 
    infoText: 'Показывается {0} субъект(ов)',
    infoTextFiltered: '<span class="badge badge-warning">Найдено</span> {0} из {1}',
    selectedListLabel: 'Выбранные субъекты',
    filterPlaceHolder: 'Поиск',
    preserveSelectionOnMove: 'moved',
    moveSelectedLabel: true,
    moveOnSelect: false 
});

//document.getElementById("submit_button").addEventListener("click", function (event) {
//    $('#bootstrap-duallistbox-nonselected-list_federalSubjects option').removeAttr('selected');
//    form.submit();
//});