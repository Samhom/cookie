package cn.hncu.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println(" <HEAD><TITLE>演示利用Cookie显示用户上次登录的时间</TITLE></HEAD>");
		out.println(" <BODY>");

		// 读取客户端的cookie
		Cookie cs[] = request.getCookies();
		boolean boo = false;
		if (cs != null) {
			for (Cookie c : cs) {
				// 遍历
				if ("loginTime".equals(c.getName())) {
					String val = c.getValue();
					long dt = Long.parseLong(val);
					Date d = new Date(dt);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
					out.print("您上次登录时间是：" + sdf.format(d));
					boo = true;
					break;
				}
			}
		}
		if (boo == false) {// 表示之前1年没有访问记录！因为下面我们保存的过期时间是一年
			out.print("您最近一年是第一次访问。。。");
		}

		// 无论是新旧用户，都会以最近的时间俩创建一个Cookie，写到客户端。原来有了的，就是更新时间
		Date d = new Date();
		Cookie c = new Cookie("loginTime", "" + d.getTime());
		c.setPath(request.getContextPath());
		c.setMaxAge(60 * 60 * 24 * 30 * 12);
		response.addCookie(c);

		out.println(" </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

}