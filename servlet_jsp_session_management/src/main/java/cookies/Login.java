package cookies;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import session_management.User;
import session_management.UserDao;

@WebServlet("/logincookies")
public class Login extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");

		UserDao userdao = new UserDao();
		User user = userdao.login(email);

		RequestDispatcher rd = req.getRequestDispatcher("login.jsp");

		PrintWriter writer = resp.getWriter();
		if (user == null) {
			writer.print("<h1>Invalid Email adress</h1>");
			rd.include(req, resp);
		} else {
			if (password.equals(user.getPassword())) {
				Cookie cookie1 = new Cookie("email", email);
				resp.addCookie(cookie1);
				Cookie cookie2 = new Cookie("password", password);
				resp.addCookie(cookie2);

				resp.sendRedirect("home.jsp");
			} else {
				writer.print("<h1>Wrong Password</h1>");
				rd.include(req, resp);
			}
		}
	}
}