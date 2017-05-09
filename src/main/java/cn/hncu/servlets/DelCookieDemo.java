package cn.hncu.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DelCookieDemo extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		//PrintWriter out = response.getWriter();
		Cookie cs[] = request.getCookies();
		if (cs != null) {
			for (Cookie c : cs) {
				// 要想遍历到"name"这个cookie，当前servlet必须要有读的权限，即servlet的url-pattern必须是该cookie所设路径或者是其所设路径的子路径
				// 删除name这个cookie
				if ("name".equals(c.getName())) {
					c.setPath(request.getContextPath());// 删除时是通过这一句来判断权限的！这里必须和原来所设的路径完全一样才能删除，否则不能删除！
					// 对于上句，我的个人理解是：因为如果你这个路径设置不同了，其实只是相当与新开了一个cookie，这个新cookie的到期时间是0，name是"name"

					c.setMaxAge(0);// 到期时见设为0，即是删除---此处只是设置删除标识
					response.addCookie(c);
				}
			}
		}
	}
}