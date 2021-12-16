package fr.eni.projet.encheres.dal;

import java.util.List;

import fr.eni.projet.encheres.bo.Article;

public interface DAOArticle extends DAO<Article> {

	public List<Article> selectByMotClef(String motClef) throws DALException;

	public List<Article> selectByCategorie(int idCategorie) throws DALException;

	public List<Article> selectByUtilisateur(int idUtilisateur) throws DALException;

}
