package fr.eni.projet.encheres.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projet.encheres.bll.ArticleManager;
import fr.eni.projet.encheres.bll.BLLException;
import fr.eni.projet.encheres.bo.Article;

/**
 * Servlet implementation class ServletTestDBArticle
 */
@WebServlet("/ServletAffichageListeArticle")
public class ServletAffichageListeArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletAffichageListeArticle() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ouverture Servlet");
		try {

			ArticleManager articleManager = new ArticleManager();
			System.out.println("Manager Chargé");

			List<Article> catalogueArticles = new ArrayList<Article>();
			Article art = new Article();
			
			catalogueArticles = articleManager.getListeEnCours();
			System.out.println("catalogue chargé");
			System.out.println("nbr d'entrée au catalogue : " + catalogueArticles.size());

			request.setAttribute("catalogueArticles", catalogueArticles);
		} catch (BLLException e) {
			System.err.println(e);
		}

		// Transfert de l'affichage à la JSP
		
		response.setCharacterEncoding("UTF-8");
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Views/accueil.jsp");
		rd.include(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
