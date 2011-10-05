<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="weibo4j.http.*"%>
<%@ page language="java" import="weibo4j.*"%>
<%@ page language="java" import="com.taobao.zhangjun.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>HistoryFind</title>
</head>
<body>
	<%
	String intervalDays = request.getParameter("intervalDays");
	AccessToken accessToken = (AccessToken)session.getAttribute("accToken");
	HistoryFind historyFind = new HistoryFind(accessToken);
	boolean intervalDaysValid = true;
	int intervalDaysInt = 0;
	try{
		intervalDaysInt = Integer.parseInt(intervalDays);
	}catch(Exception e){
		intervalDaysValid = false;
	}
	if( intervalDaysInt < 1 || intervalDaysInt > 365 ){
		intervalDaysValid = false ;
	}
	if( intervalDaysValid == true ){
		try{
			int findCount = historyFind.findAndPublish(intervalDaysInt);
			request.setAttribute("findCount",findCount);
			%>
			恭喜！${param.intervalDays}天前的数据共${findCount}条已经发布完了！
			<br /> 可以查看您的
			<a href="http://www.weibo.com" target="_blank">weibo</a>		
			<%
		}catch(Exception e){
			String msg = e.getMessage();
			%><font color="red">错误！</font>
			<br /><%=e%><%
		}
	}else{
		%><font color="red">输入天数不合法,请输入1-365之间的整数</font><%
	}
%>
	<p />
	<hr />
	@author
	</br>
	<b>weibo:</b>
	<a href="http://weibo.com/zyk1983" target="_blank">格子</a>
	<br />
	<b>mail:</b>jack.zhangyk@gmail.com
	<br/>
	<b>翻墙可用</b><a href="http://client.77jsq.com/aff.php?aff=1395" target="_blank">VPSS</a>,速度好很安全
	<br />
	<a href="/">返回首页</a>
</body>
</html>