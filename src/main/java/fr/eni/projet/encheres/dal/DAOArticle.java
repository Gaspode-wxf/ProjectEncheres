package fr.eni.projet.encheres.dal;

import java.util.List;

import fr.eni.projet.encheres.bo.Article;
import fr.eni.projet.encheres.bo.Categorie;
import fr.eni.projet.encheres.bo.user.Vendeur;

public interface DAOArticle extends DAO<Article> {

	public List<Article> selectBy() throws DALException;

	public List<Article> selectBy(String motClef) throws DALException;

	public List<Article> selectBy(Categorie cat) throws DALException;

	public List<Article> selectBy(Categorie cat, String motClef) throws DALException;

	public List<Article> selectBy(Vendeur ven) throws DALException;

	public List<Article> selectBy(Categorie cat, String mot, Vendeur ven) throws DALException;

	public List<Article> selectBy(Categorie cat, Vendeur ven) throws DALException;

	public List<Article> selectBy(String mot, Vendeur ven) throws DALException;

	public List<Article> selectByUtilisateur(Vendeur ven) throws DALException;

	public List<Article> selectByFuturEnc(Vendeur ven) throws DALException;

	public List<Article> selectByPasseEnc(Vendeur ven) throws DALException;

	public List<Article> selectByAcheteur(Vendeur ach) throws DALException;

	public List<Article> selectAcquis(Vendeur ach) throws DALException;

}
