package fr.eni.projet.encheres.servlets;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import fr.eni.projet.encheres.bll.BLLException;
import fr.eni.projet.encheres.bll.UtilisateurManager;
import fr.eni.projet.encheres.bo.Adresse;
import fr.eni.projet.encheres.bo.user.Vendeur;

/**
 * Servlet implementation class ServletCreerCompte
 */
@WebServlet("/ServletCreerCompte")
public class ServletCreerCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rs = request.getRequestDispatcher("WEB-INF/Views/user/creerCompte.jsp");
		rs.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Recuperation des données formulaire
		String pseudo = (String) request.getParameter("pseudo");
		String nom = (String) request.getParameter("nom");
		String prenom = (String) request.getParameter("prenom");
		String email = (String) request.getParameter("email");
		String phone = (String) request.getParameter("phone");
		String rue = (String) request.getParameter("rue");
		String cp = (String) request.getParameter("codePostal");
		String ville = (String) request.getParameter("ville");
		String mdp = (String) request.getParameter("mdp");
		String mdpConf = (String) request.getParameter("mdpConf");

		// Verification du mdp et de sa confirmation

		if (!mdp.equals(mdpConf)) {

			RequestDispatcher rs = request.getRequestDispatcher("WEB-INF/Views/creerCompte.jsp");
			rs.forward(request, response);
		}

		// Creation Adresse
		Adresse adr = new Adresse(rue, cp, ville);
		// Creation Vendeur

		Vendeur ven = new Vendeur(nom, prenom, pseudo, mdpConf, email, adr);

		if (!phone.isEmpty()) {
			ven.setTelephone(phone);
		}

		// Ajout à la BDD
		try {
			UtilisateurManager userMan = new UtilisateurManager();
			userMan.addItem(ven);
		} catch (BLLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Créer un moyen de rester connecté
		HttpSession session;

		session = request.getSession();

		session.setAttribute("connecte", (boolean) true);
		session.setAttribute("pseudo", pseudo);
		session.setAttribute("user", ven);
		// retour à l'accueil
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Views/accueil.jsp");
		rd.forward(request, response);
	}

}
