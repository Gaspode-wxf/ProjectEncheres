package fr.eni.projet.encheres.dal;

public class DALException extends Exception {
	/**
	 * @author William "Gaspode" Freyer
	 *
	 */
	private static final long serialVersionUID = 1L;

	public DALException() {
		super();
	}

	public DALException(String message) {
		super(message);
	}

	public DALException(String message, Throwable exception) {
		super(message, exception);
	}

	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer("Couche DAL - ");
		sb.append(super.getMessage());

		return sb.toString();
	}

}
