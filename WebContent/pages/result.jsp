<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta   http-equiv="refresh"   content="3;URL=${pageContext.request.contextPath}/Scan/getMap2/${createDate}/${goodiid}/${coordinate.horizontal}/${coordinate.vertical}">  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">  
            #canvas {  
                border: thin solid blue;  
            }  
        </style>

</head>
<body> 
时间：${createDate }
标签号：${goodiid }
<p id="message"></p> 
<canvas id="canvas">
</canvas>  
    <br>
</body>
 <script type="text/javascript">  
        var canvas = document.getElementById("canvas");  
        canvas.width=600;
        canvas.height=1000;
        var context = canvas.getContext("2d");
        //平面图
        context.fillStyle="#996633";
        context.strokeStyle = "black";
        context.font="30px Arial";
        context.strokeRect(0, 0, 78, 116);   //门
        context.strokeText("门",20,48);
        context.strokeText("口",20,88);
        context.fillRect(0, 151, 78, 139); //第一张桌子
        context.strokeText("桌",20,200);
        context.strokeText("子",20,250);
        context.fillRect(0, 441, 138, 139);
        context.strokeText("桌 子",28,525);
        context.fillRect(0, 731, 138, 139);
        context.strokeText("桌 子",28,810);
        context.fillRect(282, 310, 96, 270);
        context.strokeText("桌",310,400);
        context.strokeText("子",310,500);
        context.fillRect(282, 708, 96, 163);
        context.strokeText("桌",310,770);
        context.strokeText("子",310,830);
        context.fillRect(474, 731, 138, 139);
        context.strokeText("桌 子",510,810);
        context.fillRect(474, 441, 138, 139);
        context.strokeText("桌 子",510,525);
        context.fillRect(474, 151, 138, 139);
        context.strokeText("桌 子",510,232);//平面图
        var message = document.getElementById("message");  
        message.innerHTML = "x=" + ${coordinate.horizontal} + " ,y=" + ${coordinate.vertical};
        context.beginPath();
        context.arc(${coordinate.horizontal},${coordinate.vertical},10,0,2*Math.PI);
        context.stroke();
        canvas.onmousemove = function (e) {  
            var location = getLocation(e.clientX, e.clientY);  
            var message = document.getElementById("message");  
            message.innerHTML = "x=" + location.x + " ,y=" + location.y; 
            //context.clearRect(0, 0, canvas.width, canvas.height); 
            context.beginPath();
            context.arc(location.x,location.y,10,0,2*Math.PI);
            context.stroke();
        }; 
</script>
</html>
