function formatDateToStr(data) {
    var da = new Date(data);
    var year = da.getFullYear();
    var month = da.getMonth() + 1;
    var date = da.getDate();
    // var day = da.getDay();
    var hour = da.getHours();
    var minute = da.getMinutes();
    var second = da.getSeconds()
    var yymmdd = [year, month, date].join('-');
    var hms = [hour, minute, second].join(':');
    return yymmdd + " " + hms;
}
