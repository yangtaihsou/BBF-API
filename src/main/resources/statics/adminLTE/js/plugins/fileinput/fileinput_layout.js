var layoutTemplates = {
    main1: '{preview}\n' +
    '<div class="kv-upload-progress hide"></div>\n' +
    '<div class="input-group {class}">\n' +
    '   {caption}\n' +
    '   <div class="input-group-btn">\n' +
    '       {remove}\n' +
    '       {cancel}\n' +
    '       {upload}\n' +
    '       {browse}\n' +
    '   </div>\n' +
    '</div>',
    main2: '{preview}\n<div class="kv-upload-progress hide"></div>\n{remove}\n{cancel}\n{upload}\n{browse}\n',
    preview: '<div class="file-preview {class}">\n' +
    '    <div class="close fileinput-remove">&times;</div>\n' +
    '    <div class="{dropClass}">\n' +
    '    <div class="file-preview-thumbnails">\n' +
    '    </div>\n' +
    '    <div class="clearfix"></div>' +
    '    <div class="file-preview-status text-center text-success"></div>\n' +
    '    <div class="kv-fileinput-error"></div>\n' +
    '    </div>\n' +
    '</div>',
    icon: '<span class="glyphicon glyphicon-file kv-caption-icon"></span>',
    caption: '<div tabindex="-1" class="form-control file-caption {class}">\n' +
    '   <span class="file-caption-ellipsis">&hellip;</span>\n' +
    '   <div class="file-caption-name"></div>\n' +
    '</div>',
    modal: '<div id="{id}" class="modal fade">\n' +
    '  <div class="modal-dialog modal-lg">\n' +
    '    <div class="modal-content">\n' +
    '      <div class="modal-header">\n' +
    '        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>\n' +
    '        <h3 class="modal-title">Detailed Preview <small>{title}</small></h3>\n' +
    '      </div>\n' +
    '      <div class="modal-body">\n' +
    '        <textarea class="form-control" style="font-family:Monaco,Consolas,monospace; height: {height}px;" readonly>{body}</textarea>\n' +
    '      </div>\n' +
    '    </div>\n' +
    '  </div>\n' +
    '</div>',
    progress: '<div class="progress">\n' +
    '    <div class="{class}" role="progressbar"' +
    ' aria-valuenow="{percent}" aria-valuemin="0" aria-valuemax="100" style="width:{percent}%;">\n' +
    '        {percent}%\n' +
    '     </div>\n' +
    '</div>',
    footer: '<div class="file-thumbnail-footer">\n' +
    '    <div class="file-caption-name">{caption}</div>\n' +
    '    {actions}\n' +
    '</div>',
    actions: '<div class="file-actions">\n' + //删除掉上传按钮
    '    <div class="file-footer-buttons">\n' +
    '        {delete}{other}' +
    '    </div>\n' +
    '    <div class="file-upload-indicator" tabindex="-1" title="{indicatorTitle}">{indicator}</div>\n' +
    '    <div class="clearfix"></div>\n' +
    '</div>',
    actionDelete: '<button type="button" class="kv-file-remove {removeClass}" ' +
    'title="{removeTitle}"{dataUrl}{dataKey}>{removeIcon}</button>\n',
    actionUpload: '<button type="button" class="kv-file-upload {uploadClass}" title="{uploadTitle}">' +
    '   {uploadIcon}\n</button>\n'
}