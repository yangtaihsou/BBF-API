$(document).ready(function(){
	checkBtns();
});

function checkBtns(container){
	
	var currentRole = top.getCurrentRoles();
	if(currentRole == ""){
		return;
	}
	
	var curentRoleArr = currentRole.split(",");
	var btns = null;
	if(container == null || container == undefined || container == ''){
		btns = $(".btn");	
	}else{
		btns = $("#container").find(".btn");
	}
	
	if(btns == null || btns == undefined || btns == ''){
		return true;
	}
	
	$(btns).each(function(index , item){
		var showRoles = $(item).attr("showRole");
		
		if(showRoles == null || showRoles == undefined || showRoles == ''){

			showRoles = $(item).find("i").attr("showRole");
			if(showRoles == null || showRoles == undefined || showRoles == ''){
				return true;	
			}
			
		}
		
		var showRoleArr = showRoles.split(",");
		
		var isContains = false;
		$(showRoleArr).each(function(roleIndex , roleItem){
			
			$(curentRoleArr).each(function(cIndex , cItem){
				if(cItem == roleItem){
					isContains = true;
					return false;
				}
			});
			if(isContains){//有交集即停止寻找
				return false;
			}
		});
		//没有交集不显示
		if(!isContains){
			$(item).attr("style" , "display:none");
		}
		
	});
}
