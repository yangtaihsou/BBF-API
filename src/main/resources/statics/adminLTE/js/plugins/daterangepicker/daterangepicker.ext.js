var daterangepickerext = function(rangedate){
	var startDate = $(rangedate).find("div.daterange input.start").val();
    var endDate = $(rangedate).find("div.daterange input.end").val();
    if(!startDate){
    	startDate = new Date();
    }
    if(!endDate){
    	endDate = new Date();
    }
	$(rangedate).find("div.daterange").daterangepicker({
				startDate : startDate,
				endDate : endDate,
				showDropdowns : true,
				showWeekNumbers : false, //是否显示第几周
				timePicker : false, //是否显示小时和分钟
				timePickerIncrement : 60, //时间的增量，单位为分钟
				timePicker12Hour : false, //是否使用12小时制来显示时间
				ranges : {
					'今日': [moment().subtract(0,'days').startOf('day'), moment().subtract(0,'days').endOf('day')],
                    '昨日': [moment().subtract(1,'days').startOf('day'), moment().subtract(1,'days').endOf('day')],
                    '本月': [moment().startOf('month'), moment()],
                    '上月': [moment().add(-1,'months').startOf('month'), moment().add(-1,'months').endOf('month')]
				},
				opens : 'right', //日期选择框的弹出位置
				buttonClasses : [ 'btn btn-default' ],
				applyClass : 'btn-small btn-primary blue',
				cancelClass : 'btn-small',
				format : 'YYYY-MM-DD', //控件中from和to 显示的日期格式
				separator : ' to ',
				locale : {
					applyLabel : '确定',
					cancelLabel : '取消',
					clearLabel : '清除',
					fromLabel : '起始时间',
					toLabel : '结束时间',
					customRangeLabel : '自定义',
					daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
					monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月' ],
					firstDay : 1
				}
			}, function(start, end, label) {//格式化日期显示框
				if(label == 'clear'){
					$(rangedate).find('div.daterange span').html('');
					$(rangedate).find('div.daterange input.start').val('');
					$(rangedate).find('div.daterange input.end').val('');
				}else if(label == '今日'){
					$('.daterange span').html(moment().format('YYYY-MM-DD') + ' - ' + moment().format('YYYY-MM-DD')); 
					$(rangedate).find('div.daterange input.start').val(moment().format('YYYY-MM-DD'));
					$(rangedate).find('div.daterange input.end').val(moment().format('YYYY-MM-DD'));
				}else{
					$(rangedate).find('div.daterange span').html(start.format('YYYY-MM-DD') + ' - ' + end.format('YYYY-MM-DD'));
					$(rangedate).find('div.daterange input.start').val(start.format('YYYY-MM-DD'));
					$(rangedate).find('div.daterange input.end').val(end.format('YYYY-MM-DD'));
				}
           });
	  var showdefault = $(rangedate).find("div.daterange").attr("showdefault");
	  if(showdefault == 'true'){
		  $('.daterange span').html(moment().format('YYYY-MM-DD') + ' - ' + moment().format('YYYY-MM-DD')); 
	  }
	  //设置日期菜单被选项  --开始--
	  var dateOption = $(rangedate).find("div.daterange span").attr("dateoption");
	  if(dateOption=='day') {
		dateOption = "今日";
	  }else if(dateOption=='yday') {
			dateOption = "昨日";
	  }else if(dateOption=='month'){
			dateOption ="本月";
	  }else if(dateOption=='ymonth'){
			dateOption ="上月";
	  }else if(dateOption=='year'){
			dateOption ="当年";
	  }else{
			dateOption = "自定义";
	  }
	  $(".daterangepicker").find("li").each(function (){
		if($(this).hasClass("active")){
			$(this).removeClass("active");
		}
		if(dateOption==$(this).html()){
			$(this).addClass("active");
			$(this).click();
		}
  });
}