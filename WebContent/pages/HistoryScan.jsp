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
   //页面每隔6分钟自动刷新一遍       
   response.setHeader("refresh" , "3600" );  
   %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>历史货物进出记录</title>

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
                            <h1 class="page-header"> 货物历史进出记录</h1>
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
                                                <th>携带人</th>
                                                <th>携带货物</th>
                                                <th>时间</th>
                                                <th>进/出库</th>
                                                <th>货物定位</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                           	<c:forEach var="scan" items="${scans}">
   			                                      <tr>
   				                                      <td>${scan.peoplename }</td>
	   			                                      <td>${scan.goodname }</td>	   	
	   			                                      <td>${scan.createDate }</td>	
	   		                                       <c:if test="${scan.statu ==0}">
                                                      <td>出库</td>
                                                   </c:if>
                                                   <c:if test="${scan.statu ==1}">
                                                      <td>进库</td>
                                                   </c:if>	
                                                       <td>
	   				                                     <a href="${pageContext.request.contextPath}/pages/result.jsp">详情</a>		                                   
	   			                                       </td>			
   			                                      </tr>
   		                                    </c:forEach>
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
 <script>
 $(document).ready(function() {
     $('#dataTables-example1').DataTable({
         responsive: true,
         "aoColumnDefs": [ { "bSortable": false, "aTargets": [ 0,1,2,3,4 ] }]
     });
 });
 </script>

</body>
</html>