var form = document.getElementById("form");

$('#optionsbox').bootstrapDualListbox({
    nonSelectedListLabel: 'Доступные муниципалитеты',
    filterTextClear: 'показать все',
    infoTextEmpty: 'Список пуст', 
    infoText: 'Показывается {0} муниципалитет(ов)',
    infoTextFiltered: '<span class="badge badge-warning">Найдено</span> {0} из {1}',
    selectedListLabel: 'Выбранные муниципалитеты',
    filterPlaceHolder: 'Поиск',
    preserveSelectionOnMove: 'moved',
    moveSelectedLabel: true,
    moveOnSelect: false 
});

document.getElementById("submit_button").addEventListener("click", function (event) {
    $('#bootstrap-duallistbox-nonselected-list_municipalities option').removeAttr('selected');
    form.submit();
});