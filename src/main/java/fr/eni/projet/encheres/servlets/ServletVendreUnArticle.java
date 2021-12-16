package fr.eni.projet.encheres.servlets;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projet.encheres.bll.ArticleManager;
import fr.eni.projet.encheres.bll.BLLException;
import fr.eni.projet.encheres.bll.CategorieManager;
import fr.eni.projet.encheres.bo.Article;
import fr.eni.projet.encheres.bo.Categorie;
import fr.eni.projet.encheres.bo.user.Vendeur;

/**
 * Servlet implementation class ServletAjoutArticle
 */
@WebServlet("/ServletVendreUnArticle")
public class ServletVendreUnArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletVendreUnArticle() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Views/articles/vendreUnArticle.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// récupération données formulaire
		String nomArticle = request.getParameter("nomArticle");
		String description = request.getParameter("description");
		String libelleCategorie = request.getParameter("categorie");

		Categorie categorie = null;
		try {
			CategorieManager catMan = new CategorieManager();
			categorie = catMan.getCategorieViaNom(libelleCategorie);
		} catch (BLLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

		Integer prixInitial = Integer.parseInt(request.getParameter("prixInitial"));
		String dateDebut = request.getParameter("dateDebut");
		String[] amj = dateDebut.split("-");
		String a = amj[0];
		String m = amj[1];
		String j = amj[2];

		LocalDate dateDebutDate = LocalDate.of(Integer.parseInt(a), Integer.parseInt(m), Integer.parseInt(j));
		Date dateDebutSql = Date.valueOf(dateDebutDate);
		String dateFin = request.getParameter("dateFin");
		String[] bni = dateFin.split("-");
		String b = bni[0];
		String n = bni[1];
		String i = bni[2];

		LocalDate dateFinDate = LocalDate.of(Integer.parseInt(b), Integer.parseInt(n), Integer.parseInt(i));
		Date dateFinSql = Date.valueOf(dateFinDate);

		// création article - Constructeur à créer
		Article article = new Article(nomArticle, description, dateDebutSql, dateFinSql, prixInitial, categorie);
		HttpSession session = request.getSession(false);
		Vendeur ven = (Vendeur) session.getAttribute("user");
		article.setUtilisateur(ven);
		// Ajout à la BDD

		try {
			ArticleManager articleMan = new ArticleManager();
			articleMan.addItem(article);
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}

}
