package fr.eni.projet.encheres.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projet.encheres.bll.BLLException;
import fr.eni.projet.encheres.bll.UtilisateurManager;
import fr.eni.projet.encheres.bo.user.Vendeur;

/**
 * Servlet implementation class ServletSignIn
 */
@WebServlet("/ServletSignIn")
public class ServletSignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletSignIn() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cook : cookies) {
				if (cook.getName().equals("rem")) {
					request.setAttribute("pseudo", cook.getValue());
				}
			}
		} else {
			request.setAttribute("pseudo", null);
		}
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/Views/user/SignIn.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd;
		Cookie remember;

		// Recup données formulaire
		String pseudo = request.getParameter("pseudo");
		System.out.println("pseudo recup : " + pseudo);

		String mdp = request.getParameter("mdp");
		System.out.println("mdp recup : " + mdp);

		String rem = request.getParameter("remember");
		System.out.println("checkbox recup : " + rem);

		// On va chercher l'utilisateur
		Vendeur ven = null;
		try {
			UtilisateurManager userMan = new UtilisateurManager();
			System.out.println("userMan prêt à foncer !");

			ven = userMan.getVendeurViaPseudo(pseudo);

			System.out.println(ven);
		} catch (BLLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (ven == null) {
			System.out.println("pas de vendeur avec ce nom");
			rd = request.getRequestDispatcher("WEB-INF/Views/SignIn.jsp");
		} else {
			if (!ven.getMdp().trim().equals(mdp)) {
				System.out.println("Mot de Passe Erroné");
				rd = request.getRequestDispatcher("WEB-INF/Views/SignIn.jsp");
			} else {
				HttpSession session = request.getSession(true);
				session.setAttribute("user", ven);
				session.setAttribute("connecte", (boolean)true);
				session.setAttribute("pseudo", ven.getPseudo());
				rd = request.getRequestDispatcher("ServletAccueil");

				if (rem != null) {
					remember = new Cookie("rem", pseudo);
					
				}
				else {
					remember = new Cookie("rem", "");
				}
				remember.setMaxAge(99999);
				response.addCookie(remember);
				
				

			}
		}
		
		rd.forward(request, response);

	}

}
