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

    <title>注册</title>

    <!-- Bootstrap Core CSS -->
    <link href="<%=basePath%>vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="<%=basePath%>vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<%=basePath%>dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

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
        return true;
}
</script>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">请输入相关信息</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form"   action="${pageContext.request.contextPath}/User/Register.action"    onSubmit="return check(this)" method="post" >
                            <fieldset>
                                 <div class="form-group">
                                  <input class="form-control" placeholder="人员标签ID" name="iid" type="text" >
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="账号" name="username" type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="密码必须多于或等于 6个字符" name="password" type="password" value="">
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="用户名" name="name" type="text" >
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="手机" name="phone" type="text" >
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="邮箱" name="email" type="text" >
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="地址" name="address" type="text" >
                                </div>
                              
                                <input class="btn btn-lg btn-success btn-block" type="submit" name="submit"value="注册"> 
                                
                            </fieldset>
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

</html>
