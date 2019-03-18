package it.ccse.bandiEsperti.business.dao;
import java.util.Set;

import it.ccse.bandiEsperti.business.model.CondizioniRicerca;

public interface ICondizioniRicercaDAO {
	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.ICampiRicercaDAO#persist(it.ccse.bandiEsperti.business.model.CampiRicerca)
	 */
	public abstract void persist(CondizioniRicerca transientInstance);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.ICampiRicercaDAO#remove(it.ccse.bandiEsperti.business.model.CampiRicerca)
	 */
	public abstract void remove(CondizioniRicerca persistentInstance);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.ICampiRicercaDAO#merge(it.ccse.bandiEsperti.business.model.CampiRicerca)
	 */
	public abstract CondizioniRicerca merge(CondizioniRicerca detachedInstance);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.ICampiRicercaDAO#findById(java.lang.Integer)
	 */
	public abstract CondizioniRicerca findById(Integer id);

	public abstract Set<CondizioniRicerca> findAll() throws Exception;
}
