package fr.eni.projet.encheres.bo;

import java.sql.Date;
import java.time.LocalDate;

import fr.eni.projet.encheres.bo.user.Vendeur;


/**
 * @author Alexandre Mchich
 *
 *
 * @update William Ajout d'un constructeur vide correction nomenclature
 *         suppression de idEnchere dans le constructeur
 *
 */

public class Enchere {

	//L'id enchère est définie par un tableau : index 1 pour vendeur et index 0 pour Article
	private Integer[] idEnchere = new Integer[2];
	private Article article;
	private Vendeur vendeur;
	private Date dateEnchere;
	private int montantEnchere;

	public Enchere() {

	}

	public Enchere(int montantEnchere, Article article, Vendeur vendeur) {

		this.vendeur = vendeur;
		this.idEnchere[1]= vendeur.getId();
		
		this.article = article;
		this.idEnchere[0] = article.getId();
		
		this.dateEnchere = Date.valueOf(LocalDate.now());
		this.montantEnchere = montantEnchere;

	}
	
	public Enchere(Date dateEnchere, int montantEnchere, Article article, Vendeur vendeur) {

		this.vendeur = vendeur;
		vendeur.ajouterEnchere(this);
		this.idEnchere[1]= vendeur.getId();
		
		this.article = article;
		article.ajouterEnchere(this);
		this.idEnchere[0] = article.getId();
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;

	}
	
	
	public Integer[] getIdEnchere() {
		
		return idEnchere;
	}
	
	
	

	public Date getDateEnchere() {
		if(dateEnchere==null) {
			this.dateEnchere = Date.valueOf(LocalDate.now());
		}
		
		
		return dateEnchere;
	}

	public void setDateEnchere(Date dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getMontantEnchere() {
		return montantEnchere;
	}

	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
		this.idEnchere[0] = article.getId();
		article.ajouterEnchere(this);

	}

	public Vendeur getVendeur() {
		return vendeur;
	}

	public void setVendeur(Vendeur vendeur) {
		this.vendeur = vendeur;
		this.idEnchere[1]=vendeur.getId();
		vendeur.ajouterEnchere(this);
	}

	@Override
	public String toString() {
		StringBuffer enchere = new StringBuffer();
		
		enchere.append("Enchere sur : ").append(this.article.getNomArticle()).
		append(". Vendu par : " +this.article.getUtilisateur().getPseudo()).
		append(" Acheteur : "+ this.vendeur.getPseudo()).
		append(" Offre : "+this.montantEnchere).
		append("  Date : "+ this.dateEnchere);
		
		return enchere.toString();
	}

}