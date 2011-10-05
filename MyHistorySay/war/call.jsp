<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="weibo4j.*" %>
<%@ page language="java" import="weibo4j.http.*" %>
<% %>
<jsp:useBean id="weboauth" scope="request" class="com.taobao.zhangjun.WebOAuth" />
<%
	String host = "weiboutil.appspot.com";
	//host = "localhost:8888";
	//System.out.println(weboauth);
	RequestToken resToken=weboauth.request("http://"+host+"/historyfind.jsp");
	if(resToken!=null){
		out.println(resToken.getToken());
		out.println(resToken.getTokenSecret());
		session.setAttribute("resToken",resToken);
		response.sendRedirect(resToken.getAuthorizationURL());
		
	}else{
		out.println("request error");
	}
%>