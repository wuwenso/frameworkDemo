$.fn.extend({
	showInfo:function(msg){
		msg = msg || "正在处理，请耐心等待，请勿关闭页面！";
		var $lad=$("<div class='smtloading' ><img src='/common-static/img/loadingbar.gif' width='100px'/><p style='text-align:left;'>" + msg + " </p></div>");
		$(this).before($lad);
		this.domAll = Array.prototype.slice.call(arguments,1);
		for(var i in this.domAll){
			$(this.domAll[i]).hide();
		}
	},
	closeInfo:function(){
		$(this).prev().empty().remove();
		for(var i in this.domAll){
				$(this.domAll[i]).show();
		}
	}
});
(function($){
	$.getFormValue = function(el) {
		var $inputs = el.find(':input');
		var values = {};
		$inputs.each(function() {
			var input = $(this);
			var name = this.name;
			var value;
			if (input.is(':radio')) {
				value = el.find('input[name="' + name + '"]:checked').val();
			} else if (input.is(':checkbox')) {
				value = el.find('input[name="' + name + '"]:checked').val();
			} else {
				value = input.val();
				input.val($.trim(value));
			}
			if (name != '' && value != '' && value != null) {
				values[name] = $.trim(value);
			}
		});
		return values;
	};
	$.getRedirectParam=function(redirectParam){
		var param=eval(redirectParam);
		var redirectPam="";
		if(0!=param.length){
			redirectPam=redirectPam+"?";
			for(var i=0;i<param.length;i++){
				var pam=param[i];
				redirectPam=redirectPam+pam.name+"="+pam.value;
				if(i!=param.length-1){
					redirectPam=redirectPam+"&";
				}
			}
		}
		return redirectPam;
	};
	
})(jQuery);

(function(window,$){
	window.menu=function(menu){
		$("menu").removeClass("current");
		$("[menu="+menu+"]").addClass('current').parents('.content').prev().trigger('click');
	};
})(window,jQuery);
function toDelete(url, table) {
	var param = '';
	var ids = table.getSelect();
	for ( var i in ids) {
		param += "ids=" + ids[i] + "&";
	}
	if (param.length > 0){
		param = param.substring(0, param.length - 1);
	}else {
		$.messager.alert('友情提示', '您没有选择任何需要删除的信息！');
		return;
	}
	;
	$.messager.confirm('友情提示', '删除后不能恢复，确定要删除选中记录吗？', null, function(flag) {
		if (flag) {
			$.ajax({
				url : url,
				success : function(data) {
					with (data) {
						if (status == 1) {
							table.load();
							$.messager.alert("友情提示", message);
						} else {
							$.messager.alert("友情提示", message);
						}
					}
				},
				dataType : "json",
				type : "post",
				data : param
			});
		}
	});
}
var formatMoney = function(money,patter) {
	money = parseFloat(money);
	if(null==patter||undefined==patter||""==patter){
		patter="#,##0.##";
	}
	return formatNumber(money,patter);
};
function formatNumber(num, pattern) {
	var bl=false;
	if(num<0){
		bl=true;
	}
	num=Math.abs(num);
	var strarr = num ? num.toString().split('.') : [ '0' ];
	var fmtarr = pattern ? pattern.split('.') : [ '' ];
	var retstr = '';
	// 整数部分   
	var str = strarr[0];
	var fmt = fmtarr[0];
	var i = str.length - 1;
	var comma = false;
	for (var f = fmt.length - 1; f >= 0; f--) {
		switch (fmt.substr(f, 1)) {
		case '#':
			if (i >= 0)
				retstr = str.substr(i--, 1) + retstr;
			break;
		case '0':
			if (i >= 0)
				retstr = str.substr(i--, 1) + retstr;
			else
				retstr = '0' + retstr;
			break;
		case ',':
			comma = true;
			retstr = ',' + retstr;
			break;
		}
	}
	if (i >= 0) {
		if (comma) {
			var l = str.length;
			for (; i >= 0; i--) {
				retstr = str.substr(i, 1) + retstr;
				if (i > 0 && ((l - i) % 3) == 0)
					retstr = ',' + retstr;
			}
		} else
			retstr = str.substr(0, i + 1) + retstr;
	}
	retstr = retstr + '.';
	// 处理小数部分   
	str = strarr.length > 1 ? strarr[1] : '';
	fmt = fmtarr.length > 1 ? fmtarr[1] : '';
	i = 0;
	for (var f = 0; f < fmt.length; f++) {
		switch (fmt.substr(f, 1)) {
		case '#':
			if (i < str.length)
				retstr += str.substr(i++, 1);
			break;
		case '0':
			if (i < str.length)
				retstr += str.substr(i++, 1);
			else
				retstr += '0';
			break;
		}
	}
	var str=retstr.replace(/^,+/, '').replace(/\.$/, '');
	if(bl){
		str="-"+str;
	}
	return str;
}
(function($){
	var Location=function(){
		this.specialName='params';
	};
	Location.prototype={
		 _getUrlOriginalParam:function(name){
				var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		    var r = window.location.search.substr(1).match(reg);
		    if($.isArray(r)){
			    for(var i=0;i<r.length;i++){
			    	r[i]=decodeURI(r[i]);
			    }
		    }
		    return r;
		},
		getUrlParam:function(name){
			try {
				return this._getUrlOriginalParam(name)[2];
			} catch (e) {
				return null;
			}
		},
		getSpecialParam:function(){
			try {
				return this._getUrlOriginalParam(this.specialName)[0];
			} catch (e) {
				return null;
			}
		},
		getSpecialData:function(){
			try {
				return eval("("+this.getUrlParam(this.specialName)+")")||{};
			} catch (e) {
				return null;
			}
		}
	};
	$.location=new Location();
})(jQuery);

