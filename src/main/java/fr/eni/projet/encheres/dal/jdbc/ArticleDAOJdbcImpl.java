package fr.eni.projet.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projet.encheres.bo.Adresse;
import fr.eni.projet.encheres.bo.Article;
import fr.eni.projet.encheres.bo.Categorie;
import fr.eni.projet.encheres.bo.user.Vendeur;
import fr.eni.projet.encheres.dal.ConnectionProvider;
import fr.eni.projet.encheres.dal.DALException;
import fr.eni.projet.encheres.dal.DAOArticle;

/**
 * @author Greg
 *
 */
public class ArticleDAOJdbcImpl extends DAOJdbcImpl<Article> implements DAOArticle {

	String sqlDeleteByID = "delete from ARTICLES where id=?";
	String sqlInsert = "insert into ARTICLES(nomArticle, description, dateDebutEncheres, dateFinEncheres, prixInitial, prixVente, idUtilisateur, idCategorie) values (?,?,?,?,?,?,?,?)";
	String sqlSelectByID = "select nomArticle, description, dateDebutEncheres, dateFinEncheres, prixInitial, prixVente, CATEGORIES.id as idCategorie, libelle, UTILISATEURS.id as idUtilisateur, pseudo, nom, prenom, email, telephone, rue, codePostal, ville from ARTICLES inner join UTILISATEURS on idUtilisateur=Utilisateurs.id inner join CATEGORIES on  idCategorie=Categories.id where Articles.id=?";
	String sqlSelectAll = "select Articles.id as id, nomArticle, description, dateDebutEncheres, dateFinEncheres, prixInitial, prixVente, CATEGORIES.id as idCategorie, libelle, UTILISATEURS.id as idUtilisateur, pseudo, nom, prenom, email, telephone, rue, codePostal, ville from ARTICLES inner join UTILISATEURS on idUtilisateur=Utilisateurs.id inner join CATEGORIES on idCategorie=CATEGORIES.id";
	String sqlUpdate = "update ARTICLES set nomArticle=?, description=?, dateDebutEncheres=?, dateFinEncheres=?, prixInitial=?, prixVente=?, idUtilisateur=?, idCategorie=?, where id=? ";
	String sqlTruncate = "truncate table ARTICLES";
	String sqlSelectByMotClef = "select Articles.id as id, nomArticle, description, dateDebutEncheres, dateFinEncheres, prixInitial, prixVente, CATEGORIES.id as idCategorie, libelle, UTILISATEURS.id as idUtilisateur, pseudo, nom, prenom, email, telephone, rue, codePostal, ville from ARTICLES inner join UTILISATEURS on idUtilisateur=Utilisateurs.id inner join CATEGORIES on  idCategorie=Categories.id where nomArticle like ?";
	String sqlSelectByCategorie = "select Articles.id as id, nomArticle, description, dateDebutEncheres, dateFinEncheres, prixInitial, prixVente, CATEGORIES.id as idCategorie, libelle, UTILISATEURS.id as idUtilisateur, pseudo, nom, prenom, email, telephone, rue, codePostal, ville from ARTICLES inner join UTILISATEURS on idUtilisateur=Utilisateurs.id inner join CATEGORIES on idCategorie=CATEGORIES.id where CATEGORIES.id=?";
	String sqlSelectByUtilisateur = "select Articles.id as id, nomArticle, description, dateDebutEncheres, dateFinEncheres, prixInitial, prixVente, CATEGORIES.id as idCategorie, libelle, UTILISATEURS.id as idUtilisateur, pseudo, nom, prenom, email, telephone, rue, codePostal, ville from ARTICLES inner join UTILISATEURS on idUtilisateur=Utilisateurs.id inner join CATEGORIES on idCategorie=CATEGORIES.id where UTILISATEURS.id=?";
	private String sqlSelectByEnchereEnCours = "select Articles.id as id, nomArticle, description, dateDebutEncheres, dateFinEncheres, prixInitial, prixVente, CATEGORIES.id as idCategorie, libelle, UTILISATEURS.id as idUtilisateur, pseudo, nom, prenom, email, telephone, rue, codePostal, ville from ARTICLES inner join UTILISATEURS on idUtilisateur=Utilisateurs.id inner join CATEGORIES on idCategorie=CATEGORIES.id where DATEDIFF(day,Articles.dateDebutEncheres, CURRENT_TIMESTAMP)>=0 and DATEDIFF(day,Articles.dateFinEncheres, CURRENT_TIMESTAMP)<0";

	public ArticleDAOJdbcImpl() {
		setSqlDeleteByID(sqlDeleteByID);
		setSqlSelectAll(sqlSelectAll);
		setSqlSelectByID(sqlSelectByID);
		setSqlTruncate(sqlTruncate);
		setSqlUpdate(sqlUpdate);

	}

	@Override
	public void completeStmt(Article a, PreparedStatement stmt) throws SQLException {

		/*
		 * ordre des attributs nomArticle, description, dateDebutEnchere,
		 * dateFinEnchere, prixInitial, prixVente, idUtilisateur, idCategorie
		 * 
		 */
		int index = 1;
		stmt.setString(index++, a.getNomArticle());
		stmt.setString(index++, a.getDescription());
		stmt.setDate(index++, a.getDateDebut());
		stmt.setDate(index++, a.getDateFin());
		stmt.setInt(index++, a.getPrixInitial());
		stmt.setInt(index++, a.getPrixVente());
		stmt.setInt(index++, a.getUtilisateur().getId());
		stmt.setInt(index++, a.getCategorie().getId());

		if (a.getId() != null) {
			stmt.setInt(index++, a.getId());
		}
	}

	@Override
	public Article createFromRS(ResultSet rs) throws SQLException {

		// génération de l'adresse
		Adresse adr = new Adresse();

		adr.setRue(rs.getString("rue"));
		adr.setCodePostal(rs.getString("codePostal"));
		adr.setVille("ville");

		System.out.println("Génération adresse");

		// génération du vendeur
		Vendeur vendeur = new Vendeur();

		vendeur.setId(rs.getInt("idUtilisateur"));
		vendeur.setPseudo(rs.getString("pseudo"));
		vendeur.setNom(rs.getString("nom"));
		vendeur.setPrenom(rs.getString("prenom"));
		vendeur.setEmail(rs.getString("email"));
		vendeur.setTelephone(rs.getString("telephone"));
		vendeur.setAdresse(adr);
//		vendeur.setMdp(rs.getString("motDePasse"));
//		vendeur.setCredit(rs.getInt("credit"));
//		vendeur.setAdministrateur(rs.getBoolean("administreur"));

		System.out.println("Génération vendeur");

		// génération de la Categorie
		Categorie cat = new Categorie();
		cat.setId(rs.getInt("idCategorie"));
		cat.setNom(rs.getString("libelle"));

		System.out.println("Génération Catégorie");

		// génération de l'article
		Article a = new Article();

		a.setId(rs.getInt("id"));
		a.setNomArticle(rs.getString("nomArticle"));
		a.setDescription(rs.getString("description"));
		a.setDateDebut(rs.getDate("dateDebutEncheres"));
		a.setDateFin(rs.getDate("dateFinEncheres"));
		a.setCategorie(cat);
		a.setUtilisateur(vendeur);

		System.out.println("Génération Article");

		return a;

	}

	@Override
	public void delete(Article a) throws DALException {
		this.deleteByID(a.getId());

	}

	@Override
	public void insert(Article a) throws DALException {
		String sql = sqlInsert;
		try (Connection con = ConnectionProvider.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				ResultSet rs = null;) {
			completeStmt(a, stmt);
			stmt.execute();
			a.setId(JdbcTools.recupID(stmt, rs));
		} catch (SQLException e) {
			throw new DALException("erreur de requete Insert", e);

		}
	}

	@Override
	public List<Article> selectByMotClef(String motClef) throws DALException {
		String sql = sqlSelectByMotClef;
		List<Article> liste = new ArrayList<Article>();
		Article a = null;
		try (Connection con = ConnectionProvider.getConnection(); PreparedStatement stmt = con.prepareStatement(sql);) {
			stmt.setString(1, motClef);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				a = createFromRS(rs);
			}
			liste.add(a);
		} catch (SQLException e) {
			throw new DALException("erreur de requête SelectAll", e);
		}
		return liste;
	}

	@Override
	public List<Article> selectByCategorie(int idCategorie) throws DALException {
		String sql = sqlSelectByCategorie;
		List<Article> liste = new ArrayList<Article>();
		Article a = null;
		try (Connection con = ConnectionProvider.getConnection(); PreparedStatement stmt = con.prepareStatement(sql);) {
			stmt.setInt(1, idCategorie);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				a = createFromRS(rs);
			}
			liste.add(a);

		} catch (SQLException e) {
			throw new DALException("erreur de requête SelectAll", e);
		}
		return liste;
	}

	@Override
	public List<Article> selectByUtilisateur(int idUtilisateur) throws DALException {
		String sql = sqlSelectByUtilisateur;
		List<Article> liste = new ArrayList<Article>();
		Article a = null;
		try (Connection con = ConnectionProvider.getConnection(); PreparedStatement stmt = con.prepareStatement(sql);) {
			stmt.setInt(1, idUtilisateur);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				a = createFromRS(rs);
			}
			liste.add(a);

		} catch (SQLException e) {
			throw new DALException("erreur de requête SelectAll", e);
		}
		return liste;
	}

	@Override
	public List<Article> selectByEnchereEnCours() throws DALException {
		String sql = sqlSelectByEnchereEnCours;
		List<Article> liste = new ArrayList<Article>();
		Article art = null;
		try (Connection con = ConnectionProvider.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {

			while (rs.next()) {

				art = createFromRS(rs);
				liste.add(art);
			}
		} catch (SQLException e) {
			throw new DALException("erreur de requete Select All", e);
		}
		return liste;
	}

}
