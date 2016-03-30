<%@page contentType="text/html;charset=utf-8" language="java"%>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>车辆管理- 查删</title>
		<link rel="stylesheet" href="/static/css/page.css" />
		<script type="text/javascript" src="/static/plugins/jquery/jquery.pagination.js"></script>
		<script type="text/javascript" src="/static/js/car/carlist.js"></script>
	</head>
	<body >
		<div class="maincontent">
			<div class="two_third maincontent_inner ">
				<a class="btnfold" id="FoldlColumn"> </a>
				<div class="breadcrumbs">
					<a>司机车辆管理 </a><span>车辆管理</span>
				</div>
				<!-- breadcrumbs -->
				<div class="left">
					<!-- START WIDGET LIST -->
					<div class="content">
						<div class="form_default widgetbox">
							<div class="titlebg clearfix" id="dataDiv">
								<div class="filterwrap">
									<div class="one_third">
										<label class="formtit">车型</label>
										<select id="carType" name="carType">
											<option value="">全部</option>
										</select>
									</div>
									<div class="one_third">
										<label class="formtit">车牌号 </label>
										<input type="text" id="licenseNum" name="licenseNum"/>
									</div><br clear="all"/>
									<div class="one_third">
										<label class="formtit">状态</label>
										<select id="status" name="status">
											<option value="">全部</option>
											<option value="1">有效</option>
											<option value="2">无效</option>
										</select>
									</div>
									<div class="one_third">
										<label class="formtit">车具号 </label>
										<input type="text" id="carNum" name="carNum" />
									</div>
									<div class="one_third">
										<label class="formtit">&nbsp;</label>
										<a class="btn-green search btn" id="searchBtn">查询 </a>
										<a class="btn-red search btn" id="importBtn">导入</a>
									</div>
								</div>
							</div>
							<div class="content nopadding ohidden page_relative" >
								<div id="carList"></div>
								<div class="stabbtns fl">
									<a href="/pf/car/toSaveOrUpdate" class="btn btn-green fl btnadd" title="添加" id="toSaveBtn">添 加</a>
									 <a class="btn btndelete btn-red fl" id="delBtn" title="删除">删 除 </a>
								</div>
							</div>
						</div>
					</div>
					<!-- content -->
				</div>
				<!-- left -->
			</div>
			<!-- two_third -->
			<br clear="all"/>
		</div>
	</body>
</html>