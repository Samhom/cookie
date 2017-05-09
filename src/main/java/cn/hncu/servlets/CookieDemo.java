package cn.hncu.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieDemo extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		// 向客户端写cookie
		Random r = new Random();
		int n = r.nextInt(100);
		String name = "jack";// cookie的格式：key=value
		Cookie c = new Cookie("name", name + n);
		c.setMaxAge(60 * 60);// 设置过期时间，以秒为单位
		c.setPath(request.getContextPath());// 该路径是: /项目名
		// Cookie机制中，是通过path来控制权限的。只有<url-pattern>和该path相同或是它的子路径的servlet才能够访问该cookie
		// 如果把一个cookie的path设为项目根目录，那么该项目下的所有servlet都能够访问它
		response.addCookie(c);

		// 这一段演示cookie带中文
		String str = "我带中文";
		str = URLEncoder.encode(str, "utf-8");// 中文设置编码！！！urlencode编码
		Cookie cStr = new Cookie("str", str);
		// 如果不设置setMaxAge，则浏览器一关闭就过期
		cStr.setPath("/");
		response.addCookie(cStr);

		// 读取客户端发过来的cookie
		Cookie cs[] = request.getCookies();// 读取cookie
		if (cs != null) {// 防范一下
			for (Cookie cc : cs) {
				String name2 = cc.getName();
				String val = cc.getValue();
				val = URLDecoder.decode(val, "utf-8");// 原来是怎么编码的，就怎么解码！
														// 中文解码，ascii是原样的！
				out.print(name2 + "=" + val + "<br/>");
			}
		}

		out.print("Cook保存成功！");
	}

}