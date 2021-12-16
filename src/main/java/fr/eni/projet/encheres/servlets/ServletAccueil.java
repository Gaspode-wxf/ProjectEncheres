package fr.eni.projet.encheres.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projet.encheres.bll.BLLException;
import fr.eni.projet.encheres.bll.CategorieManager;
import fr.eni.projet.encheres.bo.Categorie;

/**
 * Servlet implementation class ServletAccueil
 * @author Gaspode
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
		request.setAttribute("choixCategorie", "");
		request.setAttribute("motClef", "");

		
		Cookie troll = new Cookie("NumCB", "CiaoEtBonDev");
		response.addCookie(troll);
		List<Categorie> listeCat = null;
		try {
			CategorieManager catMan = new CategorieManager();
			listeCat = catMan.getCatalogue();
		} catch (BLLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("listeCat", listeCat);
		
		
		
		
		// Envoi de la request
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher("ServletAffichageListeArticle");
		
		List<Categorie> listeCat = null;
		try {
			CategorieManager catMan = new CategorieManager();
			listeCat = catMan.getCatalogue();
		} catch (BLLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("listeCat", listeCat);
		
		
		
		String categorieChoix = request.getParameter("choixCategorie");
		Categorie cat = null;	
if (!categorieChoix.isEmpty()) {
			
			try {
				CategorieManager catMan = new CategorieManager();
				cat = catMan.getCategorieViaNom(categorieChoix);
				request.setAttribute("categorieComp", cat);
			} catch (BLLException e) {
				e.printStackTrace();
			}
		}
		
		
		
		String motClef = request.getParameter("motClef");
		request.setAttribute("motClef", motClef);
		rd.forward(request, response);
	}

}
