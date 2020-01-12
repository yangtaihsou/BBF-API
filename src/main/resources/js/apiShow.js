var appId = T.p("appId");
var vm = new Vue({
    el: '#rrapp',
    data: {
        http: {
            id: null,
            appId: null,
            url: domain + "/api/rundApi/",
            body: null,
            contentType: {key: 'Content-Type', value: 'application/json'},
            header: [],
            formData: [],
            method: 'POST',
            mockInfo: {
                scriptText: '',
                scriptType: '',
                themeType: '',
            },
            mocked: false
        },
        exeTime:null,
        serverTime:null,
        httpresponse: null,
        httpbody: '', //展示标记
        httpformData: '',//展示标记
        aceEdtiorObject: AceEdtiorObject.getInstance()
    },
    created: function () {
        isLogin();
        console.log('created....');
        this.getInfo(appId)
    },
    mounted: function () {
        this.init();
    },

    methods: {
        init: function () {
            console.info("methods init");
            this.http.url = this.http.url + appId + '?ssoToken=' + token,
                this.http.appId = appId;
            AceEdtiorObject.getInstance().aceEdtiorInit();
        },
        saveOrUpdate: function (tips) {

            var jsonStyletext = formatJson(vm.http.body);
            if (jsonStyletext == 'formatError') {
                alert("http请求的body参数不是json格式");
                return;
            }
            var url = (vm.http.id == null || vm.http.id == '') ? domain + "/console/appDemoInfo/save"+'?ssoToken='+token
                : domain + '/console/appDemoInfo/edit?ssoToken=' + token;
            var postdata = {};
            postdata.id = vm.http.id;
            postdata.appId = vm.http.appId;
            postdata.url = vm.http.url;
            if (vm.http.body) {
                postdata.body = JSON.stringify(vm.http.body);
            }
            postdata.contentType = JSON.stringify(vm.http.contentType);
            postdata.header = JSON.stringify(vm.http.header);
            postdata.formData = JSON.stringify(vm.http.formData);
            postdata.method = vm.http.method;

            var scriptText = vm.aceEdtiorObject.aceEditor.getValue();
            vm.http.mockInfo.scriptText = scriptText;
            postdata.mockInfo = JSON.stringify(vm.http.mockInfo);
            postdata.mocked = vm.http.mocked;
            var data = JSON.stringify(postdata)
            $.ajax({
                type: 'post',
                url: url,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                async: true,
                data: data,
                success: function (r) {
                    if (r.code == 0) {
                        vm.http.id = r.appDemoInfo.id;
                        if (tips != false) {
                            alert('操作成功');
                        }
                    } else {
                        if (tips != false) {
                            alert(r.reason);
                        }
                    }
                }
            });
        },
        getInfo: function (appId) {
            $.get(domain + "/console/appDemoInfo/info/" + appId + '?ssoToken=' + token, function (r) {
                if (r.code == 0) {
                    if (r.appDemoInfo) {
                        vm.http.id = r.appDemoInfo.id;
                        vm.http.appId = r.appDemoInfo.appId;
                        vm.http.url = r.appDemoInfo.url;
                        if (r.appDemoInfo.body) {
                            vm.http.body = JSON.parse(r.appDemoInfo.body);
                        }
                        vm.http.contentType = JSON.parse(r.appDemoInfo.contentType);
                        vm.http.header = JSON.parse(r.appDemoInfo.header);
                        vm.http.formData = JSON.parse(r.appDemoInfo.formData);
                        vm.http.method = r.appDemoInfo.method;
                        vm.http.mockInfo = JSON.parse(r.appDemoInfo.mockInfo);
                        vm.http.mocked = r.appDemoInfo.mocked;
                        vm.aceValueSet();

                    }
                } else {
                    alert(r.reason);
                }
            });
        },

        aceValueSet: function () {
            vm.aceEdtiorObject.aceEdtiorInit();
            vm.aceEdtiorObject.aceEditor.setTheme("ace/theme/" + vm.http.mockInfo.themeType);
            vm.aceEdtiorObject.aceEditor.setValue(vm.http.mockInfo.scriptText);//设置内容
        },
        show: function () {

            var _this = this
            this.saveOrUpdate(false);
            var jsonStyletext = formatJson(vm.http.body);
            if (jsonStyletext == 'formatError') {
                alert("http请求的body参数不是json格式");
                return;
            }
            var data = vm.http.body;
            if (vm.http.contentType.value == 'application/x-www-form-urlencoded') {
                var formDataMap = {};
                for (var j = 0, len = vm.http.formData.length; j < len; j++) {
                    formDataMap[vm.http.formData[j].key] = vm.http.formData[j].value;
                }
                data = formDataMap
            }
            var headerMap = {};
            if (vm.http.header) {
                for (var j = 0, len = vm.http.header.length; j < len; j++) {
                    headerMap[vm.http.header[j].key] = vm.http.header[j].value;
                }
            }
            vm.exeTime = null;
            vm.serverTime = null;
            var time = new Date().getTime()
            $.ajax({
                type: vm.http.method,
                url: vm.http.url,
                contentType: vm.http.contentType.value + ";charset=UTF-8",
                data: data,
                headers: headerMap,
                success: function (r) {
                    _this.httpresponse = r;
                    var end = new Date().getTime() -time;
                    vm.exeTime = "共耗时"+end+"ms";
                    vm.serverTime = ",服务端耗时"+r.time+"ms";
                },
                error: function (event, xhr, options, exc) {
                    alert("请检查登录");
                },
            });
        },
        /*   addHeader:function(){
               //this.http.header['key']='value'
               Vue.set(this.http.header,'','');
           },
           addFormData:function(){
              // this.http.formData['key']='value'
               Vue.set(this.http.formData,'','');
           },*/
        addHeader: function () {
            this.http.header.push({
                key: null,
                value: null
            })

        },
        addFormData: function () {
            this.http.contentType.value = 'application/x-www-form-urlencoded'
            this.httpbody = 'hidden'
            this.httpformData = 'visible'
            this.http.formData.push({
                key: null,
                value: null
            })
        },

        removeHeader: function (index) {

            this.http.header.splice(index, 1);

        },
        removeFormData: function (index) {
            console.log(index)
            this.http.formData.splice(index, 1);
        },
        jsonFormart: function () {
            if (vm.http.body) {
                var jsonStyletext = formatJson(vm.http.body);
                if (jsonStyletext == 'formatError') {
                    alert("http请求的body参数不是json格式");
                    return;
                } else {
                    vm.http.body = formatJson(vm.http.body);
                }
            }
            if (vm.httpresponse) {
                if (typeof vm.httpresponse === 'object') {
                    var jsonStyletext = formatJson(JSON.stringify(vm.httpresponse));
                    if (jsonStyletext == 'formatError') {
                        alert("http响应的response参数不是json格式");
                        return;
                    } else {
                        vm.httpresponse = formatJson(JSON.stringify(vm.httpresponse));
                    }
                }
            }
        },
        selectContentType: function (ele) {
            var contentType = ele.target.value;
            console.log("switch contentType:" + contentType);
            if (contentType == 'application/json') {
                this.httpbody = 'visible'
                this.httpformData = 'hidden'
            }
            if (contentType == 'application/x-www-form-urlencoded') {
                this.httpbody = 'hidden'
                this.httpformData = 'visible'
            }
        },
        selectScripteVal: function (ele) {
            var scripteType = ele.target.value;
            vm.aceEdtiorObject.aceEditor.session.setMode("ace/mode/" + scripteType);
        },
        selectThemeTypeVal: function (ele) {
            var themeType = ele.target.value;
            console.log("switch type:" + themeType);
            vm.aceEdtiorObject.aceEditor.setTheme("ace/theme/" + themeType);
        }
    }
});