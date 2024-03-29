//生成菜单
var menuItem = Vue.extend({
    name: 'menu-item',
    props:{item:{}},
    template:[
        '<li>',
        '<a v-if="item.type === 0 && item.isShow === 1" href="javascript:;">',
        '<i v-if="item.icon != null" :class="item.icon"></i>',
        '<span>{{item.name}}</span>',
        '<i class="fa fa-angle-left pull-right"></i>',
        '</a>',
        '<ul v-if="item.type === 0 && item.isShow === 1" class="treeview-menu">',
        '<menu-item :item="item" v-for="item in item.list"></menu-item>',
        '</ul>',
        '<a v-if="item.type === 1 && item.isShow === 1" :id="\'menuId_\'+item.menuId" href="javascript:void(0);" :rel="\'#\'+item.url"  onclick="vm.menuClick(this)"><i v-if="item.icon != null" :class="item.icon"></i><i v-else class="fa fa-circle-o"></i> {{item.name}}</a>',
        '</li>'
    ].join('')
});

//注册菜单组件
Vue.component('menuItem',menuItem);
var menuListCache = {};
var vm = new Vue({
    el:'#rrapp',

    data:{
        user:{},
        menuList:{},
        main:"#/page/appInfo_mng.html",
        password:'',
        newPassword:'',
        navTitle:"控制台",
        statistics:{}
    },
    methods: {
        menuClick:function (obj) {
            console.log($(obj).attr('rel'));
            if(window.location.hash == $(obj).attr('rel')){
                vm.reload();
            }else{
                window.location.hash = $(obj).attr('rel');
            }
        },
        reload:function() {
            var url = window.location.hash;
            if(null==url || ""==url){
                return;
            }
            var urlTemp = url.replace('#', '');
            var currentA = $("a[href='"+url+"']");
            if(!menuListCache[urlTemp]){
                return
            }
            var id = menuListCache[urlTemp].menuId;
            var title = menuListCache[urlTemp].name;
            $('#container').addtabs({iframeHeight: 800}).add({'id':id,"url":urlTemp,title:title});
            //导航菜单展开
            if(currentA.length==0){
                console.log(title);
                $(".sidebar-menu li").removeClass("active");
                currentA.parents("li").addClass("active");
            }
        },
        getMenuList: function (event) {
            var self = this;
            $.getJSON("js/menu.json?_"+$.now(), function(r){
            //$.getJSON("/menu?_"+$.now(), function(r){
                vm.menuList = r.menuList;
                $(r.menuList).each(function (i,elem) {
                    menuListCache[elem.url] = elem;
                });
                // console.log(JSON.stringify(menuListCache));
                setTimeout(function () {
                    self.reload();
                },100);
            });
        }
    },
    created: function(){
        var self = this;
        self.getMenuList();

        console.log("created");
    },
    mounted:function () {
        var self = this;
        window.onhashchange = function() {
            self.reload();
        };

        $("#tabs").on('click','li',function () {
            window.location.hash = "#"+$(this).attr('aria-url');
        });

        window.location.hash = this.main;
        console.log("mounted...");
    },updated:function () {
        console.log("updated...");
    },destroyed:function () {
        console.log("destroyed...");
    }
});

function setIframeHeight(iframe) {

    var url = $(iframe).attr('src');
    var r =/#\d+/;
    var v_hash = r.exec(url);

    var height = 0;
    if(!v_hash){
        height = 800;
    }else{
        height = v_hash[0].replace('#', '');
    }
    $(iframe).css('height',height+"px");
}

