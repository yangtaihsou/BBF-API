
$(function () {
    $("#jqGrid").jqGrid({
        url: domain + '/console/jvmScriptInfo/list'+'?ssoToken='+token,
        datatype: "json",
        colModel: [
            { label: 'ID', name: 'id', width: 80 },
            { label: '脚本编号', name: 'scriptId', width: 80 },
            { label: '脚本名字', name: 'scriptName', width: 80 },
            { label: '备注', name: 'remark', width: 80 },
            { label: '版本号', name: 'version', width: 80 },
            { label: '修改时间', name: 'updateDate', width: 80 ,formatter:function(cellValue,options,rowObject){
                    return formatDateToStr(cellValue);
                }},
            { label: '创建时间', name: 'createdDate', width: 80 ,formatter:function(cellValue,options,rowObject){
                    return formatDateToStr(cellValue);
                }},
            { label: '操作', name: 'enable', width: 80 ,
                formatter: function (cellvalue, options, rowObject) {
                    var btns = [];
                    btns.push('<a class="btn btn-xs btn-success" href="javascript:void(0);" onclick="vm.update('+options.rowId+')">修改</a>');
                    //btns.push('<a class="btn btn-xs btn-success" href="javascript:void(0);" onclick="vm.delete('+options.rowId+')">删除</a>');
                    return btns.join(' ');
                }
            }
        ],
        viewrecords: true,
        height: 400,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"pageNo",
            rows:"pageSize",
            order: "order"
        },
        gridComplete:function(){
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
});

var vm = new Vue({
    el:'#rrapp',
    data:{
        params:{
            nodeType:""
        },
        resources:[]
    },
    created: function() {
        isLogin();
        console.log('created....');
    },
    methods: {
        query: function () {
            $("#jqGrid").jqGrid('setGridParam',{
                postData:vm.params,
                page:1
            }).trigger("reloadGrid");
        },

        create:function () {
            var opt = {
                url: "/page/jvmScriptInfo_add.html#800",
                id:'jvmScriptInfo_add',
                title:'新增脚本',
                height:800
            };
            addTab(opt);
        },
        update:function (id) {
         var opt = {
                url: "/page/jvmScriptInfo_add.html?id="+id+"#800",
                id:'jvmScriptInfo_add_'+id,
                title:id+'.脚本变更',
                height:800
            };
            addTab(opt);
        }
    }
});