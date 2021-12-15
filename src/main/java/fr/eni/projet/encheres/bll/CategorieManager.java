package fr.eni.projet.encheres.bll;

import java.util.ArrayList;
import java.util.List;

import fr.eni.projet.encheres.bo.Categorie;
import fr.eni.projet.encheres.dal.DALException;
import fr.eni.projet.encheres.dal.DAO;
import fr.eni.projet.encheres.dal.DAOCategorie;
import fr.eni.projet.encheres.dal.DAOFactory;

/**
 * @author Alexandre Mchich
 *
 */
public class CategorieManager implements AbstractManager<Categorie> {

	private DAO<Categorie> daoCategorie;
	private List<Categorie> catalogue = new ArrayList<Categorie>();

	public CategorieManager() throws BLLException {
		System.out.println("init CategorieManager");
		this.daoCategorie = DAOFactory.getCategorieDAO();
	}

	public List<Categorie> getCatalogue() throws BLLException {

		try {
			this.catalogue = daoCategorie.selectAll();
		} catch (DALException e) {
			throw new BLLException("echec accès catalogue", e);
		}
		return catalogue;

	}

	public void addItem(Categorie categorie) throws BLLException {

		if (categorie.getId() != null) {
			throw new BLLException("categorie déjà catalogué");
		}
		validerItem(categorie);
		try {
			this.daoCategorie.insert(categorie);
		} catch (DALException e) {
			throw new BLLException("echec insertion categorie", e);
		}

	}

	public void updateItem(Categorie categorie) throws BLLException {

		validerItem(categorie);
		try {

			this.daoCategorie.update(categorie);
		} catch (DALException e) {
			throw new BLLException("echec mise à jour categorie", e);
		}

	}

	public void removeItem(Categorie categorie) throws BLLException {

		try {
			this.daoCategorie.delete(categorie);
		} catch (DALException e) {
			e.printStackTrace();
		}

	}

	public void validerItem(Categorie categorie) throws BLLException {
		// à définir en fonction de l'objet sécurité supplémentaire aux Check SQL
	}

	public Categorie getItem(int index) throws BLLException {
		Categorie categorie = null;

		try {
			categorie = this.daoCategorie.selectByID(index);
		} catch (DALException e) {
			throw new BLLException("echec récupération categorie", e);
		}

		return categorie;

	}
	public Categorie getCategorieViaNom(String nom) throws BLLException {
		Categorie c = null;

		try {
			c = ((DAOCategorie) this.daoCategorie).selectByNom(nom);
		} catch (DALException e) {
			System.err.println("echec récupération Categorie");
			throw new BLLException("echec récupération Categorie", e);
		}

		return c;
		
	}
	public void nettoyerBDD() throws BLLException {
		try {
			this.daoCategorie.deleteAll();
		} catch (DALException e) {
			e.printStackTrace();
		}
	}
}