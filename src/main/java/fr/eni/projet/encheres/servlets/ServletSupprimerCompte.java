package fr.eni.projet.encheres.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projet.encheres.bll.BLLException;
import fr.eni.projet.encheres.bll.UtilisateurManager;
import fr.eni.projet.encheres.bo.user.Vendeur;

/**
 * Servlet implementation class ServletSupprimerCompte
 */
@WebServlet("/ServletSupprimerCompte")
public class ServletSupprimerCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletSupprimerCompte() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Recup Data Vendeur
		
		HttpSession session = request.getSession();
		Vendeur ven = (Vendeur) session.getAttribute("user");
		
		UtilisateurManager userMan;
		try {
			userMan = new UtilisateurManager();
			userMan.removeItem(ven);
			session.setAttribute("connecte", false);
		} catch (BLLException e) {
			System.out.println("Erreur Suppression Utilisateur");
		}
		
		RequestDispatcher rs = request.getRequestDispatcher("ServletAccueil");

		rs.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
