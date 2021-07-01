var measure = $("#protectiveMeasure");
var measureCost = $("#protectiveMeasureCost");

if(measure.val() == ""){
    measureCost.prop("readonly", true); 
} 
  
measure.on('change', function() {
    if(measure.val() == ""){
        measureCost.prop("readonly", true); 
    } else {
        measureCost.prop("readonly", false); 
    }
});