/**
 * 
 */
package fr.eni.projet.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projet.encheres.bo.Adresse;
import fr.eni.projet.encheres.bo.Article;
import fr.eni.projet.encheres.bo.Categorie;
import fr.eni.projet.encheres.bo.Enchere;
import fr.eni.projet.encheres.bo.user.Utilisateur;
import fr.eni.projet.encheres.bo.user.Vendeur;
import fr.eni.projet.encheres.dal.ConnectionProvider;
import fr.eni.projet.encheres.dal.DALException;
import fr.eni.projet.encheres.dal.DAOEnchere;

/**
 * @author Gaspode
 *
 */

public class EnchereJdbcImpl extends DAOJdbcImpl<Enchere> implements DAOEnchere {

	String sqlDeleteByID = "delete from Encheres where idUtilisateur=? AND idArticle=?";
	String sqlInsert = "insert into Encheres(idUtilisateur, idArticle, dateEnchere, montantEnchere) values (?,?,?,?)";
	String sqlSelectByID = "select dateEnchere, montantEnchere, idArticle, nomArticle,"
							+ " description, dateDebutEncheres, dateFinEncheres," 
							+ " idCategorie, libelle, Encheres.idUtilisateur as idUser,"
							+ " pseudo, nom, prenom, email, telephone, rue, codePostal, " 
							+ "ville, motDePasse, credit, administrateur "
							+ "from ENCHERES " 
							+ "inner join ARTICLES on idArticle=Articles.id "
							+ " inner join CATEGORIES on Articles.idCategorie=Categories.id"
							+ "inner join UTILISATEURS on ENCHERES.idUtilisateur=UTILISATEURS.id "
							+ "where Encheres.idUtilisateur = ? AND idArticle = ?";
	String sqlSelectAll = "select dateEnchere, montantEnchere, idArticle, nomArticle,"
							+ " description, dateDebutEncheres, dateFinEncheres," 
							+ " idCategorie,Encheres.idUtilisateur as idUser,"
							+ " pseudo, nom, prenom, email, telephone, rue, codePostal, " 
							+ "ville, motDePasse, credit, administrateur "
							+ "from ENCHERES " 
							+ "inner join ARTICLES on idArticle=Articles.id "
							+ "inner join UTILISATEURS on ENCHERES.idUtilisateur=UTILISATEURS.id";
	String sqlUpdate = "update ENCHERES set idUtilisateur=?, dateEnchere=?, montantEnchere=?, where idUtilisateur=? AND idArticle=?";
	String sqlTruncate = "truncate table ENCHERES";
	String sqlSelectByArticle = "select dateEnchere, montantEnchere, idArticle, nomArticle,"
								+ " description, dateDebutEncheres, dateFinEncheres,"
								+ " idCategorie, libelle, Encheres.idUtilisateur as idUser,"
								+ " pseudo, nom, prenom, email, telephone, rue, codePostal, "
								+ "ville, motDePasse, credit, administrateur "
								+ "from ENCHERES "
								+ "inner join ARTICLES on idArticle=Articles.id " 
								+ "inner join CATEGORIES on ARTICLES.idCategorie=Categories.id "
								+ "inner join UTILISATEURS on ENCHERES.idUtilisateur=UTILISATEURS.id "
								+ "where idArticle = ?";
	private String sqlSelectByUser = "select dateEnchere, montantEnchere, idArticle, nomArticle,"
			+ " description, dateDebutEncheres, dateFinEncheres,"
			+ " idCategorie,Encheres.idUtilisateur as idUser,"
			+ " pseudo, nom, prenom, email, telephone, rue, codePostal, "
			+ "ville, motDePasse, credit, administrateur "
			+ "from ENCHERES " 
			+ "inner join ARTICLES on idArticle=Articles.id "
			+ "inner join UTILISATEURS on ENCHERES.idUtilisateur=UTILISATEURS.id "
			+ "where Encheres.idUtilisateur = ?";;

	public EnchereJdbcImpl() {
		setSqlDeleteByID(sqlDeleteByID);
		setSqlSelectAll(sqlSelectAll);
		setSqlSelectByID(sqlSelectByID);
		setSqlTruncate(sqlTruncate);
		setSqlUpdate(sqlUpdate);
	}

	// avec un seul id impossible de trouver une enchère simple, pour prévenir des
	// erreurs la method a été réécrite pour avertir l'utilisateur
	// et elle ne renvoit rien pour éviter des conflits.
	// il est possible d'y mettre un throw new Exception
	@Override
	public Enchere selectByID(Integer id) throws DALException {

		System.err.println("Il manque un paramètre pour la recherche");
		return null;
	}

	// la clef de Enchere n'est pas un simple ID, c'est la combinaison d'un article
	// ET d'un utilisateur, il a donc fallu surcharger la methode pour qu'elle
	// prenne en paramètres
	// les deux id

	public Enchere selectByID(Integer[] idEnchere) throws DALException {

		String sql = sqlSelectByID;
		Enchere enc = null;
		try (Connection con = ConnectionProvider.getConnection(); PreparedStatement stmt = con.prepareStatement(sql);) {

			stmt.setInt(1, idEnchere[1]);
			stmt.setInt(2, idEnchere[0]);
			try (ResultSet rs = stmt.executeQuery();) {

				rs.next();

				enc = createFromRS(rs);
			}

		} catch (SQLException e) {

			throw new DALException("erreur de requete Select by ID", e);
		}

		return enc;
	}

	@Override
	public List<Enchere> selectByArticle(Article art) throws DALException {

		String sql = sqlSelectByArticle;
		List<Enchere> liste = new ArrayList<Enchere>();
		Enchere enc = null;
		try (Connection con = ConnectionProvider.getConnection(); PreparedStatement stmt = con.prepareStatement(sql);) {

			stmt.setInt(1, art.getId());

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				enc = createFromRS(rs);
				liste.add(enc);

			}

		} catch (SQLException e) {

			throw new DALException("erreur de requete SelectByArticle", e);
		}

		return liste;
	}

	
	@Override
	public List<Enchere> selectByUser(Utilisateur user) throws DALException {

		String sql = sqlSelectByUser ;
		List<Enchere> liste = new ArrayList<Enchere>();
		Enchere enc = null;
		try (Connection con = ConnectionProvider.getConnection(); PreparedStatement stmt = con.prepareStatement(sql);) {

			stmt.setInt(1, user.getId());

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				enc = createFromRS(rs);
				liste.add(enc);

			}

		} catch (SQLException e) {

			throw new DALException("erreur de requete SelectByArticle", e);
		}

		return liste;
	}
	
	
	
	
	
	@Override
	public void deleteByID(Integer id) throws DALException {
		System.err.println("Il manque un paramètre pour la recherche");
	}

	public void deleteByID(Integer[] idEnchere) throws DALException {
		String sql = sqlDeleteByID;

		try (Connection con = ConnectionProvider.getConnection(); PreparedStatement stmt = con.prepareStatement(sql);) {
			stmt.setInt(1, idEnchere[1]);
			stmt.setInt(2, idEnchere[0]);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("erreur de suppression", e);
		}
	}

	@Override
	public void delete(Enchere t) throws DALException {

		this.deleteByID(t.getIdEnchere());

	}

	@Override
	public void insert(Enchere t) throws DALException {
		String sql = sqlInsert;
		try (Connection con = ConnectionProvider.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				ResultSet rs = null;) {
			completeStmt(t, stmt);
			stmt.execute();
		} catch (SQLException e) {
			throw new DALException("erreur de requete Insert", e);

		}
	}

	@Override
	public void completeStmt(Enchere t, PreparedStatement stmt) throws SQLException {
		/*
		 * ordre des attributs : update idUtilisateur, dateEnchere, montantEnchere where
		 * idUtilisateur, idArticle insert idUtilisateur, idArticle, dateEnchere,
		 * montantEnchere
		 * 
		 * Comme tous les paramètres doivent être ajoutés et qu'il n'y a pas le même
		 * nombre en fonction de la requete, il a fallu trouver une astuce pour
		 * différencier les deux requetes, en esperant que
		 * getParameterMetaData().getParameterCount() donne bien le nombre de "?"
		 */
		int nbData = stmt.getParameterMetaData().getParameterCount();
		Integer idUtilisateur = t.getIdEnchere()[1];
		Integer idArticle = t.getIdEnchere()[0];
		Date dateEnchere = t.getDateEnchere();
		int montantEnchere = t.getMontantEnchere();
		int index = 1;
		if (nbData == 5) {
			stmt.setInt(index++, idUtilisateur);
			stmt.setDate(index++, dateEnchere);
			stmt.setInt(index++, montantEnchere);
			stmt.setInt(index++, idUtilisateur);
			stmt.setInt(index++, idArticle);
		} else if (nbData == 4) {
			stmt.setInt(index++, idUtilisateur);
			stmt.setInt(index++, idArticle);
			stmt.setDate(index++, dateEnchere);
			stmt.setInt(index++, montantEnchere);
		}
	}

	@Override
	public Enchere createFromRS(ResultSet rs) throws SQLException {

		// génération de l'adresse

		Adresse adr = new Adresse();

		adr.setRue(rs.getString("rue"));
		adr.setCodePostal(rs.getString("codePostal"));
		adr.setVille("ville");

		// génération du vendeur
		Vendeur vendeur = new Vendeur();

		vendeur.setId(rs.getInt("idUser"));
		vendeur.setPseudo(rs.getString("pseudo"));
		vendeur.setNom(rs.getString("nom"));
		vendeur.setPrenom(rs.getString("prenom"));
		vendeur.setEmail(rs.getString("email"));
		vendeur.setTelephone(rs.getString("telephone"));
		vendeur.setAdresse(adr);
		vendeur.setMdp(rs.getString("motDePasse"));
		vendeur.setCredit(rs.getInt("credit"));
//		vendeur.setAdministrateur(rs.getBoolean("administreur"));
//génération de la Categorie

		Categorie cat = new Categorie();
		cat.setId(rs.getInt("idCategorie"));
		cat.setNom("libelle");
//génération de l'article
		Article art = new Article();

		art.setId(rs.getInt("idArticle"));
		art.setNomArticle(rs.getString("nomArticle"));
		art.setDescription(rs.getString("description"));
		art.setDateDebut(rs.getDate("dateDebutEncheres"));
		art.setDateFin(rs.getDate("dateFinEncheres"));
		art.setCategorie(cat);
		art.setUtilisateur(vendeur);

//génération de l'enchere : 
		Enchere enc = new Enchere();
		enc.setVendeur(vendeur);
		enc.setArticle(art);
		enc.setDateEnchere(rs.getDate("dateEnchere"));
		enc.setMontantEnchere(rs.getInt("montantEnchere"));

		return enc;
	}

}
