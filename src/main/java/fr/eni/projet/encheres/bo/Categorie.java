package fr.eni.projet.encheres.bo;

/**
 * @author Alexandre Mchich
 *
 *
 * @update William
 * 
 *         Remplacement de int id par Integer id Reorganisation des
 *         constructeurs Correction nomemclature
 */

public class Categorie {

	private Integer id;
	private String nom;

	public Categorie() {
	}

	public Categorie(String nom) {
		super();
		this.setNom(nom);
	}

	public void ajouterArticle(Article article) {
		this.ajouterArticle(article);
		// Méthode ajouter article

	}

	public Categorie(Integer id, String nom) {
		this.id = id;
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Categorie [id=").append(this.id).append(", nom=").append(this.nom);

				return builder.toString();
		}

}