/*
 This file is part of the NetView open source project
 Copyright (c) 2017 NetView authors
 Licensed under The MIT License
 */
var notify = {};
(function(){
    notify.success = success;
    notify.danger = danger;

    function success(message) {
        s(message, 'panel-success');
    }
    function danger(message) {
        f(message, 'panel-danger');
    }

    function f(message, panelClass) {
        var d = $('<div class="panel panel-notify center-block"></div>')
            .addClass(panelClass)
            .append(
                $('<div class="panel-heading"></div>').html('<span class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;' + message)
            );
        d.hide();
        d.fadeIn(100, function() {
            d.click(function() {
                d.fadeOut(0, end);
            });
            setTimeout( function() {
                d.fadeOut(1000, end);
            }, 2000 );
        });
        function end() {
            d.remove();
        }
        $(document.body).append(d);
    }
    function s(message, panelClass) {
        var d = $('<div class="panel panel-notify center-block"></div>')
            .addClass(panelClass)
            .append(
                $('<div class="panel-heading"></div>').html('<span class="glyphicon glyphicon-ok-sign"></span>&nbsp;' + message)
            );
        d.hide();
        d.fadeIn(100, function() {
            d.click(function() {
                d.fadeOut(0, end);
            });
            setTimeout( function() {
                d.fadeOut(1000, end);
            }, 2000 );
        });
        function end() {
            d.remove();
        }
        $(document.body).append(d);
    }
})();