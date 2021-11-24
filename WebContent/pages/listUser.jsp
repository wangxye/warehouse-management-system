<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查找人员</title>

    </script>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

   
    <!-- Bootstrap Core CSS -->
    <link href="<%=basePath%>vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="<%=basePath%>vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<%=basePath%>dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="<%=basePath%>vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <style type="text/css">
        td{
            width:100%;
            word-break:keep-all;/* 不换行 */
            white-space:nowrap;/* 不换行 */
            overflow:hidden;/* 内容超出宽度时隐藏超出部分的内容 */
            text-overflow:ellipsis;/* 当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用。*/


        }
    </style>
</head>

<body>

<div id="wrapper">

       <!-- Navigation 导航栏 -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
               
                <a class="navbar-brand" href="#">仓库管理</a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
 
                 <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i>${user.name } <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                       <li><a href="${pageContext.request.contextPath }/User/find/${user.id}"><i class="fa fa-user fa-fw"></i> User Profile</a>
                        </li>
                        <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="${pageContext.request.contextPath }/pages/login.jsp"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                       <li>
                            <a href="#"><i class="fa fa-user fa-fw"></i>人员管理<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="${pageContext.request.contextPath }/User/findAllUser">人员查询</a>
                                </li>
                                <li>
                                    <a href="#">管理权限</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>

                        <li>
                            <a href="#"><i class="fa fa-wrench fa-fw"></i> 分类管理<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="${pageContext.request.contextPath }/pages/addcategory.jsp" >添加分类</a>
                                </li>
                                <li>
                                    <a href="${pageContext.request.contextPath }/Category/getAll.action" >查看分类</a>
                                </li>

                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-sitemap fa-fw"></i> 货物管理<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                   <a href="${pageContext.request.contextPath }/Good/ToaddGood" >添加货物</a>
                                </li>
                                <li>
                                   <a href="${pageContext.request.contextPath }/Good/listgood">查找货物</a>
                                </li>
                                <li>
                                  <a href="${pageContext.request.contextPath }/Scan/find.action"  target="_blank">货物进出记录</a>
                                </li>
                                <li>
                                   <a href="${pageContext.request.contextPath }/pages/targe.jsp"  target="_blank">货物标签定位</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>


    <!-- Page Content -->
    <div id="page-wrapper">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">人员查询</h1>
                </div>
                <!-- /.col-lg-12 -->
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                           
                            <!-- /.panel-heading -->
                            <div class="panel-body ">
                                <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example1" style="table-layout:fixed;">
                                    <thead>
                                    <tr>
                                        <th>人员名字</th>
                                        <th>人员标签id</th>
                                        <th>手机号</th>
                                        <th>邮箱地址</th>
                                        <th>地址</th>                                                                         
                                    </tr>
                                    </thead>
                                    <tbody>                                                                    
                                    <c:forEach var="User" items="${Users}">
   			                           <tr>
   				                        <td>${User.name }</td>
   				                        <td>${User.iid}</td> 
   				                        <td>${User.phone}</td>
   				                        <td>${User.email}</td>
   				                        <td>${User.address}</td>  				                     				  				                   
   			                            </tr>
   		                            </c:forEach>   		       			                               
                                    </tbody>
                                </table>
                                <!-- /.table-responsive -->
                               
                            </div>
                            <!-- /.panel-body -->
                        </div>
                        <!-- /.panel -->
                    </div>
                    <!-- /.col-lg-12 -->
                </div>

                        <!-- /.panel -->
            </div>
            </div>
            <!-- /.row -->
        </div>
        <!-- /.container-fluid -->

    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<!-- jQuery -->
<script src="<%=basePath%>vendor/jquery/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="<%=basePath%>vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="<%=basePath%>vendor/metisMenu/metisMenu.min.js"></script>

    <!-- DataTables JavaScript -->
    <script src="<%=basePath%>vendor/datatables/js/jquery.dataTables.min.js"></script>
    <script src="<%=basePath%>vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
    <script src="<%=basePath%>vendor/datatables-responsive/dataTables.responsive.js"></script>

    <!-- Custom Theme JavaScript -->
<script src="<%=basePath%>dist/js/sb-admin-2.js"></script>

    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
        $(document).ready(function() {
            $('#dataTables-example1').DataTable({
                responsive: true,
                "aoColumnDefs": [ { "bSortable": false, "aTargets": [ 0,4,5,6 ] }]
            });
        });
     /*   $(function() {
            $('table').find('td').mouseover(function() {
                var cont = $(this).text(); // 获取到内容
            });
        });
        $(document).on('click','.layui-table-cell',function(){
            // $("p").css({"background-color":"blue","font-size":"14px"});
            var x=$(this).offset(),
                    left=x.left,
                    top=x.top;
            var w=$(this).width(),
                    h=$(this).height();
            $('.hoverDiv').css({
                "width":w,
                "left":left+"px",
                "top":top+h+'px',
                "display":'block'
            }).slideDown();
        })

        $("#dataTables-example1 td").mouseover(function(){
            $(this).find("div").show();
        });*/


    </script>
</body>

</html>
