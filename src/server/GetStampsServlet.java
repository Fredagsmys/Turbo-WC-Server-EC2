package server;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import db.SQL_connection;
import db.Stamp;
import wse.server.servlet.HttpServlet;
import wse.server.servlet.HttpServletRequest;
import wse.server.servlet.HttpServletResponse;

public class GetStampsServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SQL_connection connection = WC_server.getConnect();
		List<Stamp> stamps = new LinkedList<>();
		try {
			 stamps = connection.get_stamps();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String message = "";
		if (stamps.size() >0) {
			for (Stamp stamp : stamps) {
				message += "<li>" + stamp.bathroom + " rengjordes vid " + stamp.date + "</li>";
			}
		}
		String html = String.format("<article>"
				+ "<header><h1>De senaste städningarna av Turbos toaletter</h1>"
				+ "<p>Gjord av Max Mattsson</p>"
				+ "<ul>"
				+ "%s"
				+ "</ul>"
				+ "</header>"
				+ "</article>", message);
		byte[] byteMessage = html.getBytes(StandardCharsets.UTF_8);
		
		response.setContentLength(byteMessage.length);
		response.setContentType("utf-8");
		response.write(byteMessage);
	}
}
