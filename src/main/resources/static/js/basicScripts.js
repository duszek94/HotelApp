var preloader = $('.loader');
var showbox = $('.showbox');
var price2 = 0;
var price3 = 0;
var roomId = 0;
var roomTittle = '';
var days = 0;

$(document).ready(function () {
    waitForElement(".container", function () {
        $(preloader).css({'display': 'none'});
        $(showbox).css({'display': 'none'});
    });

    $('.container').fadeIn(1600);
});



function waitForElement(elementPath, callBack) {
    window.setTimeout(function () {
        if($(elementPath).length){
            callBack(elementPath, $(elementPath));
        }else{
            waitForElement(elementPath, callBack);
        }
    },500)
}



$(function () {
    $('#datetimepicker1').datetimepicker({
        defaultDate: new Date(),
        minDate: new Date(),
        format: 'DD/MM/YYYY',
        useCurrent: false //Important! See issue #1075
    });
    $('#datetimepicker2').datetimepicker({
        defaultDate: new Date(Date.now()+1*24*60*60*1000),
        minDate: new Date(),
        format: 'DD/MM/YYYY',
        useCurrent: false //Important! See issue #1075
    });
    $("#datetimepicker1").on("dp.change", function (e) {
        $('#datetimepicker2').data("DateTimePicker").minDate(e.date);
    });
    $("#datetimepicker2").on("dp.change", function (e) {
        $('#datetimepicker1').data("DateTimePicker").maxDate(e.date);
    });
});

Date.daysBetween = function( date1, date2 ) {
    //Get 1 day in milliseconds
    var one_day=1000*60*60*24;

    // Convert both dates to milliseconds
    var date1_ms = date1.getTime();
    var date2_ms = date2.getTime();

    // Calculate the difference in milliseconds
    var difference_ms = date2_ms - date1_ms;

    // Convert back to days and return
    return Math.round(difference_ms/one_day);
};

$('.cls').click(function () {
    preloader.css({'display': 'none'});
});

function stringToDate(string) {
    var partsFrom = string.split("/");
    var d = new Date(Number(partsFrom[2]), Number(partsFrom[1]) - 1, Number(partsFrom[0]));

    return d;
}


function getRoomTittle(roomType) {
    var roomTittle = '';
    if(roomType == '2os'){
        roomTittle = 'Pokój 2 osobowy';
    }else if(roomType == '3os'){
        roomTittle = 'Pokój 3 osobowy';
    }else if(roomType == 'ap'){
        roomTittle = 'Apartament';
    }else if(roomType == 'apDL'){
        roomTittle = 'Apartament DeLux';
    }

    return roomTittle;
}

$(document.body).on('click', '#backToFirstStep', function() {
    $('#firstStep').fadeIn(600);
    $('#secondStep').css({'display': 'none'});
});

$(document.body).on('click', '#firstStepCommit', function() {
    $('#secondStep').fadeIn(600);
    $('#firstStep').css({'display': 'none'});
});


$('#checkRooms').click(function () {
    var beginDate = $('#datetimepicker1').find("input").val();
    var endDate = $('#datetimepicker2').find("input").val();
    var roomType = $("input:radio[name='roomType']:checked").val();
    var rooms = new Array();
    var reservations = new Array();
    var bDate = stringToDate(beginDate);
    var eDate = stringToDate(endDate);
    var from = bDate.getTime();
    var to = eDate.getTime();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    days = Date.daysBetween(bDate, eDate);
    roomTittle = getRoomTittle(roomType);



    $('#roomsResult').html('');

    if(bDate > eDate){
        bootstrapNotify.warning('Data początku rezerwacji powinna być mniejsza od daty zakończenia', '#notification');
        return;
    }


    if(typeof beginDate  !== "undefined" && beginDate
        && typeof endDate  !== "undefined" && endDate
        && typeof roomType  !== "undefined" && roomType){

            preloader.css({'display': 'block'});
    }else{
        bootstrapNotify.warning('Wybierz wszystkie kryteria', '#notification');
        return;
    }


    $.ajax({
        url:'/api/room/status/' + roomType + '/' + from + '/' + to,
        dataType: 'json',
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        
        success: function (roomsDtoList) {

            if(!roomsDtoList.success){
                preloader.css({'display': 'none'});
                return;
            }

            rooms = roomsDtoList.list;

            if(rooms.length != 0){
                var isFree;
                var price = 0;
                var i = 0;
                var roomResult = '';
                var reservationsResult = '';



                $.each(rooms, function () {
                    if(rooms[i] !== undefined) {
                        reservations = rooms[i].reservations;
                        reservationsResult = '';
                        price = rooms[i].price;
                        price2 = days * price + 'zł. + śniadania: ' + 20 * days + 'zł.';
                        price3 = days * price;

                        var j = 0;
                        $.each(reservations, function () {
                            var d1 = stringToDate(reservations[j].dateFrom);
                            var d2 = stringToDate(reservations[j].dateTo);

                            reservationsResult += reservations[j].dateFrom + " -> " +
                                reservations[j].dateTo + "<br>" +
                                d1.toString() + '<br>' +
                                d2.toString() + '<br><br>';

                            j++;
                        });
                        roomResult +=
                            "<br>roomType: " + rooms[i].roomType + ", roomId: " + rooms[i].roomId + "<br>" + reservationsResult;

                        roomId = rooms[i].roomId;

                        if(rooms[i] !== undefined){
                            if(rooms[i].isFree == true){
                                isFree = true;
                                return;
                            }else isFree = false;
                        }

                        i++;
                    }

                });

                if(isFree){
                    preloader.css({'display': 'none'});

                    $('#roomsResult').html(
                        '<div style="height: 200px; margin-top: 20px;">' +
                            '<a href="/pokoje/' + roomType + '" target="_blank" style="color: black!important; text-decoration: none!important;">' +
                                '<div class="freeRoom">' +
                                    '<img class="roomResultImage" src="/images/pokoje/'+ roomType +'/1.jpg"/>' +
                                    '<div class="roomResultData">' +
                                        '<h4>' +
                                            roomTittle +
                                        '</h4>' +
                                        'kliknij, aby przejść do opisu<br>' +
                                        '<br><b>' + beginDate + ' -> ' + endDate + '</b>' +
                                        '<br>Cena za dobę: ' + price + ' zł.<br>+ opcjonalnie śniadanie 20zł.' +
                                    '</div>' +
                                    '<div class="roomResultOptions" contenteditable="true" onclick="return false;"> ' +
                                        '<button type="button" id="firstStepCommit" class="btn btn-success">Wybierz</button><br><br>' +
                                        '<div>' +
                                            'Cena pobytu:<br>' + days * price + ' zł.<br>' +
                                            '+ śniadania: ' + 20 * days + ' zł.' +
                                        '</div>' +
                                    '</div>' +
                                '</div>' +
                            '</a>' +
                        '</div');


                    $('#roomReservationTittle').html('<b>2.</b>&nbsp; Wypełnij formularz rezerwacji:&nbsp; ' + roomTittle);

                    $('#roomsResult2').html(
                        '<div style="height: 200px;">' +
                            '<a href="/pokoje/' + roomType + '" target="_blank" style="color: black!important; text-decoration: none!important;">' +
                                '<div class="freeRoom">' +
                                    '<img class="roomResultImage" src="/images/pokoje/'+ roomType +'/1.jpg"/>' +
                                    '<div class="roomResultData">' +
                                        '<h4>' +
                                        roomTittle +
                                        '</h4>' +
                                        'kliknij, aby przejść do opisu<br>' +
                                        '<br><b>' + beginDate + ' -> ' + endDate + '</b>' +
                                        '<br>Cena za dobę: ' + price + ' zł.<br>+ opcjonalnie śniadanie 20 zł.' +
                                    '</div>' +
                                '</div>' +
                            '</a>' +
                        '</div>');

                }else{
                    bootstrapNotify.warning('Lipa z tym pokojem w tym terminie :(', '#notification');

                    preloader.css({'display': 'none'});
                }

                //$('#roomsResult').html(roomResult + '<br>' + '<br>/api/room/status/' + roomType + '/' + from + '/' + to);
            }
            else{
                bootstrapNotify.warning("Brak pokoi", "#notification");
                preloader.css({'display': 'none'});
                return;
            }
        }
    });
});


$(document.body).on('click', '#reservationContinue', function() {
    var firstname = $('#clientFirstname').val();
    var lastname = $('#clientLastname').val();
    var email = $('#clientEmail').val();
    var phone = $('#clientPhone').val();
    var paymentMethod = $("input:radio[name='paymentMethod']:checked").val();
    var details = $('#details').val();
    var dateFrom = $('#datetimepicker1').find("input").val();
    var dateTo = $('#datetimepicker2').find("input").val();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");



    if(!firstname || !lastname || !email || !phone || !paymentMethod){
        bootstrapNotify.warning("Wypełnij wymagane pola", "#notification");
        return;
    }

    $('#secondStep').css({'display': 'none'});
    $('.loader2').css({'display': 'block'});


    var userDto = {
        "firstname" : firstname,
        "lastname" : lastname,
        "email" : email,
        "phone" : phone
    };


    var userData = JSON.stringify(userDto);

    $.ajax({
        url: '/api/reservation/create/user',
        dataType: 'json',
        type: 'post',
        contentType: 'application/json',
        data: userData,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },

        success: function (userId) {
            var reservationDto = {
                "dateFrom" : dateFrom,
                "dateTo" : dateTo,
                "reservationState" : "n",
                "paidone" : "n",
                "paymentMethod" : paymentMethod,
                "details" : details,
                "price" : price2,
                "paymentDate" : "",
                "roomId" : roomId,
                "userId" : userId
            };

            var reservationData = JSON.stringify(reservationDto);

            $.ajax({
                url: '/api/reservation/create/reservation',
                dataType: 'json',
                type: 'post',
                contentType: 'application/json',
                data: reservationData,
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                },

                success: function (reservationDto) {
                    var reservation = reservationDto;

                    bootstrapNotify.success('Dodano rezerwację, oto jej podsumowanie', '#notification');


                    $('#reservationSummary').html('<h4>' +
                            '<b>Dane użytkownika: </b><br><br>' +
                            userDto.firstname + ' ' + userDto.lastname + '<br>' +
                            userDto.email + ', tel: ' + userDto.phone + '<br>' +
                            '<br><b>Sczegóły rezerwacji:</b> <br><br>' +
                            'Od: ' + reservation.dateFrom + ' do: ' + reservation.dateTo + '<br>' +
                            roomTittle + '<br>' +
                            'Cena za wypożyczenie: ' + price2 + '<br>' +
                            'Dodatkowe wymagania/informacje: ' + reservation.details + '<br>' +
                            'Kwotę: ' + price2 + ' należy przelać na dane:' + '<br><br>' +
                            'Hotel Walter' + '<br>' +
                            'nr konta: 0000 1111 2222 3333 4444 5555' +
                        '</h4>' +
                        '<h4>' +
                            '<br><br>Szczegóły rezerwacji zostało wysłane na podany adres e-mail.' +
                            'Rezerwacja zostanie potwierdzona po dokonaniu zapłaty.<br><br>' +
                            'Dziękujemy za wybranie naszego hotelu oraz życzymy miłego pobytu.' +
                        '</h4>');



                    $('#thirdStep').fadeIn(600);
                    $('.loader2').css({'display': 'none'});
                },
                error: function () {
                    bootstrapNotify.warning('Wystąpił błąd podczas tworzenia rezerwacji', '#notification');
                    $('.loader2').css({'display': 'none'});
                    return;
                }
            });
        },
        error: function () {
            bootstrapNotify.warning('Wystąpił błąd podczas przetwarzania danych użytkownika', '#notification');
            $('.loader2').css({'display': 'none'});
            return;
        }
    });

});


$('#reserve').on('hidden.bs.modal', function () {
    $('#thirdStep').css({'display': 'none'});
    $('#roomsResult').html('');
    $('#firstStep').css({'display': 'block'});
    $('.loader2').css({'display': 'none'});
});



