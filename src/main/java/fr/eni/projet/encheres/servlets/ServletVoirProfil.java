package fr.eni.projet.encheres.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
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
 * Servlet implementation class ServletVoirProfil
 */
@WebServlet("/ServletVoirProfil")
public class ServletVoirProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Vendeur ven = (Vendeur) session.getAttribute("user");
		System.out.println("vendeur : " + ven);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Views/user/profil.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			
		//Recup Data Vendeur
		
		HttpSession session = request.getSession();
		Vendeur ven = (Vendeur) session.getAttribute("user");
		
		// Verification du mdp et de sa confirmation

				if (!mdp.equals(mdpConf)) {

					RequestDispatcher rs = request.getRequestDispatcher("WEB-INF/Views/user/profil.jsp");
					rs.forward(request, response);
				}
		//màj Adresse
		ven.getAdresse().setRue(rue);
		ven.getAdresse().setCodePostal(cp);
		ven.getAdresse().setVille(ville);
		//màj Vendeur
		ven.setPseudo(pseudo);
		ven.setNom(nom);
		ven.setPrenom(prenom);
		ven.setEmail(email);
		ven.setTelephone(phone);
		ven.setMdp(mdp);

		
		UtilisateurManager userMan;
		try {
			userMan = new UtilisateurManager();
			userMan.updateItem(ven);
		} catch (BLLException e) {
			System.out.println("Erreur Mise à jour Utilisateur");
		}
		
		RequestDispatcher rs = request.getRequestDispatcher("WEB-INF/Views/user/profil.jsp");

		rs.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doHead(HttpServletRequest, HttpServletResponse)
	 */
	protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
