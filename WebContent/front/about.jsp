<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title>仓库管理界面</title>
<link href="css/main.css" rel="stylesheet" type="text/css">
<link href="css/container.css" rel="stylesheet" type="text/css">
<link href="css/reset.css" rel="stylesheet" type="text/css">
<link href="css/screen.css" rel="stylesheet" type="text/css">
<script src="js/jquery.min.js">
</script>
<script src="js/tab.js">
</script>
</head>

<body>

	<%@ include file="common_header.jsp"%>

	<!--banner-->
	<div class="second_banner">
		<img src="img/1.gif" alt="">
	</div>
	<!--//banner-->
	<!--企业简介-->
	<div class="container">

		<div class="left">
			<div class="menu">
				<div class="menu_title">
					团队简介 <br> <span>Company profiles</span>
				</div>
				<ul id="tab">
					<li class="active" onclick="changeValue(this)"><a
						href="javascript:void(0)">团队概况</a></li>
					</li>

				</ul>
			</div>
		</div>

		<div class="right">
			<div class="location">
				<span>当前位置：<a href="#">团队简介</a>
				</span>
				<div class="brief" id="b">团队概况</div>
			</div>
			<div style="font-size: 14px; margin-top: 53px; line-height: 36px;">
				<div id="tab_con">
					<div id="tab_con_1" class="active">
						<br>
						<p>团队全称</p>
						<br>
						<p>浙江工业大学之江学院物联网中心</p>
						<br>
						<p>创立时间</p>
						<br>
						<p>之江物联网中心创建成立于 2017 年 12 月</p>
						<p>
							之江物联网中心将永葆创新激情，不断追求卓越，全力打造大学生项目团队，用实际行动迎接中国数字化产业的腾飞。
						</p>




					</div>
					<div id="tab_con_2" class="dis-n" style="text-align: center;">
						<img src="img/architecture.png" alt="" style="margin: 2% 0;">
					</div>

				</div>
			</div>
		</div>
	</div>
	<!--//团队简介-->
	<!--底部-->
	<div class="bottom">
		<div class="footer">
			<div class="gulid">
				<p>Copyright 2018 之江物联网中心 All Rights.</p>
				<p>
					之江物联网中心  技术支持
				</p>
				<p>之江物联网中心开发并提供本系统作为仓库管理系统的操作界面</p>
			</div>
		</div>
	</div>

</body>



<!--//底部-->
<script>
tabs("#tab", "active", "#tab_con");
</script>

</script>

</html>
