var commonErp = (function () {
    var instance;
    var Erp = function () {
        var me = this;
        me.map = new Map();
        me.serviceUrl = '';
        me.trimForm = function () {
            if ($('form').length > 0) {
                $('form').find('input[type=text]').each(function () {
                    $(this).val($(this).val().trim());
                });
            }
        }

        me.postMultipartAjaxCallBack = function (form, url, fn, fnP1) {
            me.trimForm();
            me.releaseLastAjax();
            form.ajaxSubmit({
                beforeSend: function () {
                    top.formMask();
                },
                type: "post",
                url: url,//url+"?"+form.serialize()
                cache: false,
                async: true,
                success: function (result) {

                    var errorResult = eval(result);
                    if (errorResult.errorCode != null && errorResult.errorCode != undefined) {
                        // alertBootbox(errorResult.errorMsg);
                    } else {
                        fn.call(fnP1, result, fnP1);//回调
                    }
                },
                complete: function (XMLHttpRequest, textStatus) {
                    top.formUnmask();

                }
            })
        }
        $.fn.serializeObject = function () {
            var o = {};
            var a = this.serializeArray();
            $.each(a, function () {
                if (o[this.name] !== undefined) {
                    if (!o[this.name].push) {
                        o[this.name] = [o[this.name]];
                    }
                    o[this.name].push(this.value || '');
                } else {
                    o[this.name] = this.value || '';
                }
            });
            return o;
        };
        me.postFormAjaxCallBack = function (form, url, fn, fnP1) {
            me.trimForm();
            me.releaseLastAjax();
            $.ajax({
                xhrFields: {withCredentials: true}, // 发送凭据
                dataType: "json",
                contentType: "application/json;charset=utf-8;",
                beforeSend: function () {
                    top.formMask();
                },
                type: "post",
                url: me.serviceUrl + url,//url+"?"+form.serialize()
                data: form.serialize(),
                cache: false,
                async: true,
                success: function (result) {

                    var errorResult = eval(result);
                    if (errorResult.errorCode != null && errorResult.errorCode != undefined) {
                        alertBootbox(errorResult.errorMsg);
                    } else {
                        fn.call(fnP1, result, fnP1);//回调
                    }
                },
                complete: function (XMLHttpRequest, textStatus) {
                    top.formUnmask();

                }
            })
        }
        me.postAjaxCallBack = function (paraData, url, fn, fnP1, contentType) {
            me.trimForm();
            me.releaseLastAjax();
            url = me.serviceUrl + url;
            var data = paraData;
            if (contentType && contentType == 'form') {
                contentType = 'application/x-www-form-urlencoded;charset=UTF-8;';
            } else {
                contentType = "application/json;charset=utf-8;";
                data = JSON.stringify(paraData);
            }
            console.log(url);
            $.ajax({
                xhrFields: {withCredentials: true}, // 发送凭据
                contentType: contentType,
                beforeSend: function () {
                    top.formMask();
                },
                type: "post",
                url: url,
                cache: false,
                async: true,
                timeout: 60000, //超时时间设置，单位毫秒
                data: data,
                success: function (result) {
                    var errorResult = eval(result);
                    if (errorResult.errorCode != null && errorResult.errorCode != undefined) {
                        alertBootbox(errorResult.errorMsg);
                    } else {
                        fn.call(fnP1, result, fnP1);//回调
                    }
                },
                complete: function (XMLHttpRequest, textStatus) {
                    top.formUnmask();
                }
            })
        }
        me.getJsonpAjax = function (url) {
            me.trimForm();
            $.ajax({
                beforeSend: function () {
                    top.formMask();
                },
                type: "get",
                url: me.serviceUrl + url + "&_=" + new Date().getTime(),
                dataType: 'jsonp',
                cache: false,
                async: true,
                success: function (result) {
                    //console.info(result);
                },
                complete: function (XMLHttpRequest, textStatus) {
                    top.formUnmask();
                }
            })
        }
        me.getAjaxCallBack = function (url, fn, fnP1) {
            me.trimForm();
            $.ajax({
                xhrFields: {withCredentials: true}, // 发送凭据
                dataType: "json",
                contentType: "application/json;charset=utf-8;",
                beforeSend: function () {
                    top.formMask();
                },
                type: "get",
                url: me.serviceUrl + url ,
                cache: false,
                async: true,
                success: function (result) {
                    fn.call(null, result, fnP1);//回调
                },
                complete: function (XMLHttpRequest, textStatus) {
                    top.formUnmask();
                }
            })
        }

        me.ajaxRequesArr = [];

        me.releaseLastAjax = function () {
            for (var i = 0; i < me.ajaxRequesArr.length; i++) {
                var status = me.ajaxRequesArr[i].status;
                if (status != 200) {
                    me.ajaxRequesArr[i].abort();
                }
            }
            me.ajaxRequesArr = [];
        }
        /**
         * 该验证支持js定义和html5两种方式
         * 可以定义fields，或者在属性上加 required type pattern等属性标记,并标注 data-bv-*-message作为验证失败的消息
         * demo:http://bv.doc.javake.cn/examples/#html5
         */
        me.initBootstrapValidator = function (form, button, submitHandler, fields) {
            $(form).bootstrapValidator({
                submitButtons: button,//触发按钮
                tipType: true,//tip框提示
                submitHandler: function (validator, form, submitButton) {//验证通过回调函数
                    submitHandler();
                },
                feedbackIcons: {//样式
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: fields
            });
            if (button != null && button != undefined && submitHandler != null && submitHandler != undefined) {
                $(form).find(button).on('click', function () {
                    var $target = $($(this).attr('data-toggle'));
                    $target.toggle();
                    if (!$target.is(':visible')) {
                        $(form).data('bootstrapValidator').disableSubmitButtons(false);
                    }
                });
            }


        }

        me.initDataTable = function (datatable, queryform, sAjaxSource, columns, columnDefs, aButtons, callBack, bPaginate, iDisplayLength, sortParams) {
            if (bPaginate == undefined || bPaginate == null) {
                bPaginate = true;
            }
            if (iDisplayLength == '' || iDisplayLength == undefined || iDisplayLength == null) {
                iDisplayLength = 10;
            }

            var bSort = false;
            var orderDirects = [];
            if (sortParams != null) {
                if (sortParams.isSort == true) {
                    bSort = true;
                }
                if (sortParams.defaultOrderDirect == null || sortParams.defaultOrderDirect.length == 0) {
                    orderDirects = [[0, "desc"]];
                } else {
                    orderDirects = sortParams.defaultOrderDirect
                }

            }
            var dtable = $(datatable).dataTable({
                "sDom": 'T<"clear">lfrtip',//自定义布局
                "oTableTools": {//TableTools是一个对table内的数据进行复制、保存(xls、pdf等)等操作的Jquery DataTables扩展控件
                    "aButtons": aButtons
                },
                "bPaginate": bPaginate,// 是否显示分页器
                "bFilter": false,// 是否使用搜索功能
                "bLengthChange": false,// 是否显示每页行数选择条
                "iDisplayLength": iDisplayLength,// 每页显示行数
                "bSort": bSort,
                "oLanguage": {//国际化，使用中文
                    "sUrl": "/static/adminLTE/js/datatable/zh_CN.json"
                },
                "bScrollCollapse": true,//是否开启DataTables的高度自适应，当数据条数不够分页数据条数的时候，插件高度是否随数据条数而改变
                "bAutoWidth": true,
                "scrollX": true,
                "autoWidth": true,
                "bProcessing": true,//DataTables载入数据时，是否显示‘进度’提示
                "bServerSide": true,//是否启动服务器端数据导入
                "sAjaxSource": sAjaxSource,//服务器地址
                "sServerMethod": "POST", //请求类型
                "fnServerData": function (sSource, aoData, fnCallback) {//向服务器发送请求
                    var data = $(queryform).serializeObject();
                    data.aoData = JSON.stringify(aoData);
                    $.ajax({
                        type: 'post',
                        url: me.serviceUrl + sSource,
                        dataType: "json",
                        data: data,
                        success: function (resp) {
                            //console.log(resp);
                            var errorResult = eval(resp);
                            if (errorResult.errorCode != null && errorResult.errorCode != undefined) {
                                alertBootbox(errorResult.errorMsg);
                            } else {
                                fnCallback(resp);
                            }

                        }
                        // error:errorHandler
                    });
                },

                "createdRow": function (row, data, index) {

                    checkBtns();

                },
                "fnInitComplete": function (oSettings, json) {//row初始化后回调函数

                    if (callBack != null && callBack != undefined) {
                        var tableID = $(datatable).selector;
                        $(tableID + " tbody").on('dblclick', 'tr', function () {
                            var row = dtable.DataTable().row(this);
                            var rowdata = row.data();
                            callBack(rowdata);
                        });
                    }
                },
                "order": orderDirects,
                columns: columns,//列属性
                columnDefs: columnDefs//列数据格式化

            });

            return dtable;
        };

        function errorHandler(xhr, textStatus, error) {
            if (textStatus === 'timeout') {
                alert('The server took too long to send the data.');
            } else {
                alert('An error occurred on the server. Please try again in a minute.');
            }

            $('.dataTable').dataTable().fnProcessingIndicator(false);
        }

        me.initEmptyDataTable = function (datatable, columns, columnDefs, aButtons, callBack, pageSize) {
            return $(datatable).dataTable({
                "sDom": 'T<"clear">lfrtip',
                "oTableTools": {
                    "aButtons": aButtons
                },
                "bPaginate": false,// 分页按钮
                "bFilter": false,// 搜索栏
                "bLengthChange": false,// 每行显示记录数
                "iDisplayLength": pageSize ? pageSize : 100,// 每页显示行数
                "bSort": false,// 排序
                "oLanguage": {
                    "sUrl": "/static/adminLTE/js/datatable/zh_CN.json"
                },
                "fnInitComplete": function (oSettings, json) {//row初始化后回调函数
                    if (callBack != null && callBack != undefined) {
                        var tableID = $(datatable).selector;
                        $(tableID + " tbody").on('dblclick', 'tr', function () {
                            var row = dtable.DataTable().row(this);
                            var rowdata = row.data();
                            callBack(rowdata);
                        });
                    }
                },
                "bScrollCollapse": true
            });
        };

        me.getQueryString = function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]);
            return null;
        }
        me.initSelectDataTable = function (datatable, queryform, sAjaxSource, columns, columnDefs, aButtons, pageSize) {
            return $(datatable).dataTable({
                "sDom": 'T<"clear">lfrtip',
                "oTableTools": {
                    "aButtons": aButtons
                },
                "fnInitComplete": function (oSettings, json) {
                    $("#datatable tbody").on('click', 'tr', function () {
                        $('input[type=checkbox]').attr("checked", false);
                        if ($(this).hasClass('selected')) {
                            $(this).removeClass('selected');
                        } else {
                            $('#datatable tbody tr.selected').removeClass('selected');
                            $(this).addClass('selected');
                            $(this).find("input[type='checkbox']").prop('checked', true);
                        }
                    });
                },
                "bPaginate": true,// 分页按钮
                "bFilter": false,// 搜索栏
                "bLengthChange": false,// 每行显示记录数
                "iDisplayLength": pageSize ? pageSize : 10,// 每页显示行数
                "bSort": false,// 排序
                "oLanguage": {
                    "sUrl": "/static/adminLTE/js/datatable/zh_CN.json"
                },
                "bScrollCollapse": true,
                "bProcessing": true,
                "bServerSide": true,
                "sAjaxSource": sAjaxSource,
                "sServerMethod": "POST",
                "fnServerData": function (sSource, aoData, fnCallback) {
                    var data = $(queryform).serializeObject();
                    data.aoData = JSON.stringify(aoData);
                    $.ajax({
                        type: 'post',
                        url: me.serviceUrl + sSource,
                        dataType: "json",
                        data: data,
                        success: function (resp) {
                            fnCallback(resp);
                        }
                    });
                },
                columns: columns,
                columnDefs: columnDefs
            });
        }
        me.initSystemList = function (systemId) {
            me.getAjaxCallBack('/system/systemList', me.querySystemCallBack, systemId);
        }
        me.querySystemCallBack = function (data,systemId) {
            if (data.code == "000000" && data.list) {
                var html='';
                data.list.forEach(function (e) {
                    html = html + "<option value='" + e.systemId + "'>" + e.systemName + "</option>";
                });
                $('#'+systemId).append(html);
            } else {
                alertBootbox("加载信息异常，请联系管理员！");
            }
        }
    }
    return {
        getInstance: function () {//子页面都是导到index.jsp，所以对象可以只初始化一次
            if (!instance) {
                instance = new Erp();
            }
            return instance;
        }
    }
})();


var DocumentElementMap = {
    elment: {
        'text': 'input:text',
        'checkbox': 'input:checkbox',
        'select': 'select',
        'button': 'button',
        'radio': 'input:radio',
        'hidden': 'input:hidden'
    },
    docElementArr: {},
    docElementCount: 0,
    clear: function (contextObj) {
        this.docElementArr = $("#" + contextObj + " :input");
        this.docElementCount = this.docElementArr.size();
        for (var index = 0; index < this.docElementCount; index++) {
            var contextElement = this.docElementArr.eq(index);
            for (var e in this.elmentClear) {
                if (contextElement.is(e)) {
                    if (typeof (this.elmentClear[e]) == "function") {
                        this.elmentClear[e](contextElement);
                    }
                }
            }
        }
    },
    elmentClear: {
        'input:text': function (obj) {
            obj.val('');
        },
        'input:checkbox': function (obj) {
            obj.removeAttr("checked");
        },
        'select': function (obj) {
            obj.get(0).selectedIndex = 0;
        },
        'button': function (obj) {
        },
        'input:radio': function (obj) {
            obj.removeAttr("checked");
        },
        'input:hidden': function (obj) {
        }
    }
}

function showLoading() {
    $('body').css("overflow", "hidden")
    $("#loading_cover").show();
}

function hideLoading() {
    $('body').css("overflow", "")
    $("#loading_cover").hide();
}

(function ($) {
    String.prototype.trim = function () {
        return this.replace(/(^\s*)|(\s*$)/g, "");
    }
    String.prototype.ltrim = function () {
        return this.replace(/(^\s*)/g, "");
    }
    String.prototype.rtrim = function () {
        return this.replace(/(\s*$)/g, "");
    }
})(jQuery);


Date.prototype.pattern = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    var week = {
        "0": "\u65e5",
        "1": "\u4e00",
        "2": "\u4e8c",
        "3": "\u4e09",
        "4": "\u56db",
        "5": "\u4e94",
        "6": "\u516d"
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    if (/(E+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f" : "\u5468") : "") + week[this.getDay() + ""]);
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}      