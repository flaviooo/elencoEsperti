package it.ccse.bandiEsperti.business.dao;
import java.util.Set;

import it.ccse.bandiEsperti.business.model.CampiRicerca;

public interface ICampiRicercaDAO {
	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.ICampiRicercaDAO#persist(it.ccse.bandiEsperti.business.model.CampiRicerca)
	 */
	public abstract void persist(CampiRicerca transientInstance);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.ICampiRicercaDAO#remove(it.ccse.bandiEsperti.business.model.CampiRicerca)
	 */
	public abstract void remove(CampiRicerca persistentInstance);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.ICampiRicercaDAO#merge(it.ccse.bandiEsperti.business.model.CampiRicerca)
	 */
	public abstract CampiRicerca merge(CampiRicerca detachedInstance);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.ICampiRicercaDAO#findById(java.lang.Integer)
	 */
	public abstract CampiRicerca findById(Integer id);

	public abstract Set<CampiRicerca> findAll() throws Exception;
}
