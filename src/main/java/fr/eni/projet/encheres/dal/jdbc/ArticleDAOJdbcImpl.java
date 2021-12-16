package fr.eni.projet.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projet.encheres.bll.BLLException;
import fr.eni.projet.encheres.bll.EnchereManager;
import fr.eni.projet.encheres.bo.Adresse;
import fr.eni.projet.encheres.bo.Article;
import fr.eni.projet.encheres.bo.Categorie;
import fr.eni.projet.encheres.bo.user.Vendeur;
import fr.eni.projet.encheres.dal.ConnectionProvider;
import fr.eni.projet.encheres.dal.DALException;
import fr.eni.projet.encheres.dal.DAOArticle;

/**
 * @author William
 *
 */
public class ArticleDAOJdbcImpl extends DAOJdbcImpl<Article> implements DAOArticle {

	String sqlDeleteByID = "delete from ARTICLES where id=?";
	String sqlInsert = "insert into ARTICLES(nomArticle, description, dateDebutEncheres, dateFinEncheres, prixInitial, prixVente, idUtilisateur, idCategorie) values (?,?,?,?,?,?,?,?)";
	String sqlSelectByID = "select nomArticle, description, dateDebutEncheres, dateFinEncheres, prixInitial, prixVente, CATEGORIES.id as idCategorie, libelle, UTILISATEURS.id as idUtilisateur, pseudo, nom, prenom, email, telephone, rue, codePostal, ville from ARTICLES inner join UTILISATEURS on idUtilisateur=Utilisateurs.id inner join CATEGORIES on  idCategorie=Categories.id where Articles.id=?";
	String sqlSelectAll = "select Articles.id as id, nomArticle, description, dateDebutEncheres, dateFinEncheres, prixInitial, prixVente, CATEGORIES.id as idCategorie, libelle, UTILISATEURS.id as idUtilisateur, pseudo, nom, prenom, email, telephone, rue, codePostal, ville from ARTICLES inner join UTILISATEURS on idUtilisateur=Utilisateurs.id inner join CATEGORIES on idCategorie=CATEGORIES.id";
	String sqlUpdate = "update ARTICLES set nomArticle=?, description=?, dateDebutEncheres=?, dateFinEncheres=?, prixInitial=?, prixVente=?, idUtilisateur=?, idCategorie=?, where id=? ";
	String sqlTruncate = "truncate table ARTICLES";

	String sqlByEnchereEnCours = "DATEDIFF(day,Articles.dateDebutEncheres, CURRENT_TIMESTAMP)>=0 and DATEDIFF(day, CURRENT_TIMESTAMP,Articles.dateFinEncheres)>0";
	String sqlByUtilisateur = "UTILISATEURS.id=?";
	String sqlByMotClef = "nomArticle like ?";
	String sqlByCategorie = "CATEGORIES.id=?";

	String sqlJoinEnchere = "INNER JOIN ENCHERES on ENCHERES.idArticle = ARTICLES.id";
	String sqlByAcheteur = "and ENCHERES.idUtilisateur= ? ";
	private String sqlJoinMax = "inner join (Select Encheres.idArticle, MAX(ENCHERES.montantEnchere) as prixMax from ENCHERES group by idArticle) encMax on ENCHERES.montantEnchere=prixMax";
	private String sqlByEnchereTermine = "DATEDIFF(day, CURRENT_TIMESTAMP,Articles.dateFinEncheres)<0";
	private String sqlByPasCommence = " DATEDIFF(day,Articles.dateDebutEncheres, CURRENT_TIMESTAMP)<0 ";

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
		Adresse adr = genererAdresse(rs);

		// génération du vendeur
		Vendeur vendeur = genererVendeur(rs, adr);

		// génération de la Categorie
		Categorie cat = genererCategorie(rs);


		// génération de l'article
		Article a = genererArticle(rs, vendeur, cat);

		

		return a;

	}

	/**
	 * @param rs
	 * @param vendeur
	 * @param cat
	 * @return
	 * @throws SQLException
	 */
	public Article genererArticle(ResultSet rs, Vendeur vendeur, Categorie cat) throws SQLException {
		Article a = new Article();

		a.setId(rs.getInt("id"));
		a.setNomArticle(rs.getString("nomArticle"));
		a.setDescription(rs.getString("description"));
		a.setDateDebut(rs.getDate("dateDebutEncheres"));
		a.setDateFin(rs.getDate("dateFinEncheres"));
		a.setPrixInitial(rs.getInt("prixInitial"));
		a.setPrixVente(rs.getInt("prixVente"));
		a.setCategorie(cat);
		a.setUtilisateur(vendeur);
		return a;
	}

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public Categorie genererCategorie(ResultSet rs) throws SQLException {
		Categorie cat = new Categorie();
		cat.setId(rs.getInt("idCategorie"));
		cat.setNom(rs.getString("libelle"));
		return cat;
	}

	/**
	 * @param rs
	 * @param adr
	 * @return
	 * @throws SQLException
	 */
	public Vendeur genererVendeur(ResultSet rs, Adresse adr) throws SQLException {
		Vendeur vendeur = new Vendeur();

		vendeur.setId(rs.getInt("idUtilisateur"));
		vendeur.setPseudo(rs.getString("pseudo"));
		vendeur.setNom(rs.getString("nom"));
		vendeur.setPrenom(rs.getString("prenom"));
		vendeur.setEmail(rs.getString("email"));
		vendeur.setTelephone(rs.getString("telephone"));
		vendeur.setAdresse(adr);
		return vendeur;
	}

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public Adresse genererAdresse(ResultSet rs) throws SQLException {
		Adresse adr = new Adresse();

		adr.setRue(rs.getString("rue"));
		adr.setCodePostal(rs.getString("codePostal"));
		adr.setVille("ville");
		return adr;
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

	// Requetes Select permettant de récupérer la liste des Articles dont l'enchère
	// est en cours

	@Override
	public List<Article> selectBy() throws DALException {
		String sql = sqlSelectAll + " WHERE " + sqlByEnchereEnCours;
		List<Article> liste = new ArrayList<Article>();
		Article art = null;
		try (Connection con = ConnectionProvider.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {

			while (rs.next()) {

				art = createFromRS(rs);
				//Recuperer Valeur Enchere Max sur l'Article
				try {
					EnchereManager EncMan = new EnchereManager();
					art.setListeEnchere(EncMan.getEncheresArticle(art));
					
				} catch (BLLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				liste.add(art);
			}
		} catch (SQLException e) {
			throw new DALException("erreur de requete Select All", e);
		}
		return liste;
	}

	// Ajouter une string à la requete permet de chercher par mot clef

	@Override
	public List<Article> selectBy(String motClef) throws DALException {
		String sql = sqlSelectAll + " WHERE " + sqlByEnchereEnCours + " AND " + sqlByMotClef;
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

	// Ajouter une Categorie à la requete permet de chercher par Categorie
	@Override
	public List<Article> selectBy(Categorie cat) throws DALException {
		String sql = sqlSelectAll + " WHERE " + sqlByEnchereEnCours + " AND " + sqlByCategorie;
		List<Article> liste = new ArrayList<Article>();
		Article a = null;
		try (Connection con = ConnectionProvider.getConnection(); PreparedStatement stmt = con.prepareStatement(sql);) {
			stmt.setInt(1, cat.getId());
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

	// Double filtre de recherche

	@Override
	public List<Article> selectBy(Categorie cat, String motClef) throws DALException {
		String sql = sqlSelectAll + " WHERE " + sqlByEnchereEnCours + "AND" + sqlByCategorie + " AND " + sqlByMotClef;
		List<Article> liste = new ArrayList<Article>();
		Article a = null;
		try (Connection con = ConnectionProvider.getConnection(); PreparedStatement stmt = con.prepareStatement(sql);) {
			stmt.setInt(1, cat.getId());
			stmt.setString(2, motClef);
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

	// Si on veut récup la liste des Articles en Vente actuellement proposés par un
	// Vendeur précis

	@Override
	public List<Article> selectBy(Vendeur ven) throws DALException {
		String sql = sqlSelectAll + " WHERE " + sqlByEnchereEnCours + "AND" + sqlByUtilisateur;
		List<Article> liste = new ArrayList<Article>();
		Article a = null;
		try (Connection con = ConnectionProvider.getConnection(); PreparedStatement stmt = con.prepareStatement(sql);) {

			stmt.setInt(1, ven.getId());
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

	// TODO des variantes avec plus de filtres

	@Override
	public List<Article> selectBy(Categorie cat, String mot, Vendeur ven) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> selectBy(Categorie cat, Vendeur ven) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> selectBy(String mot, Vendeur ven) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	// obtenir uniquement les articles mis en vente par un Utilisateur précis

	@Override
	public List<Article> selectByUtilisateur(Vendeur ven) throws DALException {
		String sql = sqlSelectAll + " WHERE " + sqlByUtilisateur;
		List<Article> liste = new ArrayList<Article>();
		Article a = null;
		try (Connection con = ConnectionProvider.getConnection(); PreparedStatement stmt = con.prepareStatement(sql);) {
			stmt.setInt(1, ven.getId());
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

	// Liste des Articles dont l'enchère n'est pas encore ouverte et dont le vendeur
	// est l'utilisateur
	public List<Article> selectByFuturEnc(Vendeur ven) throws DALException {
		String sql = sqlSelectAll + " WHERE " + sqlByUtilisateur + " AND " + sqlByPasCommence;
		List<Article> liste = new ArrayList<Article>();
		Article a = null;
		try (Connection con = ConnectionProvider.getConnection(); PreparedStatement stmt = con.prepareStatement(sql);) {
			stmt.setInt(1, ven.getId());
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

	// Liste des Articles dont l'enchère est terminée et dont le vendeur est
	// l'utilisateur
	public List<Article> selectByPasseEnc(Vendeur ven) throws DALException {
		String sql = sqlSelectAll + " WHERE " + sqlByUtilisateur + " AND " + sqlByEnchereTermine;
		List<Article> liste = new ArrayList<Article>();
		Article a = null;
		try (Connection con = ConnectionProvider.getConnection(); PreparedStatement stmt = con.prepareStatement(sql);) {
			stmt.setInt(1, ven.getId());
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

	// Liste des Articles dont l'enchère est ouverte sur lesquels un Utilisateur a
	// placé une enchère

	public List<Article> selectByAcheteur(Vendeur ach) throws DALException {
		String sql = sqlSelectAll + sqlJoinEnchere + " WHERE " + sqlByEnchereEnCours + sqlByAcheteur;

		List<Article> liste = new ArrayList<Article>();
		Article a = null;
		try (Connection con = ConnectionProvider.getConnection(); PreparedStatement stmt = con.prepareStatement(sql);) {
			stmt.setInt(1, ach.getId());
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

	// Liste des Articles dont l'enchère est terminée sur lesquels un Utilisateur a
	// placé la plus grosse enchère

	public List<Article> selectAcquis(Vendeur ach) throws DALException {

		String sql = sqlSelectAll + sqlJoinEnchere + sqlJoinMax + " WHERE " + sqlByEnchereTermine + sqlByAcheteur;

		List<Article> liste = new ArrayList<Article>();
		Article a = null;
		try (Connection con = ConnectionProvider.getConnection(); PreparedStatement stmt = con.prepareStatement(sql);) {
			stmt.setInt(1, ach.getId());
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

}
