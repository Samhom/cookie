package cn.hncu.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieDemo2 extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		// 向客户端写Cookie
		Random r = new Random();
		int n = r.nextInt(100);
		Cookie c = new Cookie("age", "" + n);
		c.setMaxAge(60 * 60);// 过期时间
		c.setPath(request.getContextPath() + "/servlet/CookieDemo2");// Cookie机制中,是通过path来控制权限的
		// 由于CookieDemo的url-pattern是项目根目录/CookieDemo，不是当前cookie所设路径的子目录，因此无法访问到该cookie

		// 注意！！！path不一样，那么cookie是不同的对象，也就是不会覆那个名字相同的cookie！
		response.addCookie(c);

		// 读取从客户端发来的cookie
		Cookie cs[] = request.getCookies();
		if (cs != null) {
			for (Cookie cc : cs) {
				String name = cc.getName();
				String val = cc.getValue();
				out.print("22222--" + name + "=" + val + "<br/>");
			}
		}
		out.print("Cookie保存成功！");

	}
}