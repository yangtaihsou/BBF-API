/**
 * 时间工具类
 */

var TimeObjectUtil;
/**
 * @title 时间工具类
 * @note 本类一律违规验证返回false
 * @author {boonyachengdu@gmail.com}
 * @date 2013-07-01
 * @formatter "2013-07-01 00:00:00" , "2013-07-01"
 */
TimeObjectUtil = {
	/**
	 * 获取当前时间毫秒数
	 */
	getCurrentMsTime : function() {
		var myDate = new Date();
		return myDate.getTime();
	},
	/**
	 * 毫秒转时间格式
	 */
	longMsTimeConvertToDateTime : function(time) {
		var myDate = new Date(time);
		return this.formatterDateTime(myDate);
	},
	/**
	 * 时间格式转毫秒
	 */
	dateToLongMsTime : function(date) {
		var myDate = new Date(date);
		return myDate.getTime();
	},
	/**
	 * 格式化日期（不含时间）
	 */
	formatterDate : function(date) {
		var datetime = date.getFullYear()
			+ "-"// "年"
			+ ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
			+ (date.getMonth() + 1))
			+ "-"// "月"
			+ (date.getDate() < 10 ? "0" + date.getDate() : date
				.getDate());
		return datetime;
	},
	/**
	 * 格式化日期（含时间"00:00:00"）
	 */
	formatterDate2 : function(date) {
		var datetime = date.getFullYear()
			+ "-"// "年"
			+ ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
			+ (date.getMonth() + 1))
			+ "-"// "月"
			+ (date.getDate() < 10 ? "0" + date.getDate() : date
				.getDate()) + " " + "00:00:00";
		return datetime;
	},
	/**
	 * 格式化去日期（含时间）
	 */
	formatterDateTime : function(date) {
		var datetime = date.getFullYear()
			+ "-"// "年"
			+ ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
			+ (date.getMonth() + 1))
			+ "-"// "月"
			+ (date.getDate() < 10 ? "0" + date.getDate() : date
				.getDate())
			+ " "
			+ (date.getHours() < 10 ? "0" + date.getHours() : date
				.getHours())
			+ ":"
			+ (date.getMinutes() < 10 ? "0" + date.getMinutes() : date
				.getMinutes())
			+ ":"
			+ (date.getSeconds() < 10 ? "0" + date.getSeconds() : date
				.getSeconds());
		return datetime;
	},
	/**
	 * 时间比较{结束时间大于开始时间}
	 */
	compareDateEndTimeGTStartTime : function(startTime, endTime) {
		return ((new Date(endTime.replace(/-/g, "/"))) > (new Date(
			startTime.replace(/-/g, "/"))));
	},
	/**
	 * 验证开始时间合理性{开始时间不能小于当前时间{X}个月}
	 */
	compareRightStartTime : function(month, startTime) {
		var now = formatterDayAndTime(new Date());
		var sms = new Date(startTime.replace(/-/g, "/"));
		var ems = new Date(now.replace(/-/g, "/"));
		var tDayms = month * 30 * 24 * 60 * 60 * 1000;
		var dvalue = ems - sms;
		if (dvalue > tDayms) {
			return false;
		}
		return true;
	},
	/**
	 * 验证开始时间合理性{结束时间不能小于当前时间{X}个月}
	 */
	compareRightEndTime : function(month, endTime) {
		var now = formatterDayAndTime(new Date());
		var sms = new Date(now.replace(/-/g, "/"));
		var ems = new Date(endTime.replace(/-/g, "/"));
		var tDayms = month * 30 * 24 * 60 * 60 * 1000;
		var dvalue = sms - ems;
		if (dvalue > tDayms) {
			return false;
		}
		return true;
	},
	/**
	 * 验证开始时间合理性{结束时间与开始时间的间隔不能大于{X}个月}
	 */
	compareEndTimeGTStartTime : function(month, startTime, endTime) {
		var sms = new Date(startTime.replace(/-/g, "/"));
		var ems = new Date(endTime.replace(/-/g, "/"));
		var tDayms = month * 30 * 24 * 60 * 60 * 1000;
		var dvalue = ems - sms;
		if (dvalue > tDayms) {
			return false;
		}
		return true;
	},
	/**
	 * 获取最近几天[开始时间和结束时间值,时间往前推算]
	 */
	getRecentDaysDateTime : function(day) {
		var daymsTime = day * 24 * 60 * 60 * 1000;
		var yesterDatsmsTime = this.getCurrentMsTime() - daymsTime;
		var startTime = this.longMsTimeConvertToDateTime(yesterDatsmsTime);
		var pastDate = this.formatterDate2(new Date(startTime));
		var nowDate = this.formatterDate2(new Date());
		var obj = {
			startTime : pastDate,
			endTime : nowDate
		};
		return obj;
	},
	/**
	 * 获取今天[开始时间和结束时间值]
	 */
	getTodayDateTime : function() {
		var daymsTime = 24 * 60 * 60 * 1000;
		var tomorrowDatsmsTime = this.getCurrentMsTime() + daymsTime;
		var currentTime = this.longMsTimeConvertToDateTime(this.getCurrentMsTime());
		var termorrowTime = this.longMsTimeConvertToDateTime(tomorrowDatsmsTime);
		var nowDate = this.formatterDate2(new Date(currentTime));
		var tomorrowDate = this.formatterDate2(new Date(termorrowTime));
		var obj = {
			startTime : nowDate,
			endTime : tomorrowDate
		};
		return obj;
	},
	/**
	 * 获取明天[开始时间和结束时间值]
	 */
	getTomorrowDateTime : function() {
		var daymsTime = 24 * 60 * 60 * 1000;
		var tomorrowDatsmsTime = this.getCurrentMsTime() + daymsTime;
		var termorrowTime = this.longMsTimeConvertToDateTime(tomorrowDatsmsTime);
		var theDayAfterTomorrowDatsmsTime = this.getCurrentMsTime()+ (2 * daymsTime);
		var theDayAfterTomorrowTime = this.longMsTimeConvertToDateTime(theDayAfterTomorrowDatsmsTime);
		var pastDate = this.formatterDate2(new Date(termorrowTime));
		var nowDate = this.formatterDate2(new Date(theDayAfterTomorrowTime));
		var obj = {
			startTime : pastDate,
			endTime : nowDate
		};
		return obj;
	},


	GetDay:function (objDate1 ,objDate2) {
	var arrDate, objDate1, objDate2, intDays;

	objDate1 = new Date();
	objDate2 = new Date();

	arrDate = $("#开始日期").val().split("-");
	objDate1.setFullYear(arrDate[0], arrDate[1], arrDate[2]);

	arrDate = $("#结束日期").val().split("-");
	objDate2.setFullYear(arrDate[0], arrDate[1], arrDate[2]);

	intDays = parseInt(Math.abs(objDate1 - objDate2) / 1000 / 60 / 60 / 24);

	$("#天数").val(intDays + 1);
}
};


var DateUtil = {

	/**
	 * 判断闰年
	 * @param date Date日期对象
	 * @return boolean true 或false
	 */
	isLeapYear : function(date){
		return (0==date.getYear()%4&&((date.getYear()%100!=0)||(date.getYear()%400==0)));
	},

	/**
	 * 日期对象转换为指定格式的字符串
	 * @param f 日期格式,格式定义如下 yyyy-MM-dd HH:mm:ss
	 * @param date Date日期对象, 如果缺省，则为当前时间
	 *
	 * YYYY/yyyy/YY/yy 表示年份
	 * MM/M 月份
	 * W/w 星期
	 * dd/DD/d/D 日期
	 * hh/HH/h/H 时间
	 * mm/m 分钟
	 * ss/SS/s/S 秒
	 * @return string 指定格式的时间字符串
	 */
	dateToStr : function(formatStr, date){
		formatStr = arguments[0] || "yyyy-MM-dd HH:mm:ss";
		date = arguments[1] || new Date();
		var str = formatStr;
		var Week = ['日','一','二','三','四','五','六'];
		str=str.replace(/yyyy|YYYY/,date.getFullYear());
		str=str.replace(/yy|YY/,(date.getYear() % 100)>9?(date.getYear() % 100).toString():'0' + (date.getYear() % 100));
		str=str.replace(/MM/,date.getMonth()>9?(date.getMonth() + 1):'0' + (date.getMonth() + 1));
		str=str.replace(/M/g,date.getMonth());
		str=str.replace(/w|W/g,Week[date.getDay()]);

		str=str.replace(/dd|DD/,date.getDate()>9?date.getDate().toString():'0' + date.getDate());
		str=str.replace(/d|D/g,date.getDate());

		str=str.replace(/hh|HH/,date.getHours()>9?date.getHours().toString():'0' + date.getHours());
		str=str.replace(/h|H/g,date.getHours());
		str=str.replace(/mm/,date.getMinutes()>9?date.getMinutes().toString():'0' + date.getMinutes());
		str=str.replace(/m/g,date.getMinutes());

		str=str.replace(/ss|SS/,date.getSeconds()>9?date.getSeconds().toString():'0' + date.getSeconds());
		str=str.replace(/s|S/g,date.getSeconds());

		return str;
	},


	/**
	 * 日期计算
	 * @param strInterval string  可选值 y 年 m月 d日 w星期 ww周 h时 n分 s秒
	 * @param num int
	 * @param date Date 日期对象
	 * @return Date 返回日期对象
	 */
	dateAdd : function(strInterval, num, date){
		date =  arguments[2] || new Date();
		switch (strInterval) {
			case 's' :return new Date(date.getTime() + (1000 * num));
			case 'n' :return new Date(date.getTime() + (60000 * num));
			case 'h' :return new Date(date.getTime() + (3600000 * num));
			case 'd' :return new Date(date.getTime() + (86400000 * num));
			case 'w' :return new Date(date.getTime() + ((86400000 * 7) * num));
			case 'm' :return new Date(date.getFullYear(), (date.getMonth()) + num, date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds());
			case 'y' :return new Date((date.getFullYear() + num), date.getMonth(), date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds());
		}
	},

	/**
	 * 比较日期差 dtEnd 格式为日期型或者有效日期格式字符串
	 * @param strInterval string  可选值 y 年 m月 d日 w星期 ww周 h时 n分 s秒
	 * @param dtStart Date  可选值 y 年 m月 d日 w星期 ww周 h时 n分 s秒
	 * @param dtEnd Date  可选值 y 年 m月 d日 w星期 ww周 h时 n分 s秒
	 */
	dateDiff : function(strInterval, dtStart, dtEnd) {
		switch (strInterval) {
			case 's' :return parseInt((dtEnd - dtStart) / 1000);
			case 'n' :return parseInt((dtEnd - dtStart) / 60000);
			case 'h' :return parseInt((dtEnd - dtStart) / 3600000);
			case 'd' :return parseInt((dtEnd - dtStart) / 86400000);
			case 'w' :return parseInt((dtEnd - dtStart) / (86400000 * 7));
			case 'm' :return (dtEnd.getMonth()+1)+((dtEnd.getFullYear()-dtStart.getFullYear())*12) - (dtStart.getMonth()+1);
			case 'y' :return dtEnd.getFullYear() - dtStart.getFullYear();
		}
	},

	/**
	 * 字符串转换为日期对象
	 * @param date Date 格式为yyyy-MM-dd HH:mm:ss，必须按年月日时分秒的顺序，中间分隔符不限制
	 */
	strToDate : function(dateStr){
		var data = dateStr;
		var reCat = /(\d{1,4})/gm;
		var t = data.match(reCat);
		t[1] = t[1] - 1;
		eval('var d = new Date('+t.join(',')+');');
		return d;
	},

	/**
	 * 把指定格式的字符串转换为日期对象yyyy-MM-dd HH:mm:ss
	 *
	 */
	strFormatToDate : function(formatStr, dateStr){
		var year = 0;
		var start = -1;
		var len = dateStr.length;
		if((start = formatStr.indexOf('yyyy')) > -1 && start < len){
			year = dateStr.substr(start, 4);
		}
		var month = 0;
		if((start = formatStr.indexOf('MM')) > -1  && start < len){
			month = parseInt(dateStr.substr(start, 2)) - 1;
		}
		var day = 0;
		if((start = formatStr.indexOf('dd')) > -1 && start < len){
			day = parseInt(dateStr.substr(start, 2));
		}
		var hour = 0;
		if( ((start = formatStr.indexOf('HH')) > -1 || (start = formatStr.indexOf('hh')) > 1) && start < len){
			hour = parseInt(dateStr.substr(start, 2));
		}
		var minute = 0;
		if((start = formatStr.indexOf('mm')) > -1  && start < len){
			minute = dateStr.substr(start, 2);
		}
		var second = 0;
		if((start = formatStr.indexOf('ss')) > -1  && start < len){
			second = dateStr.substr(start, 2);
		}
		return new Date(year, month, day, hour, minute, second);
	},


	/**
	 * 日期对象转换为毫秒数
	 */
	dateToLong : function(date){
		return date.getTime();
	},

	/**
	 * 毫秒转换为日期对象
	 * @param dateVal number 日期的毫秒数
	 */
	longToDate : function(dateVal){
		return new Date(dateVal);
	},

	/**
	 * 判断字符串是否为日期格式
	 * @param str string 字符串
	 * @param formatStr string 日期格式， 如下 yyyy-MM-dd
	 */
	isDate : function(str, formatStr){
		if (formatStr == null){
			formatStr = "yyyyMMdd";
		}
		var yIndex = formatStr.indexOf("yyyy");
		if(yIndex==-1){
			return false;
		}
		var year = str.substring(yIndex,yIndex+4);
		var mIndex = formatStr.indexOf("MM");
		if(mIndex==-1){
			return false;
		}
		var month = str.substring(mIndex,mIndex+2);
		var dIndex = formatStr.indexOf("dd");
		if(dIndex==-1){
			return false;
		}
		var day = str.substring(dIndex,dIndex+2);
		if(!isNumber(year)||year>"2100" || year< "1900"){
			return false;
		}
		if(!isNumber(month)||month>"12" || month< "01"){
			return false;
		}
		if(day>getMaxDay(year,month) || day< "01"){
			return false;
		}
		return true;
	},

	getMaxDay : function(year,month) {
		if(month==4||month==6||month==9||month==11)
			return "30";
		if(month==2)
			if(year%4==0&&year%100!=0 || year%400==0)
				return "29";
			else
				return "28";
		return "31";
	},
	/**
	 *   变量是否为数字
	 */
	isNumber : function(str)
	{
		var regExp = /^\d+$/g;
		return regExp.test(str);
	},

	/**
	 * 把日期分割成数组 [年、月、日、时、分、秒]
	 */
	toArray : function(myDate)
	{
		myDate = arguments[0] || new Date();
		var myArray = Array();
		myArray[0] = myDate.getFullYear();
		myArray[1] = myDate.getMonth();
		myArray[2] = myDate.getDate();
		myArray[3] = myDate.getHours();
		myArray[4] = myDate.getMinutes();
		myArray[5] = myDate.getSeconds();
		return myArray;
	},

	/**
	 * 取得日期数据信息
	 * 参数 interval 表示数据类型
	 * y 年 M月 d日 w星期 ww周 h时 n分 s秒
	 */
	datePart : function(interval, myDate)
	{
		myDate = arguments[1] || new Date();
		var partStr='';
		var Week = ['日','一','二','三','四','五','六'];
		switch (interval)
		{
			case 'y' :partStr = myDate.getFullYear();break;
			case 'M' :partStr = myDate.getMonth()+1;break;
			case 'd' :partStr = myDate.getDate();break;
			case 'w' :partStr = Week[myDate.getDay()];break;
			case 'ww' :partStr = myDate.WeekNumOfYear();break;
			case 'h' :partStr = myDate.getHours();break;
			case 'm' :partStr = myDate.getMinutes();break;
			case 's' :partStr = myDate.getSeconds();break;
		}
		return partStr;
	},

	/**
	 * 取得当前日期所在月的最大天数
	 */
	maxDayOfDate : function(date)
	{
		date = arguments[0] || new Date();
		date.setDate(1);
		date.setMonth(date.getMonth() + 1);
		var time = date.getTime() - 24 * 60 * 60 * 1000;
		var newDate = new Date(time);
		return newDate.getDate();
	},
	
	/**
	 * 获取两个日期之间的天数,格式为：yyyy-mm-dd
	 * @param sDate1
	 * @param sDate2
	 * @return
	 */
	dateDiff1 : function(sDate1,  sDate2){    
	       var  aDate,  iDays ;
	       if(sDate1 == sDate2){
	    	   return 1;
	       }
	      
	       var d1 = new Date(sDate1);
	       var d2 = new Date(sDate2);
	       
	       iDays  =  parseInt(Math.abs(d1.getTime()  -  d2.getTime())  /  1000  /  60  /  60  /24);    //把相差的毫秒数转换为天数  
	       
	       iDays += 1;//闭区间
	       return  iDays ;
	   }    
};