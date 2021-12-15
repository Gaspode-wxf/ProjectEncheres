package fr.eni.projet.encheres.dal;

import java.util.List;

import fr.eni.projet.encheres.bo.Categorie;

/**
 * @author Alexandre Mchich
 *
 */
public interface DAOCategorie extends DAO<Categorie> {

	public List<Categorie> selectByNom(String nom) throws DALException;
	
}
