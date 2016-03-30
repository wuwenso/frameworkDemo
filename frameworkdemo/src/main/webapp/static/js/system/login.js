$().ready(function() {
	
	$('#submitBtn').click(function() {
		var pwd=$.trim($('#password').val());
		
		if("2"==$("#password").attr("flag")){
			pwd=hex_md5(pwd);
		}else{
			pwd=$("#password").attr("pwd");
		}
		$.ajax({
			url: '/login.ajax',
			dataType: 'json',
			data: {
				'account': $.trim($('#account').val()),
				'password':pwd,
				'code': $.trim($('#code').val()),
				'remberPass':$("#CheckBox1").is(':checked')?1:2
			},
			type:"POST",
			success: function(result) {
				var message = result.message;
				if (null!=message&&message != '') {
					refreshImg();
					$('#errorMsg').text(message).show();
				} else {
					if(url){
						location.href =url;
					}else{
						location.href="/";
					}
				}
			}
		});
	});
	$('body').keypress(function (e) {
		var key = e.which;
		if(key == 13) {
			$('#submitBtn').trigger('click');
		}
	});
	$("#account").on("keydown",function(){
				$("#password").val("");
	});
	$("#password").on("keyup",function(){
		$(this).attr("flag",2);
	});
	
});

var refreshImg = function() {
	$('#codeImage.json').attr('src', '/codeImage?' + new Date().getTime());
}
