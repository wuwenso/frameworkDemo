$.fn.placeholder=function(){
	//判断是否支持html5
	if(!window.applicationCache){
		this.filter(":text[placeholder],:password[placeholder],textarea[placeholder]").each(function(){
			var parent=$("<div/>",{"style":"position: relative;"});
			var label=$("<label/>",{"style":"cursor: text;display:block; position: absolute;left:0px;z-index: 10;"});
			var _me=$(this);
			_me.after(parent);
			label.height(_me.outerHeight(true));
			label.css({"line-height":_me.outerHeight(true)+"px","padding-left":_me.css("padding-left"),"font-size":_me.css('font-size'),"color":"#A9A9A9","top":_me.css("border-top-width")});
			label.text(_me.attr("placeholder"));
			_me.css({"position":"relative"});
			parent.append(label);
			_me.insertAfter(label);
			
			label.click(function(){
				_me.focus();
			});
			var change=function(){
				if(!_me.val()||_me.val()==""){
					label.show();
				}else{
					label.hide();
				}
			};
			_me.bind('keydown keypress keyup',change);
			change();
		});
	}
}