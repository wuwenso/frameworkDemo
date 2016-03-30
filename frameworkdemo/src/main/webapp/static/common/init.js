/**
 * User: zhaotianwu DateTime: 2014/9/9 10:08 Copyright © 2014/9/9 Shanghai
 * Raxtone Software Co. Ltd Allright Reserved
 */
;
$(function() {
	$('input, textarea').placeholder();
	// ajax 拦截
	$(document).ajaxError(function(event, jqxhr, settings, thrownError) {
		switch (parseInt(jqxhr.status)) {
		case (500):
//			alert("服务器系统内部错误");
			break;
		case (401):
//			alert("未登录");
			break;
		case (403):
//			alert("无权限执行此操作");
			break;
		case (408):
//			alert("请求超时");
			break;
		case (0):
			//alert("网络错误!");
			break;
		default:
			//alert("未知错误");
		}
	});
	$(document).ajaxSuccess(function(event, xhr, settings){
		if(settings.url.indexOf('/js/jquery.uploadify-3.1.js')<0){
			var json =xhr.responseText;
			if(json){
				 json=$.parseJSON(json);
				 if(null!=json&&json.hasOwnProperty("errCode")){
					if (json.errCode== "0") {
						//alert(json.errMsg);
						location.href="/login";
					}else if(json.errCode== "-1"){
						location.href=json.skip;
					}else if(1==json.errCode){
						console.log(json.msg);
					}
				 }
			}
		}
	});
	/**
	 * 模版方法，将字符串中预留空位以给定值填充。 "a{0}c".format("b") = abc;
	 * "a{pa}c{pb}".format({pa:"PA", pb:"PB"}) = aPAcPB;
	 * "a{0}c{1}d{0}e{2}f".format(["A", "B", "C"]); = aAcBdAeCf;
	 */
	String.prototype.format = function(value) {
		var result = this;
		if (this.indexOf("{") == -1 || this.indexOf("}") == -1) {
			return String(this); // Convert the type to "String", or it cause
									// a
			// typical bug in Firefox, type is "Object" not
			// "String".
		}
		if (typeof value == "undefined" || value == null || value == "null") {
			value = "";
		}
		var type = typeof value;
		if ("string" === type || "number" === type || "boolean" == type) {
			value = String(value);
			result = this.replace(/\{0\}/g, value);
		} else if (value instanceof Array) {
			jQuery.each(value, function(i, v) {
				var regText = "\\{" + String(i) + "\\}";
				var regex = new RegExp(regText, "g");
				if (typeof v == "undefined" || v == null || v == "null") {
					v = "";
				}
				result = result.replace(regex, v);
			});
		} else {
			for ( var key in value) {
				var v = value[key];
				if (typeof v == "undefined" || v == null || v == "null") {
					v = "";
				}
				if ("string" === (typeof v) || "number" === (typeof v)
						|| "boolean" === (typeof v)) {
					var regText = "\\{" + String(key) + "\\}";
					var regex = new RegExp(regText, "g");
					result = result.replace(regex, v);
				}
			}
		}
		return result;
	};
	$.format = function(source, params) {
		if (arguments.length == 1)
			return function() {
				var args = $.makeArray(arguments);
				args.unshift(source);
				return $.format.apply(this, args);
			};
		if (arguments.length > 2 && params.constructor != Array) {
			params = $.makeArray(arguments).slice(1);
		}
		if (params.constructor != Array) {
			params = [ params ];
		}
		$.each(params, function(i, n) {
			source = source.replace(new RegExp("\\{" + i + "\\}", "g"), n);
		});
		return source;
	};
	$.getCookie=function(name){ 
	    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	 
	    if(arr=document.cookie.match(reg))
	 
	        return unescape(arr[2]); 
	    else 
	        return null; 
	};
	/**
	 * 判断图片是否加载完成 执行特定的操作
	 * img 图片
	 * callback 图片加载完成 执行的回调函数
	 */
	$.imgLoadEvent= function(img,callback){
		var time=setInterval(function(){
			 if(/msie/.test(navigator.userAgent.toLowerCase())){
		        // IE
		        if(img[0].readyState == 'complete'){
		        	clearInterval(time);
		        	if($.isFunction(callback))
		        		callback();
		        }
			 }else{
		        // 非IE
		        if(img[0].complete == true){
		        	clearInterval(time);
		        	if($.isFunction(callback))
		        		callback();
		        }
			 }
		},100);
	};
	
	
	String.prototype.escape=function () {
		    return this.replace(/&/g, '&amp;')
	        .replace(/"/g, '&quot;')
	        .replace(/</g, '&lt;')
	        .replace(/>/g, '&gt;');
	} 
	$.escape =function(value){
    	if(typeof value=="string"){
    		return value.escape();
    	}else if($.isArray(value)||$.isPlainObject(value)){
    		for(var i in value){
    			var val=value[i];
    			value[i]=$.escape(val);//递归
    		}
    		return value;
    	}else{
    		return value;
    	}
    }
});
