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

    <title>登录</title>

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
body{
 background-image:url("<%=basePath%>front/img/bg.jpg");
 background-size:100%;
}
</style>
</head>

<body>

    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Please Sign In</h3>
                    </div>
                    <div class="panel-body">
                        <form action="${pageContext.request.contextPath}/User/login.action"  onSubmit="return check(this)" method="post">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Username" name="username" type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name="password" type="password" value="">
                                </div>
                                <div class="checkbox">
                                    <label>
                                        <input name="remember" type="checkbox" value="Remember Me">Remember Me
                                    </label>
                                </div>
                                <!-- Change this to a button or input when using this as a form -->
                                <input type="submit" class="btn btn-outline btn-primary" value="登录">     
                                 <a href="#myModal" data-toggle="modal" >还没注册？</a>        
                            </fieldset>
                        </form>
                             <!-- 注册 -->
                          <!-- 模态框（Modal） -->
                          <form onsubmit="return toVaild()" action="${pageContext.request.contextPath}/pages/register.jsp">
                            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
                               <div class="modal-dialog" style="width:300px">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                           <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                  &times;
                                           </button>
                                        <h4 class="modal-title" id="myModalLabel">
                                                                          管理权限
                                         </h4>
                                       </div>
                                  			<div class="modal-body">
                                                                                          请输入密码：
                   						    <input class="form-control" placeholder="Password" id="ppassword" type="password">
                						    </div>
               							 <div class="modal-footer">
                   						 <button type="submit" class="btn btn-default" id="submit">确定</button>
                					     </div>
           					            </div><!-- /.modal-content -->
    								  </div><!-- /.modal -->
									</div>
								</form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- jQuery -->
    <script src="<%=basePath%>vendor/jquery/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="<%=basePath%>vendor/bootstrap/js/bootstrap.min.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="<%=basePath%>vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="<%=basePath%>dist/js/sb-admin-2.js"></script>

</body>
<script>
    function toVaild(){
        var val = document.getElementById("ppassword").value;
        if(val == "admin"){
            alert("密码正确");
            return true;
        }
        else{
            alert("密码错误");
            return false;
        }
    }
</script>

</html>
 