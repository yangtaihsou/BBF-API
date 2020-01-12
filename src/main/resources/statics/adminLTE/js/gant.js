(function ($) { 
	$.fn.gant = function (options) { 
		var me = this;
		var $e = $(this);  
		var defualts = {
			 //以下为echarts的一些基础元素的默认配置
			 tooltip : {
			       show : true,
		           axisPointer : {
		               type : 'shadow'
		           },
		           feature : {
		               mark : {show: true},
		               dataView : {show: true, readOnly: false},
		               magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
		               restore : {show: true},
		               saveAsImage : {show: false}
		           },
			 },
			 grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        top: 3,
		        containLabel: true
		    },
		    yAxis: {
		        type: 'category',
		        data: []
		    },
		    label: {
                normal: {
                    show: true,
                    position: 'insideTopRight'
                }
            },
            type: 'bar',
            stack: '总量'
		};
		var opts = $.extend({}, defualts, options);  //融合配置项  
		 me.init = function(){  
			 var gantChart = echarts.init(document.getElementById($e.attr("id")));
			 if(opts.tformatter){
				 opts.tooltip.formatter = opts.tformatter;//tip提示格式化的方式 
			 }
			 if(opts.lformatter){
				 opts.label.normal.formatter = opts.lformatter;//tip提示格式化的方式 
			 }
			 //此处组装外部传入的json数据转换为echarts的格式
			 for(var i = 0; i < opts.series.length; i ++){
				 opts.series[i].type = opts.type;
				 opts.series[i].stack = opts.stack;
				 opts.series[i].label = opts.label;
				 if(opts.series[i].data[0].color){//存在自定义的颜色,则使用自定义颜色
					 opts.series[i].data[0].itemStyle = {normal:{color : opts.series[i].data[0].color}};
				 }
			 }
			 //X轴的格式化
			 var  xAxis =  {
		        type: 'value',
		        axisLabel : {
	        	   formatter: function (value){
	        	         var start_time = opts.startTime;
	        	         var end_time = start_time + opts.days*24*60*60*1000;
	        	         var date = new Array();
	        	         var i = 1;
	        	         while(end_time > start_time){
			        	    var date_formatter = new Date(start_time);
			        	    var date_time = (date_formatter.getFullYear()+"-"+(date_formatter.getMonth()+1)+"-"+date_formatter.getDate());
			        	    start_time = start_time + i*24*60*60*1000;
			        	    date.push(date_time);
	        	         }
	        	         return date[value*1];
	        	      }
	        	 }
		    };
			 var option = {
				    tooltip : opts.tooltip,
				    grid: opts.grid,
				    xAxis:  xAxis,
				    yAxis: opts.yAxis,
				    series : opts.series
			};
			gantChart.setOption(option);
		 }
		 me.init();  
		 return this;
	};
})(jQuery);
