package fr.eni.projet.encheres.dal;

import java.util.List;

import fr.eni.projet.encheres.bo.Categorie;

/**
 * @author William "Gaspode" Freyer
 *
 */
public interface DAO<T> {

	public List<T> selectAll() throws DALException;

	public T selectByID(Integer id) throws DALException;

	public void update(T t) throws DALException;

	public void deleteByID(Integer id) throws DALException;

	public void delete(T t) throws DALException;

	public void insert(T t) throws DALException;

	public void deleteAll() throws DALException;

	

}
