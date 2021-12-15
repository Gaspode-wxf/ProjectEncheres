package fr.eni.projet.encheres.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletAccueil
 */
@WebServlet(
		
		urlPatterns={"/ServletAccueil"}
		
		)
public class ServletAccueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAccueil() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Preparation du dispatcher + oeuf de paques
		RequestDispatcher rd = request.getRequestDispatcher("ServletAffichageListeArticle");
		
//		ServletAffichageListeArticle servAff = new ServletAffichageListeArticle();
//		servAff.getServletContext().getRealPath(getServletInfo())
		
		
		Cookie troll = new Cookie("NumCB", "CiaoEtBonDev");
		response.addCookie(troll);
		
		//Recuperation des articles ayant des encheres en cours
		
		
		
		// Envoi de la request
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
