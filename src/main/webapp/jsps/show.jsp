<%@page import="java.io.File"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<style type="text/css">
.span {
	border: 0px solid #000;
	width: 100px;
	height: 100px;
	overflow: hidden;
}

.span img {
	max-width: 100px;
	_width: expression(this.width > 100 ? "100px" : this.width);
}

.spans {
	border: 0px solid #000;
	width: 50px;
	height: 50px;
	overflow: hidden;
}

.spans img {
	max-width: 50px;
	_width: expression(this.width > 50 ? "50px" : this.width);
}
</style>

</head>

<body>
	<h1>看美女--利用Cookie技术显示用户最近浏览的若干个图片</h1>

	<a href="/cookie/jsps/show.jsp">看美女--利用Cookie技术显示用户最近浏览的若干个图片</a>
	<h3>最近浏览的图片：</h3>

	<!-- 添加最近3次浏览的图片 -->
	<%
		String str = null;
		Cookie cs[] = request.getCookies();
		if (cs != null) {
			for (Cookie c : cs) {
				if ("images".equals(c.getName())) {
					str = c.getValue();// ***.jpg
					break;
				}
			}
		}
		if (str != null) {
			String strs[] = str.split(",");
			for (String s : strs) {
	%>
	<span class="spans"> <img
		src="<%=request.getContextPath()%>/imgs/<%=s%>" />
	</span>
	<%
		}
		}
	%>

	<br />
	<hr />
	<br />

	<%
		//利用file遍历所有的图片，显示出来。
		String path = getServletContext().getRealPath("/imgs");
		//System.out.printf(path);//D:\apache-tomcat-7.0.30\webapps\myCookieWeb\jsps
		File file = new java.io.File(path);
		File[] files = file.listFiles();
		if (files != null) {
	%>

	<%
		for (File f : files) {
				String imgName = f.getName();
	%>
	<span class="span"> <a
		href="<%=request.getContextPath()%>/showImg?img=<%=imgName%>"> 
		<img src="<%=request.getContextPath()%>/imgs/<%=imgName%>" />
	</a>
	</span>
	<%
		}
	%>

	<%
		}
	%>

</body>
</html>