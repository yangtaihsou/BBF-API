var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "code",
            pIdKey: "parentCode",
            rootPId: ""
        },
        key: {
            url: "nourl"
        }
    }
};
var id = T.p("id");
var vm = new Vue({
    el: '#rrapp',
    data: {
        title: "新增",
        params: {
            appId: ""
        },
        appInfo: {
            id: 0,
            appId: null,
            appName: null,
            appPath: null,
            checkPara: [],
            appPara: null,
            action: null,
            remark: null,
            version: null,
            systemCode: "",
            userId: null,
            userIds: null
        },
        userId: null,
        checkParaConfigList: []
    },
    mounted: function () {
        this.init();
    },
    created: function () {
        isLogin();
        getSystems();
        if (id != null) {
            console.info("created=" + id);
            this.title = "修改";
            this.getInfo()
        } else {
            this.getUuid();
            console.info("uuid init finished");
        }

    },
    methods: {
        init: function () {
            console.info("methods init");
        },
        getUuid: function () {
            $.get(domain + "/test/page/uuid", function (r) {
                if (r.code == 0) {
                    vm.appInfo.appId = r.uuid;
                } else {
                    alert(r.reason);
                }
            });
        },
        getInfo: function () {
            $.get(domain + "/console/appInfo/info/" + id + '?ssoToken=' + token, function (r) {
                console.info("r=" + r);
                if (r.code == 0) {
                    console.info(vm);
                    vm.appInfo.id = r.appInfo.id;
                    vm.appInfo.appId = r.appInfo.appId;
                    vm.appInfo.appName = r.appInfo.appName;
                    vm.appInfo.appPath = r.appInfo.appPath;
                    //vm.appInfo.checkPara = r.appInfo.checkPara;
                    vm.appInfo.appPara = r.appInfo.appPara;
                    vm.appInfo.action = r.appInfo.action;
                    vm.appInfo.remark = r.appInfo.remark;
                    vm.appInfo.version = r.appInfo.version;
                    vm.appInfo.systemCode = r.appInfo.systemCode;
                    vm.appInfo.userId = r.appInfo.userId
                    vm.appInfo.userIds = r.appInfo.userIds;
                    vm.userId = r.appInfo.userId;
                    if (r.appInfo.checkPara) {
                        vm.checkParaConfigList = JSON.parse(r.appInfo.checkPara);
                    }
                    vm.getDetail();
                } else {
                    alert(r.reason);
                }
            });
        },
        getDetail: function () {
            var appId = vm.appInfo.appId;
            console.log("getDetail appId=" + appId);
            vm.params.appId = appId;
            $("#jqGrid").jqGrid({
                url: domain + '/console/appInfo/info/detail?ssoToken=' + token,
                postData: vm.params,
                datatype: "json",
                colModel: [
                    {label: '类型', name: 'typeDesc', width: 40},
                    {label: '类型', name: 'type', width: 40, hidden: true},
                    {label: 'id', name: 'id', width: 40, hidden: true},
                    {label: '执行编号', name: 'detailUuid', width: 120},
                    {label: '脚本名字', name: 'detailName', width: 120},
                    {label: '备注', name: 'remark', width: 80},
                    {label: '执行排序', name: 'indexOrder', width: 40},
                    {
                        label: '修改时间',
                        name: 'updateDate',
                        width: 40,
                        formatter: function (cellValue, options, rowObject) {
                            return formatDateToStr(cellValue);
                        }
                    },
                    {
                        label: '创建时间',
                        name: 'createdDate',
                        width: 40,
                        formatter: function (cellValue, options, rowObject) {
                            return formatDateToStr(cellValue);
                        }
                    },
                    {
                        label: '操作', name: 'enable', width: 80,
                        formatter: function (cellvalue, options, rowObject) {
                            var btns = [];
                            btns.push('<a class="btn btn-xs btn-success" href="javascript:void(0);"' +
                                ' onclick="vm.updateDetail(' + rowObject.id + ','
                                + rowObject.type + ')">修改</a>');
                            btns.push('&nbsp;&nbsp;&nbsp;<a class="btn btn-xs btn-warning" href="javascript:void(0);"' +
                                ' onclick="vm.updateDetailToTtable(' + rowObject.id + ','
                                + rowObject.type + ')">新页面修改</a>');
                            return btns.join(' ');
                        }
                    }
                ],
                viewrecords: true,
                height: 100,
                width: 1400,
                rowNum: 10,
                rowList: [10, 30, 50],
                rownumbers: true,
                rownumWidth: 25,
                autowidth: true,
                multiselect: true,
                /*  pager: "#jqGridPager",*/
                jsonReader: {
                    root: "page",
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
        },
        saveOrUpdate: function () {
            //验证目前登录用户是否是原拥有者
            if (email != vm.userId) {
                //新的拥有者和登陆者不一致
                if (email!=vm.appInfo.userId) {
                    alert("你没有权限修改拥有者");
                    return
                }
            }
            var para = '?ssoToken=' + token + "&loginUserId=" + email
            var url = (vm.appInfo.id == null || vm.appInfo.id == '') ? domain + "/console/appInfo/save" + para : domain + "/console/appInfo/edit" + para;
            {
                var len = vm.checkParaConfigList.length;
                for (var index = 0; index < len; index++) {
                    var jsonStyletext = formatJson(vm.checkParaConfigList[index].config);
                    if (jsonStyletext == 'formatError') {
                        alert("参数的验证配置第" + (index + 1) + "行的json格式错误！");
                        return;
                    }
                }
            }
            vm.appInfo.checkPara = JSON.stringify(vm.checkParaConfigList)
            //JSON.parse( jsonStr );
            $.ajax({
                type: "POST",
                url: url,
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                data: vm.appInfo,
                success: function (r) {
                    if (r.code == 0) {
                        alert('操作成功', function (index) {
                            //vm.back();
                            vm.appInfo.id = r.appInfo.id;
                        });
                    } else {
                        alert(r.reason);
                    }
                }
            });
        },
        back: function () {
            history.go(-1);
        },
        //动态添加参数化配置信息
        addCheckPara: function () {
            if (vm.checkParaConfigList == '') {
                vm.checkParaConfigList = []
            }
            vm.checkParaConfigList.push({
                name: null,
                config: null
            })
        },
        removeCheckPara: function (index) {
            vm.checkParaConfigList.splice(index, 1);
        },
        jsonFormart: function (index) {
            if (vm.checkParaConfigList[index].config) {
                var jsonStyletext = formatJson(vm.checkParaConfigList[index].config);
                if (jsonStyletext == 'formatError') {
                    alert("json格式不正确，请检查");
                } else {
                    vm.checkParaConfigList[index].config = formatJson(vm.checkParaConfigList[index].config);
                }
            }
        },
        addNewScript: function (scriptPrimaryId) {
            var $iframe = $('<iframe id="modelFrame" name="modelFrame" width="100%" src="" border="0" frameborder="0" scrolling="auto" height="100%">'
                + '浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。'
                + '</iframe>');
            var url = "/page/jvmScriptInfo_add.html?appId=" + vm.appInfo.appId;
            if (scriptPrimaryId != null) {
                url = url + "&id=" + scriptPrimaryId;
            }
            // $iframe.attr('src', timestamp(url));
            $iframe.attr('src', url);
            bootbox.dialog({
                message: $iframe,
                title: "添加脚本",
                className: "add-modal",
                size: 'large',
                callback: function () {
                    console.log("添加脚本完毕");
                },
                buttons: {
                    ok: {
                        label: "关    闭",
                        className: 'btn-danger',
                        callback: function () {
                            $("#jqGrid").jqGrid('setGridParam', {
                                postData: vm.params,
                                page: 1
                            }).trigger("reloadGrid");
                        }
                    }
                }
            });
        },
        //弹框更新app关联的接口或者脚本
        updateDetail: function (id, type) {
            console.log("id=" + id + "&type=" + type);
            //脚本
            if (type == 1) {
                //实际是编辑脚本
                this.addNewScript(id);
            }
        },
        //新tab更新app关联的接口或者脚本
        updateDetailToTtable: function (detailId, type) {
            console.log("updateDetailToTtable entry---++");
            var url = "/page/jvmScriptInfo_add.html?appId=" + vm.appInfo.appId + "&id=" + detailId;
            //脚本
            if (type == 1) {
            }
            var opt = {
                url: url + "#800",
                id: 'jvmScriptInfo_add_' + detailId,
                title: detailId + '.脚本变更',
                height: 800
            }
            addTab(opt);
        },
        addHttpInterface: function () {
            var $iframe = $('<iframe id="modelFrame" name="modelFrame" width="100%" src="" border="0" frameborder="0" scrolling="auto" height="100%">'
                + '浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。'
                + '</iframe>');
            var url = "/page/interfaceEdit.html?appId=" + vm.appInfo.appId;
            // $iframe.attr('src', timestamp(url));
            $iframe.attr('src', url);
            console.log("添加http接口开始---");
            bootbox.dialog({
                message: $iframe,
                title: "添加http接口",
                className: "add-modal",
                size: 'large',
                callback: function () {
                    console.log("添加http接口完毕");
                },
                buttons: {
                    ok: {
                        label: "关    闭",
                        className: 'btn-danger',
                        callback: function () {
                            console.log('addHttpInterface close refresh detailinfo');
                        }
                    }
                }
            });
        },
        appShow: function () {
            var $iframe = $('<iframe id="modelFrame" name="modelFrame" width="100%" src="" border="0" frameborder="0" scrolling="auto" height="100%">'
                + '浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。'
                + '</iframe>');
            var url = "/page/apiShow.html?appId=" + vm.appInfo.appId;
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


