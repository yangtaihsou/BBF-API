var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "code",
            pIdKey: "parentCode",
            rootPId: ""
        },
        key: {
            url:"nourl"
        }
    }
};
var ztree;

var id = T.p("id");
var vm = new Vue({
    el:'#rrapp',
    data:{
        title:"新增",
        caseConfig:{
            className:"",
            caseText:"",
            scriptType:null,
            version:"",
            systemCode:null,
            method:"",
            methodSelect:"",
            inputPara:"",
            responseData:""
        },
        caseConfigRun:{
            method:"",
            inputPara:""
        },
        aceEdtiorObject:AceEdtiorObject.getInstance(),
        caseMetods:[]
    },
    created: function() {
        if(id != null){
            console.info("created="+id);
            this.title = "修改";
            this.getInfo(id)
        }

    },
    mounted:function(){//ace editor必须放在mounted
        this.init();
    },
    methods: {
        init:function(){
            console.info("methods init");
           // vm.aceEdtiorObject.aceEdtiorInit();
            AceEdtiorObject.getInstance().aceEdtiorInit();
        },
        getInfo: function(id){
            $.get("/console/caseConfig/info/"+id, function(r){
                console.info("r="+r);
                vm.caseConfig.className = r.caseConfig.className;
                vm.caseConfig.caseText = r.caseConfig.caseText;
                vm.caseConfig.scriptType = r.caseConfig.scriptType;
                vm.caseConfig.id = r.caseConfig.id;
                vm.caseConfig.version = r.caseConfig.version;
                vm.caseConfig.systemCode = r.caseConfig.systemCode;
                vm.aceValueSet(r);
            });
        },
        aceValueSet:function(r){
            vm.aceEdtiorObject.aceEdtiorInit();
            vm.aceEdtiorObject.aceEditor.setValue( r.caseConfig.caseText);//设置内容
            console.log("-----aceValueSet")

        },
        saveOrUpdate: function () {
            var url = (vm.caseConfig.id == null || vm.caseConfig.id == '') ? "/console/caseConfig/save" : "/console/caseConfig/edit";
            var caseText =  vm.aceEdtiorObject.aceEditor.getValue();
            vm.caseConfig.caseText=caseText;
            $.ajax({
                type: "POST",
                url: url,
                contentType : "application/x-www-form-urlencoded; charset=UTF-8",
                data: vm.caseConfig,
                success: function(r){
                    if(r.code==0){
                        alert('操作成功', function(index){
                            //vm.back();
                        });
                    }else{
                        alert(r.reason);
                    }
                }
            });
        },
        back: function () {
            history.go(-1);
        },
        selectScripteVal:function (ele) {

            var scripteType = ele.target.value;

             vm.aceEdtiorObject.aceEditor.session.setMode("ace/mode/"+scripteType);
        },
        selectThemeTypeVal:function (ele) {
            var themeType = ele.target.value;
            vm.aceEdtiorObject.aceEditor.setTheme("ace/theme/"+themeType);
        },
        getCaseMethod: function () {
            var url =  "/console/caseConfig/getCaseMethod";
            var caseText =  vm.aceEdtiorObject.aceEditor.getValue();
            vm.caseConfig.caseText=caseText;
            $.ajax({
                type: "POST",
                url: url,
                contentType : "application/x-www-form-urlencoded; charset=UTF-8",
                data: vm.caseConfig,
                success: function(r){
                    if(r.code==0){
                      //  vm.caseMetods =["4","2","3"];
                        vm.caseMetods = r.methodMap;
                    }else{
                        alert(r.reason);
                    }
                }
            });
        },
        runCaseMethod: function () {
            var url =  "/console/caseConfig/runCaseMethod";
            var caseText =  vm.aceEdtiorObject.aceEditor.getValue();
            vm.caseConfig.caseText=caseText;
            if(!vm.caseConfig.method){
                vm.caseConfig.method = vm.caseConfig.methodSelect;
            }
            $.ajax({
                type: "POST",
                url: url,
                contentType : "application/x-www-form-urlencoded; charset=UTF-8",
                data: vm.caseConfig,
                success: function(r){
                    if(r.code==0){
                        console.log(r.reponse);
                        vm.caseConfig.responseData = r.reponse;
                    }else{
                        alert(r.reason);
                    }
                }
            });
        }
    }
});