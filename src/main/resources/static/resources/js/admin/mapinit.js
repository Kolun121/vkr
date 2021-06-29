$(document).ready(function () {
    $.getJSON(document.URL + "/get-map-data", function (r, status, xhr) {
            $('path').each(function() {
            var regId = $(this).attr('id');

            for (var j = 0; j < r.length; j++) {

                if (regId == r[j].mapId) {
                    $(this).attr('title', r[j].title);
                    $(this).css('fill',r[j].color);
                    $(this).attr('risk', r[j].risk);
                    $(this).attr('riskOfDeath', r[j].riskOfDeath);
                    $(this).attr('acceptableRiskOfDeath', r[j].acceptableRiskOfDeath);
                    $(this).attr('costOfMeasures', r[j].costOfMeasures);
                    $(this).attr('riskOfDeathMeasures', r[j].riskOfDeathMeasures);
                    $(this).attr('effectivenessOfMeasures', r[j].effectivenessOfMeasures);

                }
            }


        });


        $('path').hover(function(e){

        //  $('path').css('fill','#fff');
          $('.indicator').html('');
          var id = $(this).attr('id').toUpperCase();

          if($(this).attr('title')) {
            var title = $(this).attr('title');
            var risk = $(this).attr('risk');
            var riskOfDeath = $(this).attr('riskOfDeath');
            var acceptableRiskOfDeath = $(this).attr('acceptableRiskOfDeath');
            var costOfMeasures = $(this).attr('costOfMeasures');
            var riskOfDeathMeasures = $(this).attr('riskOfDeathMeasures');
            var effectivenessOfMeasures = $(this).attr('effectivenessOfMeasures');
            
            $('<div>' + title +'</div>').appendTo('.indicator');
            $('<div>Значение риска - ' + risk +'</div>').appendTo('.indicator');
            $('<div>Риск смерти - ' + riskOfDeath +' руб.</div>').appendTo('.indicator');
            $('<div>Допустимый риск - ' + acceptableRiskOfDeath +' руб.</div>').appendTo('.indicator');
            $('<div>Стоимость доп. мероприятий - ' + costOfMeasures +' руб.</div>').appendTo('.indicator');
            $('<div>Риск смерти с доп. мероприятиями - ' + riskOfDeathMeasures +' руб.</div>').appendTo('.indicator');
            $('<div>Эффективность мер - ' + effectivenessOfMeasures +'</div>').appendTo('.indicator');
          }


        //  $('path:not(.chosenSvgPath)').css('fill','rgba(0,0,0,0.5)');

          $('.indicator').css({'top':e.pageY,'left':e.pageX+30}).show();



        },function(){
            $('.indicator').html('');
            $('.indicator').hide();
        });
    });
});