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

import fr.eni.projet.encheres.bll.BLLException;
import fr.eni.projet.encheres.bll.EnchereManager;
import fr.eni.projet.encheres.bo.Enchere;

/**
 * Servlet implementation class ServletAffichageListeEnchere
 */
@WebServlet("/ServletAffichageListeEnchere")
public class ServletAffichageListeEnchere extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletAffichageListeEnchere() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			EnchereManager encMan = new EnchereManager();
			System.out.println("encMan opérationnel");

			List<Enchere> catEnchere = new ArrayList<Enchere>();

			// Article art = null;
			// catEnchere = encMan.getEncheresArticle();
			catEnchere = encMan.getCatalogue();
			System.out.println("catEnchere chargé");
			System.out.println("nbr d'entrée au catalogue : " + catEnchere.size());

			request.setAttribute("catEnchere", catEnchere);

		} catch (BLLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		// Transfert de l'affichage à la JSP

		response.setCharacterEncoding("UTF-8");

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Views/encheres/listeEnchere.jsp");
		rd.include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
