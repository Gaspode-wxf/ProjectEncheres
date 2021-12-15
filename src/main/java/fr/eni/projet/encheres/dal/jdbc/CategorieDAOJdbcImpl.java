package fr.eni.projet.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.projet.encheres.bo.Categorie;
import fr.eni.projet.encheres.bo.Enchere;
import fr.eni.projet.encheres.dal.ConnectionProvider;
import fr.eni.projet.encheres.dal.DALException;
import fr.eni.projet.encheres.dal.DAOCategorie;


/**
 * @author Alexandre Mchich
 *
 */
public class CategorieDAOJdbcImpl extends DAOJdbcImpl<Categorie> implements DAOCategorie {

	String sqlDeleteByID = "delete from Categories where id=?";
	String sqlInsert = "insert into Categories(libelle) values (?)";
	String sqlSelectByID = "select id, nom from Categories where id=?";
	String sqlSelectAll = "select id, libelle from Categories";
	String sqlUpdate = "update Categories set id=?, libelle=?, where id=? ";
	String sqlTruncate = "truncate table Categories";

	public CategorieDAOJdbcImpl() {
		System.out.println("init CategorieJdbcImpl");
		afficherTruc();
		setSqlDeleteByID(sqlDeleteByID);
		setSqlSelectAll(sqlSelectAll);
		setSqlSelectByID(sqlSelectByID);
		setSqlTruncate(sqlTruncate);
		setSqlUpdate(sqlUpdate);
	}

	@Override
		public void completeStmt(Categorie c, PreparedStatement stmt) throws SQLException {

			int index = 1;
		
			stmt.setString(index++, c.getNom());
			if(c.getId() != null) {
				stmt.setInt(index++, c.getId());
			}
		}

	@Override
	public Categorie createFromRS(ResultSet rs) throws SQLException {
		
		Categorie categorie = new Categorie();
		categorie.setId(rs.getInt("id"));
		categorie.setNom(rs.getString("libelle"));
		System.out.println(rs.getString("libelle"));
		
//			al.setNom(rs.getString(2));
//			al.setIdRepas(3);
		return categorie;
	}

	@Override
	public void delete(Categorie categorie) throws DALException {
		this.deleteByID(categorie.getId());

	}

	@Override
	public void insert(Categorie categorie) throws DALException {
		String sql = sqlInsert;
		try (Connection con = ConnectionProvider.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				ResultSet rs = null;) {
			completeStmt(categorie, stmt);
			stmt.execute();
			categorie.setId(JdbcTools.recupID(stmt, rs));
		} catch (SQLException e) {
			throw new DALException("erreur de requete Insert", e);

		}
	}

		

	@Override
	public Categorie selectByNom(String libelle) throws DALException {
		String sql = select id, libelle from Categories where libelle like?;
		Categorie c = null;
		try (Connection con = ConnectionProvider.getConnection(); PreparedStatement stmt = con.prepareStatement(sql);) {

			System.out.println(libelle);
			stmt.setString(1, libelle);
			
			try (ResultSet rs = stmt.executeQuery();) {

				rs.next();

				c = createFromRS(rs);
			}

		} catch (SQLException e) {

			throw new DALException("erreur de requete Select by Nom", e);
		}

		return c;
	}

}
