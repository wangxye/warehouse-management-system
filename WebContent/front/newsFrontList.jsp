<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
	<%  
   //页面每隔1秒自动刷新一遍       
   response.setHeader("refresh" , "1" );  
   %>  
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title>之江物联网中心</title>
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
		<img src="img/3.jpg" alt="">
	</div>
	<!--//banner-->
	<!--新闻-->
	<div class="container">
		<div class="left">
			<div class="menu_plan">
				<div class="menu_title">
					货物进出监控<br> <span>news of company</span>
				</div>
				<ul id="tab">
					<li><a href="#">货物进出监控</a></li>
				</ul>
			</div>
		</div>
		<div class="right">
			<div class="location">
				<span>当前位置：<a href="javascript:void(0)" id="a"><a
						href="#">货物进出监控</a></a></span>
				
			</div>
			<div style="font-size: 14px; margin-top: 53px; line-height: 36px;">
				<div id="tab_con">
					<div id="tab_con_2" class="dis-n" style="display: none;">
						<table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example1" style="table-layout:fixed;">
                                            <thead>
                                            <tr>
                                                <th>携带人</th>
                                                <th>携带货物</th>
                                                <th>时间</th>
                                                <th>进/出库</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                           	<c:forEach var="scan" items="${scans}">
   			                                      <tr>
   				                                      <td>${scan.username }</td>
	   			                                      <td>${scan.name }</td>	   	
	   			                                      <td>${scan.createDate }</td>	
	   		                                       <c:if test="${scan.status ==0}">
                                                      <td>出库</td>
                                                   </c:if>
                                                   <c:if test="${scan.status ==1}">
                                                      <td>进库</td>
                                                   </c:if>				
   			                                      </tr>
   		                                    </c:forEach>
                                            </tbody>
                                        </table>
					</div>

				</div>
			</div>
		</div>
	</div>
	<!--//新闻-->
	<!--底部-->
	<%@ include file="common_footer.jsp"%>

</body>

<!--//底部-->
<script>
tabs("#tab", "active", "#tab_con");
</script>



</script>

</html>
