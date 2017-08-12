package com.dromedicas.webcontroller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




/**
 * Servlet implementation class ValidateMail
 */
@WebServlet("/ValidateMail")
public class ValidateMail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	private final String HOST =  "Host";
	private final String ACCEPT_ENCODING =  "Accept-Encoding";
	private final String X_FORWARDED_FO = "X-Forwarded-For";
	private final String X_FORWARDED_PROTO = "X-Forwarded-Proto";
	private final String USER_AGENT = "User-Agent";
	private final String X_REQUEST_START = "X-Request-Start";	
	private final String ACCEPT = "Accept";
	private final String CONNECTION = "Connection";
	private final String X_FORWARDED_PORT = "X-Forwarded-Port";
	private final String FROM = "From";
	
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValidateMail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		//
		
		// Obtengo el parametro tipo que me permitira saber cual tabla modificar
		String tipo = request.getParameter("id");
		String correoLeido = request.getParameter("openmail");
		
		response.setContentType("image/png");

		String pathToWeb = getServletContext().getRealPath(File.separator);
		File f = new File(pathToWeb + "images/pixelcontrol.png");
		BufferedImage bi = ImageIO.read(f);
		OutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		out.close();
		

		System.out.println("Mensaje leido enviado a: " + correoLeido);

		//obtenindo las estadisticas
		String ipHost = request.getRemoteAddr();
		String userAgent = request.getHeader(this.USER_AGENT);
	
		
		System.out.println("IpHost: " + ipHost);
		System.out.println("Agente y Sistema: " + userAgent);
		
		// request.setAttribute("mensaje", respuesta);
		// request.getRequestDispatcher("confirmacion.jsp").forward(request,
		// response);

	}	

}
