/**
 * @author mixinlei
 * 该文件主要功能是在页面加载时，对页面元素进行渲染
 */

$(document).ready(function(){
	//对html元素添加必要样式
	decorate();
	//初始化时间格式
	//initDatePicker();
});

//对html元素添加必要样式
function decorate(){
	//添加标题下划线
	var groupTs = $(".box-body").find(".group-title");
	if(groupTs != null){
		$(groupTs).each(function(index , item){
				$(item).after('<div class="group-line"></div>');
		});
	}

	var groups = $(".box-body").find(".form-group");
	$(groups).each(function(index , group){
		//input
		var inputs = $(group).find(".form-control");
		$(inputs).each(function(num , input){
			//日期格式
			if($(input).hasClass("datepicker")){
				
				var required = $(input).attr("required");
				
				var inputP = $(input).parent("div");
				var label = $(inputP).prev('label');
	            if(!$(label).hasClass("control-label")) {
	            	
	            	if(!$(label).attr("columWidthClass")){
	            		 $(label).addClass("col-sm-2 control-label");
	            	}else{
	            		 $(label).addClass($(label).attr("columWidthClass") + " control-label");
	            		 $(label).removeAttr("columWidthClass");
	            	}
	            }
				if(required != null){
					$(label).append("<font color='red'>*</font>");
				}
				if(inputP != null && inputP != undefined){
	                if($(input).attr("columWidthClass")){
	                    $(inputP).wrap("<div class='"+$(input).attr("columWidthClass")+"'></div>");
	                }else{
	                    $(inputP).wrap("<div class='col-sm-4'></div>");
	                }
				}
			}else{
				var required = $(input).attr("required");
				var prev = $(input).prev('label');
				
	            if(!$(prev).hasClass("control-label")) {
	            	if(!$(prev).attr("columWidthClass")){
	            		 $(prev).addClass("col-sm-2 control-label");
	            	}else{
	            		 $(prev).addClass($(prev).attr("columWidthClass") + " control-label");
	            		 $(prev).removeAttr("columWidthClass");
	            	}
	            }
				if(required != null){
					$(prev).append("<font color='red'>*</font>");
				}
	            if($(input).attr("columWidthClass")){
	                $(input).wrap("<div class='"+$(input).attr("columWidthClass")+"'></div>");
	            }else{
	                $(input).wrap("<div class='col-sm-4'></div>");	
	            }
			}
		});
	});
}

/*//初始化时间格式
function initDatePicker(){
	$('div.singledate').each(function(){
		var format = $(this).find("input.datepicker").attr("format");
		$(this).datepicker({
			format: format.toLowerCase(),
		    calendarWeeks: false,
		    autoclose: true,
		    language: "zh-CN",
		    clearBtn:true,
		    todayHighlight: true
		}).on("hide",function(e){
			var myform = $("form");
			var id = $(this).find("input").attr("id");
			$(myform).data('bootstrapValidator')
			  .updateStatus(id, 'NOT_VALIDATED',null)
			  .validateField(id); 
		});
	});
	$('div.rangedate').each(function(){
		daterangepickerext(this);
	});
}*/



