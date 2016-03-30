<%@page contentType="text/html;charset=utf-8" language="java"%>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
		<title>汽车城管理系统</title>
		<jsp:include page="/WEB-INF/jsp/common/resource.jsp" />
		<script type="text/javascript" src="/static/plugins/jquery/radio-checkbox.js"></script>
		<script type="text/javascript" src="/static/plugins/md5.js"></script>
		<script>
			var url='${redirectUrl}';
		</script>
		<script type="text/javascript" src="/static/js/system/login.js"></script>
	</head>
	<body class="gradient">
		<!--<div id="hd">
			<div id="hdWrp">
				<div class="topbanner">
					<div class="loginhd"><a class="fcarlogo" href="#"> </a> </div>
				</div>  
			</div>
		</div> hd -->
		<div class="loginbg">
			<div class="loginmain">
				<div class="loginbox">
					<div class="logintitbg" style="color:#22C483;border-bottom:1px solid #e5e5e5;">汽车城管理系统 </div>
					<div class="loginform"><input style="font-weight:normal;color:#666;" id="account" placeholder="请输入用户名" value="${account }" type="text" class="logininput acntipt"/></div>
					<div class="loginform"><input style="font-weight:normal;color:#666;" id="password" placeholder="请输入密码"  <c:if test="${!empty password }">pwd="${password}" value="        " flag="1"</c:if> type="password" <c:if test="${!empty password }"></c:if><c:if test="${empty password }">flag="2"</c:if> class="logininput pswipt"/></div>
					<div class="loginform">
					<div class="fl">
						<input id="code" type="text" class="logininput" placeholder="请输入验证码" maxlength="6" style="width:200px; color:#32ca2e; font-size:16px;color:#666;"/>
					</div>
						<img width="90" height="44" src="/codeImage" id="codeImage" style="float:right;margin-top:3px;" /> 
					</div>
					<div class="loginform checkboxwrap">
						<p><input style="display: none;" id="CheckBox1"  name="clb1" <c:if test="${!empty account}">checked="checked"</c:if> type="checkbox"/><label class=""> 记住密码 </label></p> 
					</div>
					<div class="popbox">
						<div class="notification msgerror" id="errorMsg" style="display:none;"></div> 
					</div>
					<div class="loginform"> <a class="loginbtn" id="submitBtn"> 登 录 </a></div>
				</div>
			</div>
		</div>
		<div id="loginft">
			<div id="ftWrp">
				<div class="copyright">Copyright&nbsp;©&nbsp;2015&nbsp;飞路信息/沪ICP备14025343号 </div>
			</div>   <!--End ftWrp -->
		</div>	 <!--End ft -->
</body>
</html>