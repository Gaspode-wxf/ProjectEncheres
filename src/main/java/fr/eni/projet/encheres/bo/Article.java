/**
 * 
 */
package fr.eni.projet.encheres.bo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projet.encheres.bo.user.Vendeur;

/**
 * @author Greg
 * @update William
 */
public class Article {

	// Attributs Article
	private Integer id;
	private String nomArticle;
	private String description;
	private Date dateDebut;
	private Date dateFin;
	private int prixInitial;
	private int prixVente;
	private int etatVente;

	// Attributs Utilisateur (le vendeur)
	private Vendeur user;

	// Attributs Retrait
	private Retrait ret;

	// Attributs Categorie
	private Categorie cat;

	// Attributs Enchere
	List<Enchere> listeEnc = new ArrayList<Enchere>();
	protected int prixActuel;
	protected Vendeur gagnant;

	// Constructeurs

	public Article() {
		super();
	}

	public Article(String nomArticle, String description, Date dateDebut, Date dateFin, int prixInitial,
			Categorie cat) {
		super();
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.prixInitial = prixInitial;
		this.cat = cat;
		this.cat.ajouterArticle(this);

	}

	// Méthodes Utilisateur
	public Vendeur getUtilisateur() {
		return this.user;
	}

	public void setUtilisateur(Vendeur ven) {
		this.user = ven;
		ven.ajouterArticle(this);
	}

	// Méthodes Categorie
	public Categorie getCategorie() {
		return this.cat;
	}

	public void setCategorie(Categorie cat) {
		this.cat = cat;
		cat.ajouterArticle(this);
	}

	// Méthodes Enchere
	public List<Enchere> getListeEncheres() {
		return this.listeEnc;
	}

	public void setListeEnchere(List<Enchere> listeEnc) {
		this.listeEnc = listeEnc;
		if (!listeEnc.isEmpty()) {
			this.prixActuel = listeEnc.get(listeEnc.size() - 1).getMontantEnchere();
			this.gagnant = listeEnc.get(listeEnc.size() - 1).getVendeur();

		}
	}

	public void ajouterEnchere(Enchere enc) {
		this.listeEnc.add(enc);
		this.prixActuel = enc.getMontantEnchere();
		this.gagnant = enc.getVendeur();
	}

	public int getPrixActuel() {
		return this.prixActuel;
	}

	public Vendeur getGagnant() {
		return this.gagnant;
	}

	// Méthodes Retrait
	public Retrait getRetrait() {
		return this.ret;
	}

	public void setRetrait(Retrait ret) {
		this.ret = ret;
	}

	// Methodes Article

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomArticle() {
		return this.nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateDebut() {
		return this.dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public int getPrixInitial() {
		return this.prixInitial;
	}

	public void setPrixInitial(int prixInitial) {
		this.prixInitial = prixInitial;
		this.prixActuel = prixInitial;
	}

	public int getPrixVente() {
		return this.prixVente;
	}

	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	public int getEtatVente() {
		return this.etatVente;
	}

	public void setEtatVente(int etatVente) {
		this.etatVente = etatVente;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Article [id=").append(this.id).append(", nomArticle=").append(this.nomArticle)
				.append(", description=").append(this.description).append(", dateDebut=").append(this.dateDebut)
				.append(", dateFin=").append(this.dateFin).append(", prixInitial=").append(this.prixInitial)
				.append(", prixVente=").append(this.prixVente).append(", etatVente=").append(this.etatVente)
				.append(", user=").append(this.user).append(", cat=").append(this.cat).append(", listeEnc=")
				.append(this.listeEnc).append(", ret=").append(this.ret).append("]");
		return builder.toString();
	}

}
