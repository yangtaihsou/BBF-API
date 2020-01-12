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
var ztree;

var id = T.p("id");
var appId = T.p("appId");
var vm = new Vue({
    el: '#rrapp',
    data: {
        title: "新增",
        jvmScriptInfo: {
            scriptId: null,
            scriptName: null,
            scriptText: null,
            scriptType: '',
            version: null,
            indexOrder: null,
            themeType: '',
            demoInputPara: null,
            appId: null,
            remark: null
        },
        exeTime:null,
        scriptResponseData: "",
        aceEdtiorObject: AceEdtiorObject.getInstance(),
        versions : []
    },
    created: function () {
        isLogin();
        if (id != null) {
            console.info("created=" + id);
            this.title = "修改";
            this.getInfo(id)
        } else {
            this.getUuid();
        }

    },
    mounted: function () {//ace editor必须放在mounted
       /* $('select').select2({
           // placeholder:'请选择',
        });*/
        this.init();
    },
    methods: {
        getUuid: function () {
            $.get(domain + "/test/page/uuid", function (r) {
                if (r.code == 0) {
                    console.info("uuid init finished=" + r.uuid);
                    vm.jvmScriptInfo.scriptId = r.uuid;
                    console.info("appId init finished=" + appId);
                    vm.jvmScriptInfo.appId = appId;
                } else {
                    alert(r.reason);
                }
            });
        },
        init: function () {
            console.info("methods init");
            // vm.aceEdtiorObject.aceEdtiorInit();
            AceEdtiorObject.getInstance().aceEdtiorInit();
        },
        getInfo: function (id) {
            $.get(domain + "/console/jvmScriptInfo/info/" + id+'?ssoToken='+token, function (r) {
                console.info("r=" + r);
                vm.jvmScriptInfo.id = r.jvmScriptInfo.id;
                vm.jvmScriptInfo.scriptId = r.jvmScriptInfo.scriptId;
                vm.jvmScriptInfo.scriptName = r.jvmScriptInfo.scriptName;
                vm.jvmScriptInfo.scriptType = r.jvmScriptInfo.scriptType;
                vm.jvmScriptInfo.themeType = r.jvmScriptInfo.themeType;
                vm.jvmScriptInfo.demoInputPara = r.jvmScriptInfo.demoInputPara;
                vm.jvmScriptInfo.version = r.jvmScriptInfo.version;
                vm.jvmScriptInfo.remark = r.jvmScriptInfo.remark;
                vm.jvmScriptInfo.appId = r.jvmScriptInfo.appId;
                vm.jvmScriptInfo.scriptText = r.jvmScriptInfo.scriptText;
                vm.aceValueSet();
                vm.getHistoryList();
            });
        },
        aceValueSet: function (r) {
            vm.aceEdtiorObject.aceEdtiorInit();
            vm.aceEdtiorObject.aceEditor.setTheme("ace/theme/" + vm.jvmScriptInfo.themeType);
            vm.aceEdtiorObject.aceEditor.setValue(vm.jvmScriptInfo.scriptText);//设置内容
        },
        saveOrUpdate: function () {
            var url = (vm.jvmScriptInfo.id == null || vm.jvmScriptInfo.id == '') ? domain + "/console/jvmScriptInfo/save"+'?ssoToken='+token
                : domain + "/console/jvmScriptInfo/edit"+'?ssoToken='+token;
            var scriptText = vm.aceEdtiorObject.aceEditor.getValue();
            vm.jvmScriptInfo.scriptText = scriptText;
            $.ajax({
                type: "POST",
                url: url,
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                data: vm.jvmScriptInfo,
                success: function (r) {
                    if (r.code == 0) {
                        alert('操作成功', function (index) {
                            //vm.back();
                            vm.getHistoryList();
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
        selectScripteVal: function (ele) {
            var scripteType = ele.target.value;
            vm.aceEdtiorObject.aceEditor.session.setMode("ace/mode/" + scripteType);
        },
        selectThemeTypeVal: function (ele) {
            var themeType = ele.target.value;
            console.log("switch type:" + themeType);
            vm.aceEdtiorObject.aceEditor.setTheme("ace/theme/" + themeType);
        },
        runScript: function () {
            var url = domain + "/api/runScript";
            var scriptText = vm.aceEdtiorObject.aceEditor.getValue();
            vm.jvmScriptInfo.scriptText = scriptText;
            var time = new Date().getTime()
            $.ajax({
                type: "POST",
                url: url,
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                data: vm.jvmScriptInfo,
                success: function (r) {
                    var end = new Date().getTime() -time;
                    if (r.code == 0) {
                        vm.scriptResponseData = r.reponse;
                        vm.exeTime = end+"ms";
                    } else {
                        alert(r.reason);
                    }
                }
            });
        },

        jsonFormart: function () {
            var formatjson = this.formatJson(vm.scriptResponseData, "");
            if (formatjson != 'formatError') {
                vm.scriptResponseData = formatjson;
            } else {
                alert("请输入正确的json格式");
            }
        },
        formatJson: function (txt, compress/*是否为压缩模式*/) {
            if (/^\s*$/.test(txt)) {
                return 'formatError';
            }
            try {
                var data = JSON.parse(txt);
                var jsonStr = JSON.stringify(data, null, '~~!!~~!!');
                var reg = /~~!!~~!!/g;
                return jsonStr.replace(reg, '          ');
            } catch (e) {
                return 'formatError';
            }
        },
        getHistoryList:function () {
            var url = domain + "/console/jvmScriptInfoHistory/list"+'?ssoToken='+token;
            $.ajax({
                type: "GET",
                url: url+"&scriptId="+vm.jvmScriptInfo.scriptId,
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                success: function (r) {
                    vm.versions=[]
                    if (r.code == 0) {
                        r.data.forEach(function(item) {
                            var info = {};
                            info.id = item.id;
                            info.text = formatDateToStr(item.createdDate)
                            vm.versions.push(info)
                        })
                    } else {
                        alert(r.reason);
                    }
                }
            });
        },
        selectVersion: function (ele) {//选择脚本代码版本展示
            var url = domain + "/console/jvmScriptInfoHistory/info"+'?ssoToken='+token;
            var id = ele.target.value;
            if(id==''){
                return
            }
            confirm('是否展示此版本的代码', function (result) {
                if(result!='ok')
                    return;
                $.ajax({
                    type: "GET",
                    url: url+"&id="+id,
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    success: function (r) {
                        if (r.code == 0) {
                            vm.jvmScriptInfo.scriptText = r.jvmScriptInfo.scriptText;
                            vm.aceValueSet();
                            console.log("回滚展示-----");
                        } else {
                            alert(r.reason);
                        }
                    }
                });
            });
        }


    }
});