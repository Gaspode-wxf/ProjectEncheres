package fr.eni.projet.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import fr.eni.projet.encheres.bo.Adresse;
import fr.eni.projet.encheres.bo.Article;
import fr.eni.projet.encheres.bo.Categorie;
import fr.eni.projet.encheres.bo.Retrait;
import fr.eni.projet.encheres.bo.user.Vendeur;
import fr.eni.projet.encheres.dal.ConnectionProvider;
import fr.eni.projet.encheres.dal.DALException;
import fr.eni.projet.encheres.dal.DAORetrait;

/**
 * @author William "Gaspode" Freyer
 *
 */
public class RetraitDAOJdbcImpl extends DAOJdbcImpl<Retrait> implements DAORetrait {

	String sqlDeleteByID = "delete from Retraits where idArticle=?";
	String sqlInsert = "insert into Retraits values (?, ?, ?, ?)";
	String sqlSelectByID = "select idArticle, nomArticle, description, dateDebutEncheres, "
			+ "dateFinEncheres, prixInitial, prixVente,"
			+ "idCategorie, libelle, "
			+ "idUtilisateur, pseudo, nom, prenom, email, telephone, rue, codePostal, "
			+ "rue as rueRet, CodePostal as cpRet, ville as villeRet "
			+ "from RETRAITS"
			+ "INNER JOIN ARTICLES on idArticle=id "
			+ "INNER JOIN CATEGORIES on idCategorie = CATEGORIE.id "
			+ " INNER JOIN UTILISATEURS on idUtilisateur = UTILISATEURS.id"
			+ " WHERE idArticle=?";
	String sqlSelectAll = "select idArticle, nomArticle, description, dateDebutEncheres, "
			+ "dateFinEncheres, prixInitial, prixVente,"
			+ "idCategorie, libelle, "
			+ "idUtilisateur, pseudo, nom, prenom, email, telephone, rue, codePostal, "
			+ "rue as rueRet, CodePostal as cpRet, ville as villeRet "
			+ "from RETRAITS"
			+ "INNER JOIN ARTICLES on idArticle=id "
			+ "INNER JOIN CATEGORIES on idCategorie = CATEGORIE.id "
			+ " INNER JOIN UTILISATEURS on idUtilisateur = UTILISATEURS.id";
	String sqlUpdate = "update Retraits set rue=?, CodePostal=?, ville=? where idArticle=? ";
	String sqlTruncate = "truncate table Retraits";

	public RetraitDAOJdbcImpl() {
		setSqlDeleteByID(sqlDeleteByID);
		setSqlSelectAll(sqlSelectAll);
		setSqlSelectByID(sqlSelectByID);
		setSqlTruncate(sqlTruncate);
		setSqlUpdate(sqlUpdate);
	}

	@Override
	public void completeStmt(Retrait ret, PreparedStatement stmt) throws SQLException {

/*
 * Ordre des paramètres idArticle, rue, CodePostal, Ville		
 */
		Article art = ret.getArticle();
		Adresse adr = ret.getAdresse();
		int index = 1;
		int typeNbr = stmt.getParameterMetaData().getParameterType(index);
		
		if(typeNbr == Types.INTEGER) {
			//si on entre dans cette boucle ça signifie que c'est un insert
			stmt.setInt(index++, art.getId());
		}
			stmt.setString(index++, adr.getRue());
			stmt.setString(index++, adr.getCodePostal());
			stmt.setString(index++, adr.getVille());
		if(typeNbr!=Types.INTEGER) {
			// si on entre ici c'est qu'on est dans un update
			stmt.setInt(index++, art.getId());
		}

	}

	@Override
	public Retrait createFromRS(ResultSet rs) throws SQLException {
		
		
		// génération de l'adresse du Vendeur

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

		//génération du Retrait : 
				Retrait ret = new Retrait();
				ret.setArticle(art);
		//modification de l'adresse pour qu'elle soit adresse Retrait
				adr.setRue(rs.getString("rueRet"));
				adr.setCodePostal(rs.getString("cpRet"));
				adr.setVille("villeRet");
				ret.setAdresse(adr);

				return ret;
			}

	@Override
	public void delete(Retrait ret) throws DALException {
		this.deleteByID(ret.getIdArticle());

	}

	@Override
	public void insert(Retrait ret) throws DALException {
		String sql = sqlInsert;
		try (Connection con = ConnectionProvider.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				ResultSet rs = null;) {
			completeStmt(ret, stmt);
			stmt.execute();
		} catch (SQLException e) {
			throw new DALException("erreur de requete Insert", e);

		}
	}

}
