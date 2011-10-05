<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="weibo4j.http.*" %>
<%@ page language="java" import="weibo4j.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>HistoryFind</title>
<jsp:useBean id="weboauth" scope="request" class="com.taobao.zhangjun.WebOAuth" />
</head>
<body>
<%
	
	String verifier=request.getParameter("oauth_verifier");
	
	if(verifier!=null)
	{
	System.out.println("oauth:"+verifier);
		RequestToken resToken=(RequestToken) session.getAttribute("resToken");

		if(resToken!=null)
		{
			AccessToken accessToken=weboauth.requstAccessToken(resToken,verifier);
			if( accessToken != null ){
					//out.println(accessToken.getToken());
					//out.println(accessToken.getTokenSecret());
					//第二个参数每次只能用一次，发表微博内容不能重复，如果重复发会返回400错误
					//这个accessToken不用每次访问都重新取，可以存到session里面用
					//weboauth.update(accessToken,"web方式发表微博4");
					session.setAttribute("accToken",accessToken);
					%>
						<form action="historyfinddo.jsp" >
						查找<input type="input" value="4" name="intervalDays"/>天前的记忆。<input type="submit" value="查找"/>
						</form>
						<p/>
						注意:必须是1-365之间的整数
					<%		
			}else{
				out.println("access token request error");
			}
		
		}else{
			out.println("request token session error");
		}
	}
	else
		{
		out.println("verifier String error");
		}

%>
</body>
</html>