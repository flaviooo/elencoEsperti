package it.ccse.bandiEsperti.business.dao;

import it.ccse.bandiEsperti.business.model.Citta;

import java.util.Set;

public interface ICittaDAO {

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#persist(it.ccse.bandiEsperti.business.model.Citta)
	 */
	public abstract void persist(Citta transientInstance);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#remove(it.ccse.bandiEsperti.business.model.Citta)
	 */
	public abstract void remove(Citta persistentInstance);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#merge(it.ccse.bandiEsperti.business.model.Citta)
	 */
	public abstract Citta merge(Citta detachedInstance);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#findById(java.lang.Integer)
	 */
	public abstract Citta findById(Integer id);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#findByCodiceRegione(java.lang.String)
	 */
	public abstract Set<Citta> findByCodiceRegione(String codiceRegione)
			throws Exception;

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#findByCodiceProvincia(java.lang.String)
	 */
	public abstract Set<Citta> findByCodiceProvincia(String codiceProvincia) throws Exception;

	public abstract Set<Citta> findByCodiceCittaMetropolitana(String codiceCittaMetropolitana) 
			throws Exception; 
	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#findAll()
	 */
	public abstract Set<Citta> findAll() throws Exception;

}