/*
 * Copyright (c) 2008 Greg Weber greg at gregweber.info
 * Dual licensed under the MIT and GPL licenses
 *
 * jquery plugin
 * make an html table editable by the user
 *   user clicks on a cell, edits the value,
 *   then presses enter or clicks on any cell to save the new value
 *   pressing escape returns the cell text to its orignal text
 *
 * documentation at http://gregweber.info/projects/uitableedit
 * 
 * var t = $('table')
 * $.uiTableEdit( t ) // returns t
 *
 * options : off, mouseDown, find, dataEntered, dataVerify, editDone
 *   off : turns off table editing
 *   find : defaults to tbody > tr > td
 *   mousedown : called in context of the table cell (as a normal event would be)
 *     if mouseDown returns false, cell will not become editable
 *   dataVerify : called in context of the cell,
 *     if dataVerify returns false, cell will stay in editable state
 *     if dataVerify returns text, that text will replace the cell's text
 *     arguments are the cell's text, original text, event, jquery object for the cell
 *   editDone : invoked on completion
 *     arguments: td cell's new text, original text, event, and jquery element for the td cell
 */
document.write("<link href='" + contextPath
		+ "/plugins/datepicker/datepicker3.css' rel='stylesheet'/>");
document.write("<script src='" + contextPath
		+ "/plugins/datepicker/bootstrap-datepicker.js'></script>");
document.write("<link href='" + contextPath
		+ "/plugins/clockpicker/clockpicker.css' rel='stylesheet'/>");
document.write("<script src='" + contextPath
		+ "/plugins/clockpicker/babyClockpicker.js'></script>");
document
		.write("<script src='"
				+ contextPath
				+ "/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js'></script>");
jQuery.uiTableEdit = function(jq, options) {
	function unbind() {
		return jq.find(options.find).unbind('mousedown.uiTableEdit')
	}
	options = options || {}
	options.find = options.find || 'tbody > tr > td'
	if (options.off) {
		unbind().find('form').each(function() {
			var f = $(this);
			f.parents("td:first").text(f.find(':text').attr('value'));
			f.remove();
		});
		return jq;
	}

	function bind_mouse_down(mouseDn) {
		unbind().bind('mousedown.uiTableEdit', mouseDn)
	}
	function td_edit() {
		var td = jQuery(this);
		if (td.attr("class") != undefined && td.attr("class") != null
				&& td.attr("class").trim() == 'noEdit') {
			return false;
		}
		function restore(e) {
			var val = td.find(':text').val();
			if (options.dataVerify) {
				var value = options.dataVerify
						.call(this, val, orig_text, e, td);
				if (value === false) {
					return false;
				}
				if (value !== null && value !== undefined)
					val = value;
			}
			td.html("");
			td.text(val);
			if (options.editDone)
				options.editDone(val, orig_text.trim(), e, td)
			bind_mouse_down(td_edit_wrapper);
		}

		function checkEscape(e) {
			if (e.keyCode === 27) {
				td.html("");
				td.text(orig_text);
				bind_mouse_down(td_edit_wrapper);
			}
		}

		var orig_text = td.text();
		var w = td.width();
		var h = td.height();
		var editValue = td.text().trim();
		td.css({
			width : w + "px",
			height : h + "px",
			padding : "0",
			margin : "0"
		});
		if (options.preEdit&&!options.preEdit(jQuery(this)))
			return false;
		if (td.attr("class") != undefined && td.attr("class") != null
				&& td.attr("class").trim() == 'dataEdit') {
			td
					.html(
							'<form name="td-editor" action="javascript:void(0);">'
									+ '<div class="input-group date">'
									+ '<input type="text" value="'
									+ editValue
									+ '" formatflag="date" class="datepicker" style="margin:0px;padding:0px;border:0px;width:'
									+ w
									+ 'px;" readonly="true">'
									+ '<span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>'
									+ '</div>' + '</form>').find('form')
					.submit(restore).keypress(checkEscape);
			$('.input-group.date').datepicker({
				format : "yyyy-mm-dd",
				calendarWeeks : false,
				autoclose : true,
				language : "zh-CN",
				clearBtn : true,
				todayHighlight : true
			});
		} else if (td.attr("class") != undefined && td.attr("class") != null
				&& td.attr("class").trim() == 'timeEdit') {
			td
					.html(
							'<form name="td-editor" action="javascript:void(0);">'
							+ '<div class="input-group clockpicker">'
							+ '<input type="text" value="'
							+ editValue
							+ '" style="margin:0px;padding:0px;border:0px;width:'
							+ w
							+ 'px;" readonly="true">'
							+ '<span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>'
							+ '</div>' + '</form>').find('form').submit(restore)
					.keypress(checkEscape);
			$('.clockpicker').clockpicker({
				placement: 'bottom',
			    align: 'left',
			    autoclose: true,
			    minuteDisplay:'quarter',
			    minuteSelect :'quarter'
			});
		} else {
			td
					.html(
							'<form name="td-editor" action="javascript:void(0);">'
									+ '<input type="text" name="td_edit" value="'
									+ editValue
									+ '"'
									+ ' onfocus="this.select();" style="margin:0px;padding:0px;border:0px;width: '
									+ w + 'px;">' + '</input></form>').find(
							'form').submit(restore).keypress(checkEscape);
		}
		function focus_text() {
			td.find('input:text').get(0).focus()
		}
		//
		// // focus bug (seen in FireFox) fixed by small delay
		setTimeout(focus_text, 50);

		/*
		 * TODO: investigate removing bind_mouse_down I also got rid of
		 * bind_mouse_down(restore), because now that you can refocus on fields
		 * that have been blurred, you can have multiple edits going
		 * simultaneously
		 */
		bind_mouse_down(restore);
	}

	var td_edit_wrapper = !options.mouseDown ? td_edit : function() {
		if (options.mouseDown.apply(this, arguments) == false)
			return false;
		td_edit.apply(this, arguments);
	};
	bind_mouse_down(td_edit_wrapper);
	return jq;
}
