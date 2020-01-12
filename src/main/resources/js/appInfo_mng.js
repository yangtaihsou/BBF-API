$(function () {

    var para = '?ssoToken=' + token + "&loginUserId=" + email
    $("#jqGrid").jqGrid({
        url: domain + '/console/appInfo/list?ssoToken=' + token + "&loginUserId=" + email,
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', width: 20},
            {label: 'appId', name: 'appId', width: 40},
            {label: 'app名字', name: 'appName', width: 120},
            {label: '备注', name: 'remark', width: 120},
            {
                label: '状态', name: 'status', width: 20, formatter: function (cellValue, options, rowObject) {
                    if (cellValue == 0) {
                        return "<font color='red'>已下线</font>";
                    }
                    if (cellValue == 1) {
                        return "<font color='blue'>已上线</font>";
                    }
                    return '';
                }
            },
            /*{ label: '版本号', name: 'version', width: 80 },*/
            {label: '拥有者', name: 'userName', width: 40},
            {label: '系统名字', name: 'systemName', width: 40},
            {label: '最后修改人', name: 'lastModifyUser', width: 40},
            {
                label: '修改时间', name: 'updateDate', width: 40, formatter: function (cellValue, options, rowObject) {
                    return formatDateToStr(cellValue);
                }
            },
            {
                label: '创建时间', name: 'createdDate', width: 40, formatter: function (cellValue, options, rowObject) {
                    return formatDateToStr(cellValue);
                }
            },
            {
                label: '操作', name: 'enable', width: 80,
                formatter: function (cellvalue, options, rowObject) {
                    console.log(cellvalue)
                    var btns = [];
                    btns.push('<a class="btn btn-xs btn-success" href="javascript:void(0);" onclick="vm.update(' + options.rowId + ')">修改</a>');
                    btns.push('<a class="btn btn-xs btn-warning" href="javascript:void(0);" onclick="vm.offline(' + options.rowId + ')">下线</a>');
                    btns.push('<a class="btn btn-xs btn-success" href="javascript:void(0);" onclick="vm.online(' + options.rowId + ')">发布</a>');

                    btns.push('<a class="btn btn-xs btn-warning" href="javascript:void(0);"' +
                        'onclick="vm.appShow(\'' + rowObject.appId + '\')">演示</a>');
                    return btns.join(' ');
                }
            }
        ],
        viewrecords: true,
        height: 400,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "pageNo",
            rows: "pageSize",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        params: {
            nodeType: ""
        },
        appInfo: {
            id: 0,
            status: 0
        },
        resources: []
    },
    created: function () {
        console.log('created....');
        isLogin();
        getSystems();
        // this.init();
    },
    mounted: function () {
    },
    methods: {
        init: function () {
            console.info("methods init");
            console.info(systemCodes);
        },
        query: function () {
            $("#jqGrid").jqGrid('setGridParam', {
                postData: vm.params,
                page: 1
            }).trigger("reloadGrid");
        },
        create: function () {
            var opt = {
                url: "/page/appInfo_add.html#800",
                id: 'appInfo_add',
                title: '新增app',
                height: 800
            };
            addTab(opt);
        },
        update: function (id) {
            var opt = {
                url: "/page/appInfo_add.html?id=" + id + "#800",
                id: 'appInfo_add_' + id,
                title: id + '.app变更',
                height: 800
            };
            addTab(opt);
        },
        online: function (id) {
            vm.appInfo.id = id;
            vm.appInfo.status = 1;
            this.updateAppInfo();
        },
        offline: function (id) {
            vm.appInfo.id = id;
            vm.appInfo.status = 0;
            this.updateAppInfo();
        },
        updateAppInfo: function () {

            var para = '?ssoToken=' + token + "&loginUserId=" + email
            var url = domain + "/console/appInfo/editSimple" + para;
            //JSON.parse( jsonStr );
            $.ajax({
                type: "POST",
                url: url,
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                data: vm.appInfo,
                success: function (r) {
                    if (r.code == 0) {
                        alert('操作成功', function (index) {
                            vm.query();
                        });
                    } else {
                        alert(r.reason, function (index) {
                            vm.query();
                        });
                    }
                },
                error: function (event, xhr, options, exc) {
                    alert("操作失败");
                }
            });
        },
        appShow: function (appId) {
            var $iframe = $('<iframe id="modelFrame" name="modelFrame" width="100%" src="" border="0" frameborder="0" scrolling="auto" height="100%">'
                + '浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。'
                + '</iframe>');
            var url = "/page/apiShow.html?appId=" + appId;
            // $iframe.attr('src', timestamp(url));
            $iframe.attr('src', timestamp(url));
            console.log("appShow开始---");
            bootbox.dialog({
                message: $iframe,
                title: "",
                className: "add-modal",
                size: 'large',
                callback: function () {
                    console.log("appShow完毕");
                },
                buttons: {
                    ok: {
                        label: "关    闭",
                        className: 'btn-danger',
                        callback: function () {
                            console.log('appShow close refresh detailinfo');
                        }
                    }
                }
            });
        }
    }
});