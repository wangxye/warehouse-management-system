<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>提示框</title>

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
    <script src="[图片]https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="[图片]https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    </head>
    <body>
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">提示信息</h3>
                    </div>
                    <div class="panel-body">
                   ${message }<br>
                       请等待，<span id="second" style="color:red; font-size:35px;">3</span>s后返回<br>
                    </div>
                  <center><input type=button value=直接返回 class="btn btn-default btn-sm" onclick="window.history.back()"></center>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
      /*  "
      var num=document.getElementById("second").innerHTML;
        //获取显示秒数的元素，通过定时器来更改秒数。
        function count()
        {
            num--;
            document.getElementById("second").innerHTML=num;
            if(num==0)
            {
                location.assign("添加货物.html");
            }
        }
        setInterval("count()",1000);
        //通过window的location和history对象来控制网页的跳转。
*/
      (function() {
          var times = 3;
          var num = document.getElementById('second');
          setInterval(function() {
              times--;
              if (times) {
                  num.innerHTML = times ;
              } else {
                  history.go(-1);
              }
          }, 1000);
      })();
    </script>
    </body>
    </html>