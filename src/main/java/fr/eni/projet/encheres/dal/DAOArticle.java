package fr.eni.projet.encheres.dal;

import java.util.List;

import fr.eni.projet.encheres.bo.Article;

public interface DAOArticle extends DAO<Article> {

	public List<Article> selectByEnchereEnCoursByMotClef(String motClef) throws DALException;

	public List<Article> selectByEnchereEnCoursByCategorie(int idCategorie) throws DALException;

	public List<Article> selectByUtilisateur(int idUtilisateur) throws DALException;
	
	public List<Article> selectByEnchereEnCours() throws DALException;
	
	public List<Article> selectByEnchereEnCoursByCategorieByMotClef(int idCategorie, String motClef) throws DALException;

}
