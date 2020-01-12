/**
* common
*/

$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
            alert(this.name+':'+this.value+'\n');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

//将时间戳转行成字符格式 eg:2014-6-12 12:0:0
function format(unixTime, isFull, timeZone){
   if(!timeZone ){
        timeZone = 8;
    }
	if (typeof(timeZone) == 'number')
	{                      
		unixTime = parseInt(unixTime) + parseInt(timeZone) * 60 * 60*1000;                  
	} 
	var time = new Date(unixTime);                  
	var ymdhis = ""; 
	var m = time.getUTCMonth() + 1;
    var d = time.getUTCDate();
	ymdhis += time.getUTCFullYear() + "-";
	ymdhis += (m >= 10 ? m : ('0' + m)) + "-";                  
	ymdhis += (d >= 10 ? d : ('0' + d)); 
	if (isFull == true ) {     
		var hour = time.getUTCHours();
		var min = time.getUTCMinutes();
		var sec = time.getUTCSeconds();
		ymdhis += " " + (hour >= 10 ? hour : ('0' + hour)) + ":";                      
		ymdhis += (min >= 10 ? min : ('0' + min)) + ":";                      
		ymdhis += (sec >= 10 ? sec : ('0' + sec));                  
	} 
	return ymdhis;              
} 

//将时间戳转行成字符格式 eg:2014-6-12 12:0:0
function formatDate(time,len){    
	if(time == null)return;
	time = time.replace("T"," ");
	if(len == 10)return time.substring(0,10);//yyyy-MM-dd
	if(len == 8) return time.substring(0,10).replace("-","");//yyyyMMdd
	if(len == 7)return time.substring(0,7);//yyyy-MM
	if(len == 19)return time;//yyyy-MM-dd HH:mm:ss
	if(len == 14)return time.replace("-","").replace(":","").replace(" ","");//yyyyMMddHHmmss
	if(len == 15)return replace("-","").replace(":","");//yyyyMMdd HHmmss
} 

//关闭当前页面所以弹出层并提示信息
function closeBootbox(obj){
	$(".bootbox").modal("hide");

	$('body').css('padding-right',0);

	if(obj){
		if(obj.message){
			if(obj.reload){
				bootbox.alert({
						message:obj.message,
						className: "alert-modal",
						buttons: { ok: {label: '确定', className: 'btn-success'}},
						callback:function(){$("#queryform").submit();}
				});
			}else{
				bootbox.alert({message:obj.message,className: "alert-modal",buttons: { ok: {label: '确定', className: 'btn-success'}}});
			}
		}
		if(obj.dtid){
			$('#'+obj.dtid).dataTable().fnDraw();
		}
	}
}

function alertBootbox(message){
	$(".bootbox").modal("hide");
	$('body').css('padding-right',0);
	bootbox.alert(
        {
         message:message,
         className: "alert-modal",
         buttons: {
             ok: {
                 label: '确定',
                 className: 'btn-success'}
         }
        }
    );
}


function resizeBootbox(title,url,height){
	$(".bootbox").modal("hide");
	$('body').css('padding-right',0);
	$iframe.attr('src',url).attr('height',height);
	bootbox.dialog({
			message: $iframe,
		 	title: title,
		 	className: "add-modal"
	 });
}

function openIframe(obj){
	$('#'+obj.fid).attr('src',obj.furl).attr('height',fheight);
	 bootbox.dialog({
		message: $('#'+id).html(),
	 	header: obj.header,
	 	className:obj.className?obj.className:'modal-primary',
	 	sizeClassName:obj.sizeClassName?obj.sizeClassName:'modal-lg'
	 });
}


//创建iframe标签，用于iframe的动态加载
var $iframe = $('<iframe id="modelFrame" name="modelFrame" width="100%" src="" border="0" frameborder="0" scrolling="auto" height="100%">'
		  +'浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。'
		  +'</iframe>');

$(function(){ 
//全局的ajax访问，处理ajax清求时sesion超时  
$.ajaxSetup({
	timeout: 60000,
    contentType:"application/x-www-form-urlencoded;charset=utf-8",   
   
    complete:function(XMLHttpRequest,textStatus){   
        var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头,sessionstatus，  
        if(sessionstatus=="timeout"){   
        	if(window.parent.document.getElementsByTagName("iframe").length>0){
    			cancel();
    			window.parent.location.reload();
    		}else{
    			window.location.reload();
    		}
        }else if( navigator.userAgent.indexOf("MSIE")>0 && textStatus == 'parsererror'){//ie游览器
        	if(window.parent.document.getElementsByTagName("iframe").length>0){
    			cancel();
    			window.parent.location.reload();
    		}else{
    			window.location.reload();
    		}
        }
    }

  });
});

//获取页面的高度、宽度
function getPageSize() {
    var xScroll, yScroll;
    if (window.innerHeight && window.scrollMaxY) {
        xScroll = window.innerWidth + window.scrollMaxX;
        yScroll = window.innerHeight + window.scrollMaxY;
    } else {
        if (document.body.scrollHeight > document.body.offsetHeight) { // all but Explorer Mac    
            xScroll = document.body.scrollWidth;
            yScroll = document.body.scrollHeight;
        } else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari    
            xScroll = document.body.offsetWidth;
            yScroll = document.body.offsetHeight;
        }
    }
    var windowWidth, windowHeight;
    if (self.innerHeight) { // all except Explorer    
        if (document.documentElement.clientWidth) {
            windowWidth = document.documentElement.clientWidth;
        } else {
            windowWidth = self.innerWidth;
        }
        windowHeight = self.innerHeight;
    } else {
        if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode    
            windowWidth = document.documentElement.clientWidth;
            windowHeight = document.documentElement.clientHeight;
        } else {
            if (document.body) { // other Explorers    
                windowWidth = document.body.clientWidth;
                windowHeight = document.body.clientHeight;
            }
        }
    }       
    // for small pages with total height less then height of the viewport    
    if (yScroll < windowHeight) {
        pageHeight = windowHeight;
    } else {
        pageHeight = yScroll;
    }    
    // for small pages with total width less then width of the viewport    
    if (xScroll < windowWidth) {
        pageWidth = xScroll;
    } else {
        pageWidth = windowWidth;
    }
    arrayPageSize = new Array(pageWidth, pageHeight, windowWidth, windowHeight);
    return arrayPageSize;
}

/**
 * 时间格式化
 */
Date.prototype.pattern=function(fmt) {           
    var o = {           
    "M+" : this.getMonth()+1, //月份           
    "d+" : this.getDate(), //日           
    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时           
    "H+" : this.getHours(), //小时           
    "m+" : this.getMinutes(), //分           
    "s+" : this.getSeconds(), //秒           
    "q+" : Math.floor((this.getMonth()+3)/3), //季度           
    "S" : this.getMilliseconds() //毫秒           
    };           
    var week = {           
    "0" : "/u65e5",           
    "1" : "/u4e00",           
    "2" : "/u4e8c",           
    "3" : "/u4e09",           
    "4" : "/u56db",           
    "5" : "/u4e94",           
    "6" : "/u516d"          
    };           
    if(/(y+)/.test(fmt)){           
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));           
    }           
    if(/(E+)/.test(fmt)){           
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);           
    }           
    for(var k in o){           
        if(new RegExp("("+ k +")").test(fmt)){           
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));           
        }           
    }           
    return fmt;           
}

Array.prototype.remove = function(dx){
	if(isNaN(dx)||dx>this.length){
		return false;
	}
	this.splice(dx,1);
};

function timestamp(url){
	 var ts = new Date().getTime();
	 if(url.indexOf("?")>-1){
		 var re = eval('/(timestamp=)([^&]*)/gi');
		 url = url.replace(re,'timestamp='+ts);
		 if(url.indexOf('timestamp=') == -1){//之前没有时间戳
			 url += "&timestamp="+ts;
		 }
	 }else{
		 url=url+"?timestamp="+ts;  
	 }
	 return url;
}

function setTimeoutFocus(id){
	setTimeout('$("#' + id + '").focus()',500);
}


function formatMoney(value, reg) {

  var   decimal =2;

    var tempValue = value+"";

    var isminus = false;
    var replaceReg = /[^\d\.]/g;

    if (tempValue.indexOf(".") <= 0) {
        //replaceReg = /[^\d]/g;
        tempValue = tempValue.replace(replaceReg, "");
    } else {
        tempValue = tempValue.replace(replaceReg, "");
        var isNaNNum = parseFloat(tempValue + "00");
        if (isNaN(isNaNNum)) {
            tempValue = isNaNNum;
        }
        if (/\./.test(tempValue) && decimal == 0) {
            tempValue = tempValue.toString().replace(/\./g, '');
        }
    }
    var re = new RegExp(reg);
    if (!re.exec(tempValue) && tempValue != "") {
        tempValue = "0";
    }

    var tempValueArray = tempValue.split(".");
    tempValue = tempValueArray.length > 1 ?
    (tempValueArray[0].length > 14 ? tempValueArray[0].substr(0, 14) : tempValueArray[0]) + "." + tempValueArray[1] :
        (tempValue.length > 14 ? tempValue.substr(0, 14) : tempValue);

    var result = doFormat(tempValue);
    if (isminus) result = '-' + result;
    if (result == null) {
        return;
    }

    var resultArray = result.split(".");

    if (tempValue.lastIndexOf(".") >= 0) {
        if (tempValue.lastIndexOf(".") == tempValue.length - 1) {
            tempValue = resultArray[0] + '.';
        } else {
            var subLength = tempValue.length - (tempValue.lastIndexOf(".") + 1);
            tempValue = resultArray[0] + "." + (resultArray[1] ? resultArray[1].substring(0, subLength) : '0');
        }
    } else {
        tempValue = resultArray[0];
    }


    return tempValue;
}


function doFormat(s) {

    var decimal=2;

    if (!s) return "";

    if ($.isNumeric(s)) {
        s = s.toString();
    }
    if (typeof s === 'string') {
        s = s.replace(/^(\d+)((\.\d*)?)$/, function(v1, v2, v3) {
            return v2.replace(/\d{1,3}(?=(\d{3})+$)/g, '$&,') + (v3.slice(0,decimal + 1) || '.00');
        });
    }

    return s.replace(/^\./, "0.")
}


/**
 * 根据url找到iframe
 */
function getFrameByURL(url){
	
	var tabframes =window.parent;
	var index = -1;

	for(var i = 0 ;i <tabframes.length ; i ++){
		
		var tabframe = tabframes[i];
		var innerFrame = null; 
		try{
			innerFrame = tabframe.$iframe;	
		}catch(e){
			console.log(e);
			continue;
		}
		
		if(innerFrame == null || innerFrame == undefined){
			continue;
		}
		var tempUrl = innerFrame[0].baseURI;
		if(tempUrl == null || tempUrl == "" || tempUrl == undefined){
			tempUrl = innerFrame[0].src;
		}
		
		var contains  = tempUrl.indexOf(url) ;
		if(contains > -1){
			index = parseInt(i);
			break;
		}
		
	}
	if(index != -1){
		return tabframes[index];
	}
	return null;
}

/**
 * 会计金额转换为普通金额如：100,222.12->100222.12
 * @param id
 */
function moneyFormat(id){
	$("#" + id).val($("#" + id).val().replace(/,/g,""));
}

function accountingFormat(id){
	var reg = /^[1-9][\d|,]*\.?\d{1,2}$/;
	if(reg.test($("#" + id).val())){
		$("#" + id).val(parseFloat($("#" + id).val().replace(/,/g,"")).toLocaleString());
	}
}

function assetRateValidate(id) {
	var reg = /(^[1-9][0-9]*\.([0-9]{1,10})$)|(^0\.([0-9]{1,10})*$)/;
	var value = $("#" + id).val();
	if (value == null || value == undefined || value == '') {
		return true;
	}
	if(reg.test(value)){
		return true;
	}
	return false;
}



function onCalculateValue(id,idAmount,idTransDay,beginDate,rate,yearDays,valueFlag) {
    debugger;
    var profit;
    var amount = $("#" + idAmount).val();
    var transPrincipal = Number(amount.replace(/,/g, ""));
    if(amount.trim() == ""){
        alertBootbox("请填写购买本金");
        return;
    }
    var transDay = $("#" + idTransDay).val();
    if(transDay.trim() == ""){
        alertBootbox("请填写转让日");
        return;
    }
    var days = getDays(beginDate,transDay)
    if(valueFlag == '1'){
        profit = transPrincipal*(rate*days/yearDays);
    }else{
        profit = transPrincipal * (Math.pow(1 + rate, days / yearDays) - 1);
    }
    $("#" + id).val((transPrincipal+profit).toFixed(2));
    accountingFormat(id);
}
function onCalculateRebuyRate(id,idTransDay,idAmount,trans,expDay,days,valueFlag,yearDays,rate) {
    debugger;
    var value;
    var amount = $("#" + idAmount).val();
    if(amount.trim() == ""){
        alertBootbox("请填写购买本金");
        return;
    }
    var transDay = $("#" + idTransDay).val();
    if(transDay.trim() == ""){
        alertBootbox("请填写转让日");
        return;
    }
    if(trans.trim() == ""){
        alertBootbox("请填写转让价");
        return;
    }
    var transPrincipal = Number(amount.replace(/,/g, ""));
    trans = Number(trans.replace(/,/g, ""));

    if(valueFlag == '1'){
        value = transPrincipal*(rate*days/yearDays);
    }else{
        value = transPrincipal * (Math.pow(1 + rate, days / yearDays) - 1);
    }
    var rebuyRate = 0;
    if(trans == 0) {
        rebuyRate = 0;
    }else{
        rebuyRate = calculateRebuyRate(transPrincipal, value, trans, yearDays,transDay,expDay);
    }
    $("#" + id).val((rebuyRate*100).toFixed(2));

    var myform = $("#erpform");
    $(myform).data('bootstrapValidator')
        .updateStatus(id, 'NOT_VALIDATED',null)
        .validateField(id);
}

function calculateRebuyRate(principal,rate,trans,yearDays,transDay,expDay){
    debugger
    var days = getDays(transDay, expDay);
    var rebuyRate = (principal + rate - trans) / trans / days * yearDays;
    debugger
    return rebuyRate;
}

function getDays(strDateStart,strDateEnd){
    var strSeparator = "-"; //日期分隔符
    var oDate1;
    var oDate2;
    var iDays;
    oDate1= strDateStart.split(strSeparator);
    oDate2= strDateEnd.split(strSeparator);
    var strDateS = new Date(oDate1[0], oDate1[1]-1, oDate1[2]);
    var strDateE = new Date(oDate2[0], oDate2[1]-1, oDate2[2]);
    iDays = parseInt(Math.abs(strDateS - strDateE ) / 1000 / 60 / 60 /24)//把相差的毫秒数转换为天数
    return iDays ;
}


