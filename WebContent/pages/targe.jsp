<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<%  
   //页面每隔5秒自动刷新一遍       
   response.setHeader("refresh" , "5" );  
   %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>货物进出记录</title>

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
</head>
<body>
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <h1 class="page-header">扫描信息</h1>
                        </div>
                        <!-- /.col-lg-12 -->
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                                    <a href="${pageContext.request.contextPath }/Scan/findAll">查找历史纪录</a>                                    
                                    </div>
                                    <!-- /.panel-heading -->
                                    <div class="panel-body ">
                                        <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example1" style="table-layout:fixed;">
                                            <thead>
                                             <tr>
                                                <th>标签</th>
                                                <th>定位</th>  
                                             <tr>
                                            </thead>
                                            <tbody>
   			                                      <tr>
   				                                      <td>A2 00 41 45 31 07 01 54 15 40 75 00 </td>	   			                                     
                                                      <td>
	   				                                     <a href="${pageContext.request.contextPath}/Scan/getnowtime/A2 00 41 45 31 07 01 54 15 40 75 00 "   target="_blank">详情</a>		                                   
	   			                                       </td>			
   			                                      </tr>
                                            </tbody>
                                        </table>
                                    </div>

                                </div>
                                <!-- /.container-fluid -->
                            </div>
                            <!-- /#page-wrapper -->

                        </div>
                    </div>
                </div>
          
  

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

</body>
</html>