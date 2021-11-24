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
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>人物信息</title>

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
    <script >
function check(form){ 
    if(form.iid.value == ""){
        alert("人员标签id不能为空");
        form.iid.focus();
        return false;
    }
    if(form.username.value == ""){
        alert("姓名不能为空");
        form.iid.focus();
        return false;
    }
        if(form.password.value == ""){
            alert("未输入密码 \n" + "请输入密码");
            form.password.focus();
            return false;
        }
        if(form.password.value.length < 6){
            alert("密码必须多于或等于 6个字符。\n");
            return false;
        }    
           if(form.email.value !="") {
            if (form.email.value.indexOf("@",0)==-1) {
                alert("邮箱格式不正确\n必须包含@符号");
                return false;
            }
            if (form.email.value.indexOf(".",0)==-1) {
                alert("邮箱格式不正确\n必须包含.符号");
                return false;
            }
           }  
           var b= window.confirm("确认修改吗？");
     	  if(!b){
     		 return false;
     	  }
        return true;
}
</script>
</head>

<body>

<div id="wrapper" >

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
                    <h1 class="page-header">人员信息</h1>
                </div>
                <!-- /.col-lg-12 -->
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                                                              人员基本信息
                            </div>
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-lg-6">
                                        <form role="form" action="${pageContext.request.contextPath}/User/update"    onSubmit="return check(this)" method="post" >
                                            <input type="text" name="id" value="${user.id}"   style="display:none">  
                                            <div class="form-group"  >
                                                <label>用户名称：</label>
                                                <input type ="text" name="name" class="form-control"  value="${user.name}">
                                                
                                            </div>
                                            <div class="form-group ">
                                                <label>用户标签：</label>
                                                <input type="text"  name="iid" class="form-control"   readonly   value="${user.iid}">
                                                <p class="help-block">标签id不可改</p>
                                            </div>
                                            <div class="form-group">
                                                <label>账号：</label>
                                                <input type="text"  name="username" class="form-control"  value="${user.username}">
                                            </div>
                                            <div class="form-group">
                                                <label>密码：</label>
                                                <input type="text" name="password" class="form-control"  value="${user.password}">
                                            </div>
                                            <div class="form-group">
                                                <label>电话：</label>
                                                 <input type="text" name="phone" class="form-control"  value="${user.phone}">
                                            </div>  
                                            <div class="form-group">
                                                <label>邮箱：</label>
                                                <input type="text" name="email" class="form-control"  value="${user.email}">
                                            </div>   
                                             <div class="form-group">
                                                <label>地址：</label>
                                                <textarea class="form-control" rows="3"   name="address">${user.address}</textarea>
                                            </div>                                                                                                                                                                 
                                            <input type="submit" class="btn btn-default" value="更新资料">                                          
                                        </form>
                                    </div>
            </div>
            <!-- /.row -->
        </div>
        <!-- /.container-fluid -->
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<!-- jQuery -->
<script src="<%=basePath%>vendor/jquery/jquery.min.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="<%=basePath%>vendor/bootstrap/js/bootstrap.min.js"></script>

<!-- Metis Menu Plugin JavaScript -->
<script src="<%=basePath%>vendor/metisMenu/metisMenu.min.js"></script>

<!-- Custom Theme JavaScript -->
<script src="<%=basePath%>dist/js/sb-admin-2.js"></script>

</body>

</html>
