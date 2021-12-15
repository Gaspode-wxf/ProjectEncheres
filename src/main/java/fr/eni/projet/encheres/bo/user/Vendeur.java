/**
 * 
 */
package fr.eni.projet.encheres.bo.user;

import java.util.ArrayList;
import java.util.List;

import fr.eni.projet.encheres.bo.Adresse;
import fr.eni.projet.encheres.bo.Article;
import fr.eni.projet.encheres.bo.Enchere;

/**
 * @author William Freyer
 *
 */
public class Vendeur extends Utilisateur {

	private int credit = 0;

	private List<Article> listeArticles = new ArrayList<Article>();
	private List<Enchere> listeEnchere = new ArrayList<Enchere>();
	

	public Vendeur() {

	}

	public Vendeur(String nom, String prenom, String pseudo, String mdp, String email, Adresse adresse) {
		super(nom, prenom, pseudo, mdp, email, adresse);
	}

	public Vendeur(Integer id, String nom, String prenom, String pseudo, String mdp, String email, Adresse adresse) {
		super(id, nom, prenom, pseudo, mdp, email, adresse);
	}

	public int getCredit() {
		return this.credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public List<Article> getListeArticles() {
		return this.listeArticles;
	}

	public void setListeArticles(List<Article> listeArticles) {
		this.listeArticles = listeArticles;
		for(Article art : listeArticles) {
			art.setUtilisateur(this);
		}
	}

	public void ajouterArticle(Article article) {
		this.listeArticles.add(article);
		
	}

	/**
	 * @return the listeEnchere
	 */
	public List<Enchere> getListeEnchere() {
		return listeEnchere;
	}

	/**
	 * @param listeEnchere the listeEnchere to set
	 */
	public void setListeEnchere(List<Enchere> listeEnchere) {
		this.listeEnchere = listeEnchere;
		for(Enchere enc : listeEnchere) {
			enc.setVendeur(this);
		}
	}
	
	public void ajouterEnchere(Enchere enc) {
		this.listeEnchere.add(enc);
	}
	@Override
	public void setAdministrateur(boolean administrateur) {
		if(!this.isAdministrateur())
		System.err.println("tu pensais vraiment qu'avec tes droits de base tu pourrais devenir un Admin ?");
		
	}

}
