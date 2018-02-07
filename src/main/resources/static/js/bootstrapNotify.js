bootstrapNotify = function (message) {  }

bootstrapNotify.warning = function (message, div) {
    var notifyDiv = $(div);
    var w = $('<div class="alert alert-danger"></div>')
        .append('<span class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;&nbsp;&nbsp;' + message);

    w.hide();
    w.fadeIn(100, function() {
        w.click(function() {
            w.fadeOut(0, end);
        });
        setTimeout( function() {
            w.fadeOut(1000, end);
        }, 2000 );
    });
    function end() {
        w.remove();
    }
    $(notifyDiv).append(w);
};

bootstrapNotify.success = function (message, div) {
    var notifyDiv = $(div);
    var s = $('<div class="alert alert-success"></div>')
        .append('<span class="glyphicon glyphicon-ok-circle"></span>&nbsp;&nbsp;&nbsp;' + message);

    s.hide();
    s.fadeIn(100, function() {
        s.click(function() {
            s.fadeOut(0, end);
        });
        setTimeout( function() {
            s.fadeOut(1000, end);
        }, 2000 );
    });
    function end() {
        s.remove();
    }
    $(notifyDiv).append(s);
};