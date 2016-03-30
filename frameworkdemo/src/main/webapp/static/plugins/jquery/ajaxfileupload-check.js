(function($) {
	$.fn.uploadValidate =function(options){
		var defaults= {
				filter : [],
				maxSize : 2 * 1024,
				minSize : 0,
				errorFormat : function() {
					$.messager.alert("友情提示","不接受此文件类型！");
				},
				sizeMismatch : function(size,maxSize,minSize) {
					if(size>maxSize){
						$.messager.alert("友情提示","附件大小不能大于"+maxSize+"M！");
					}else{
						$.messager.alert("友情提示","不能上传空文件！");
					}
				},
				fileNotFound : function() {
					$.messager.alert("友情提示","附件不存在，请重新选择！");
				}
			};
		
		
		var target=this[0];
		var def = defaults;
        var ops = $.extend(true, {}, def, options);
		
		if (!target)
			return false;

		var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
		var fileSize = 0;
		var filetypes = ops.filter;
		var filepath = target.value;
		var filemaxsize = ops.maxSize;// 文件上传最大值
		if (filepath) {
			var isnext = false;
			var fileend = filepath.substring(filepath.indexOf(".")+1);
			if (filetypes && filetypes.length > 0) {
				for (var i = 0; i < filetypes.length; i++) {
					if (filetypes[i] == fileend) {
						isnext = true;
						break;
					}
				}
			}
			if (!isnext) {
				var errorFormat = ops.errorFormat;
				if ($.isFunction(errorFormat)) {
					errorFormat();
				}
				return false;
			}
		} else {
			return false;
		}
		if (isIE && !target.files) {
			return true;
		} else {
			fileSize = target.files[0].size;
		}

		var size = fileSize / 1024;
		if (size > filemaxsize || size <= 0) {
			var sizeMismatch = ops.sizeMismatch;
			if ($.isFunction(sizeMismatch)) {
				sizeMismatch(size,ops.maxSize,ops.minSize);
			}
			return false;
		}
		return true;
	}
})(jQuery);
