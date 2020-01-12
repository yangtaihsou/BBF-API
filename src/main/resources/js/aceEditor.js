var customData = {
    userInfo : {
        admin : 'admin',
        pwd : 'admin',
        name : 'admin',
        icon : '../img/admin.png'
    },
    blog : {}
}
var AceEdtiorDics = {
    scriptType: [
        {text: 'groovy(后端)', value: 'groovy'},
        {text: 'javascript(前端)', value: 'javascript'},
        {text: 'python(QA)', value: 'python'},
        {text: 'ruby(创新者)', value: 'ruby'}
    ],
    themeType: [{text: 'github', value: 'github'},
        {text: 'clouds', value: 'clouds'}, {text: 'monokai', value: 'monokai'},
        {text: 'twilight', value: 'twilight'}, {text: 'xcode', value: 'xcode'},
        {text: 'clouds_midnight', value: 'clouds_midnight'}, {text: 'vibrant_ink', value: 'vibrant_ink'},
        {text: 'tomorrow_night_eighties', value: 'tomorrow_night_eighties'},
        {text: 'tomorrow_night_bright', value: 'tomorrow_night_bright'},
        {text: 'tomorrow_night_blue', value: 'tomorrow_night_blue'}, {
            text: 'tomorrow_night',
            value: 'tomorrow_night'
        },
        {text: 'tomorrow', value: 'tomorrow'}, {text: 'textmate', value: 'textmate'}, {
            text: 'terminal',
            value: 'terminal'
        },
        {text: 'sqlserver', value: 'sqlserver'}, {text: 'solarized_light', value: 'solarized_light'},
        {text: 'solarized_dark', value: 'solarized_dark'}, {
            text: 'solarized_dark',
            value: 'solarized_dark'
        }, {text: 'pastel_on_dark', value: 'pastel_on_dark'},
        {text: 'mono_industrial', value: 'mono_industrial'}, {
            text: 'merbivore_soft',
            value: 'merbivore_soft'
        }, {text: 'merbivore', value: 'merbivore'},
        {text: 'kuroir', value: 'kuroir'}, {text: 'kr_theme', value: 'kr_theme'}, {
            text: 'katzenmilch',
            value: 'katzenmilch'
        }, {text: 'iplastic', value: 'iplastic'},
        {text: 'idle_fingers', value: 'idle_fingers'}, {text: 'gruvbox', value: 'gruvbox'}, {
            text: 'gob',
            value: 'gob'
        },
        {text: 'eclipse', value: 'eclipse'}, {text: 'dreamweaver', value: 'dreamweaver'}, {
            text: 'dracula',
            value: 'dracula'
        }, {text: 'dawn', value: 'dawn'},
        {text: 'crimson_editor', value: 'crimson_editor'}, {text: 'cobalt', value: 'cobalt'}, {
            text: 'chrome',
            value: 'chrome'
        }, {text: 'chaos', value: 'chaos'},
        {text: 'ambiance', value: 'ambiance'}]
};
var AceEdtiorObject = (function () {
    var instance;
    var _ = function () {
        var me = this;
        me.aceEditorInitPara = {'theme': 'clouds', 'language': 'groovy'};
        //启用提示菜单
        ace.require("ace/ext/language_tools");
        //初始化对象
        me.aceEditor = ace.edit("code");

        me.aceEdtiorInit = function () {

            me.aceEditor = ace.edit("code");
            console.log("aceEdtiorInit----------");
            //设置风格和语言（更多风格和语言，请到github上相应目录查看）
            me.aceEditor.setTheme("ace/theme/" + me.aceEditorInitPara.theme);
            me.aceEditor.session.setMode("ace/mode/" + me.aceEditorInitPara.language);

            //字体大小
            me.aceEditor.setFontSize(18);
            //设置只读（true时只读，用于展示代码）
            me.aceEditor.setReadOnly(false);

            //自动换行,设置为off关闭
            me.aceEditor.setOption("wrap", "free")

            me.aceEditor.setOptions({
                enableBasicAutocompletion: true,
                enableSnippets: true,
                enableLiveAutocompletion: true
            });
            me.aceEditor.resize();
            me.aceEditor.setAutoScrollEditorIntoView(true);
            me.aceEditor.setHighlightActiveLine(false);
        }
    }
    return {
        getInstance: function () {
            if (!instance) {
                instance = new _();
            }
            return instance;
        }
    }
})()