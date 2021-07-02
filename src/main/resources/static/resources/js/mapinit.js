$(document).ready(function () {
    $.getJSON(document.URL + "get-map-data", function (r, status, xhr) {
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
            
            var titleElement = '<header class="forecast__header">\n\
                                <h1 class="forecast__title">' + title + '</h1> \n\
                                </header>';
            
            var tableElement = '<table class="forecast__table">\n\
                                <tbody>\n\
                                <tr class="thick-row">\n\
                                <td colspan="3" class="small-info"><b>Расчитанные значения</b></td>\n\
                                </tr>\n\
                                </tbody>\n\
                                </table>';
                
                
            var riskElement = '<tr><th colspan="2"><b>Значение риска:</b></th><td><b>' + risk +'</b></td></tr>';
            var riskOfDeathElement = '<tr><th colspan="2"><b>Риск смерти:</b></th><td><b>' + riskOfDeath +' руб.</b></td></tr>';
            var riskOfAcceptableDeathElement = '<tr><th colspan="2"><b>Допустимый риск:</b></th><td><b>' + acceptableRiskOfDeath +' руб.</b></td></tr>';
            var costOfMeasureElement = '<tr><th colspan="2"><b>Стоимость доп. мероприятий:</b></th><td><b>' + costOfMeasures +' руб.</b></td></tr>';
            var riskOfDeathMeasureElement = '<tr><th colspan="2"><b>Риск смерти с доп. мероприятиями:</b></th><td><b>' + riskOfDeathMeasures +' руб.</b></td></tr>';
            var effectivenessOfMeasuresElement = '<tr><th colspan="2"><b>Эффективность мер:</b></th><td><b>' + effectivenessOfMeasures +'</b></td></tr>';
            
            $(titleElement).appendTo('.indicator');
            $(tableElement).appendTo('.indicator');
            
            $(riskElement).appendTo('.forecast__table');
            $(riskOfDeathElement).appendTo('.forecast__table');
            $(riskOfAcceptableDeathElement).appendTo('.forecast__table');
            $(costOfMeasureElement).appendTo('.forecast__table');
            $(riskOfDeathMeasureElement).appendTo('.forecast__table');
            $(effectivenessOfMeasuresElement).appendTo('.forecast__table');
          }

//        $('#block').mousemove(function (e) {
//        var xPos = e.pageX - $(this).position().left;
//        var yPos = e.pageY - $(this).position().top;
//          $( ".log" ).text( "parentX: " + xPos + ", pageY: " + yPos );
//    });
        //  $('path:not(.chosenSvgPath)').css('fill','rgba(0,0,0,0.5)');

          $('.indicator').css({'top':e.pageY,'left':e.pageX - 10}).show();



        },function(){
            $('.indicator').html('');
            $('.indicator').hide();
        });
    });
});