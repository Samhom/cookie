package cn.hncu.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println(" <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println(" <BODY>");

		String img = request.getParameter("img");
		String imgStr = "<img src='" + request.getContextPath() + "/imgs/" + img + "'/>";
		out.print(imgStr);

		// 用Cookie记录用户访问过的图片信息
		Cookie cs[] = request.getCookies();
		boolean boo = false;
		if (cs != null) {
			for (Cookie c : cs) {
				if ("images".equals(c.getName())) {// 已经有了images这个cookie
					String imgs = c.getValue();
					String imgStrs[] = imgs.split(",");
					boolean booStr = false;
					// 防范点击重复的图片
					for (int i = 0; i < imgStrs.length; i++) {
						if (imgStrs[i].equals(img)) {
							if (i == 1 && imgStrs.length == 2) {
								imgs = imgStrs[i] + "," + imgStrs[0];
							} else if (i == 2 && imgStrs.length == 3) {
								imgs = imgStrs[i] + "," + imgStrs[0] + "," + imgStrs[1];
							} else if (i == 2 && imgStrs.length == 3) {
								imgs = imgStrs[i] + "," + imgStrs[0] + "," + imgStrs[1];
							}
							booStr = true;
							break;
						}
					}
					if (!booStr) {

						imgs = img + "," + imgs;// 采用如下方式会麻烦一点：imgs+","+img
						if (imgs.split(",").length > 3) {// 如果访问的图片超过3次了
							imgs = imgs.substring(0, imgs.lastIndexOf(","));// 左包含，右不包含
						}

						/*
						 * //如果这样写了，最好把上面的防范重复的图片，那个添加顺序倒过来 //imgs+","+img 方式：
						 * imgs = imgs+","+img;
						 * if(imgs.split(",").length>3){//如果访问的图片超过3次了 imgs =
						 * imgs.substring(imgs.indexOf(",")+1, imgs.length()); }
						 */
					}
					c.setValue(imgs);// 更新
					c.setMaxAge(60 * 60 * 24 * 30);
					c.setPath("/");// 相当于把访问权限完全放开，即所有的项目都能访问
					response.addCookie(c);
					boo = true;
					break;
				}
			}
		}
		if (boo == false) {// 表示首次访问，即浏览器中没有图片浏览的cookie
			Cookie c = new Cookie("images", img);
			c.setMaxAge(60 * 60 * 24 * 30);
			c.setPath("/");
			response.addCookie(c);
		}

		out.println(" </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

}