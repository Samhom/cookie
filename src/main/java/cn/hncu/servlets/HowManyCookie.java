package cn.hncu.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HowManyCookie extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		/*
		 * //测试个数--火狐47.0.1 最大支持110个 for(int i=1;i<=110;i++){ Cookie c = new
		 * Cookie("textNum"+i, ""+i); c.setMaxAge(60*15); c.setPath("/");
		 * response.addCookie(c); }
		 */

		// 测试大小 ---4092字节为最大支持的单个cookie存储
		String s = "";
		for (int i = 0; i < 4092; i++) {
			s += "1";
		}
		Cookie c = new Cookie("test", s);
		c.setMaxAge(60 * 15);
		c.setPath("/");
		response.addCookie(c);

		Cookie cs[] = request.getCookies();// 读取cookie
		if (cs != null) {// 防范一下
			for (Cookie cc : cs) {
				String key = cc.getName();
				String val = cc.getValue();
				out.print(key + "=" + val + " ");
			}
		}

	}

}