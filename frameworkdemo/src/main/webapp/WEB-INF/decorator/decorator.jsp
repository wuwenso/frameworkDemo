<%@page contentType="text/html;charset=utf-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html dir="ltr" lang="zh-CN">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta name="viewport"
		content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<title> <sitemesh:write property='title' /></title>
	<%@include file="/WEB-INF/jsp/common/resource.jsp"%>
	<sitemesh:write property='head' />
</head>
<body class="bodygrey <sitemesh:write property='body.class'/>"
	style="<sitemesh:write property='body.style'/>">
	<jsp:include page="/WEB-INF/jsp/common/head.jsp" />
	<jsp:include page="/WEB-INF/jsp/common/left.jsp" />	
	<sitemesh:write property='body' />
	<jsp:include page="/WEB-INF/jsp/common/foot.jsp" />
</body>
</html>
