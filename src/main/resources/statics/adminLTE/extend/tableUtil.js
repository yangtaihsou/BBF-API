/**
 *@author mixinlei
 *该对象封装adminLte table的一些公用方法
 * 
 *  */

var TableUtil = function(tableId){
	this.tableId = tableId;
	this.table = $("#" + tableId);
	this.entityType = 0;//0：radio 1：checkbox
	
};

TableUtil.prototype.getSelectedData = function(){
	if(this.tableId == null || this.tableId == undefined){
		return false;
	}
	
	if(this.entityType == 0){
		tr = $("#"+this.tableId).find('input[name="entityId"]').filter(':checked').parent().parent();
	}
	
	var table = $("#"+ this.tableId).DataTable();
  	var row = table.row(tr);
	var data = row.data();
	return data;  
};

TableUtil.prototype.getSelectedDatas = function(){
	if(this.tableId == null || this.tableId == undefined){
		return false;
	}
	var trs = null;
	var datas = [];
	if(this.entityType == 0){
		trs = $("#"+this.tableId).find('input[name="entityId"]').find('.selected');
		for(var i = 0 ;i < trs.length ; i++){
			var tr = trs[i];
			var table = $("#"+ this.tableId).DataTable();
		  	var row = table.row(tr);
			var data = row.data();
			datas.push(data);
		}
	}
	
	return datas;  
};
/**选中checked的*/
TableUtil.prototype.getCheckedDatas = function(){
    if(this.tableId == null || this.tableId == undefined){
        return false;
    }
    var trs = null;
    var datas = [];
    if(this.entityType == 0){
        trs = $("#"+this.tableId).find('input:checked');
        //console.log("这里是里面",trs);

        for(var i = 0 ;i < trs.length ; i++){
            var tr = $(trs[i]).parent().parent();
            var table = $("#"+ this.tableId).DataTable();
            var row = table.row(tr);
            var data = row.data();
            datas.push(data);
        }
    }

    return datas;
};


TableUtil.prototype.getSelectedDataById = function(dataId){
	
	if(dataId == null || ""){
		return null;
	}
	var table = $("#"+ this.tableId).DataTable();
	var datas = table.rows().data();
	var selectData = null;
	$(datas).each(function(index , data){
		if(dataId == data.id){
			selectData = data;
			return false;
		}
	});
	
	return selectData;
	 
	
	
}