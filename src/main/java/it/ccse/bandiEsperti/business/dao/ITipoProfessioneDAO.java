package it.ccse.bandiEsperti.business.dao;
import java.util.Set;

import it.ccse.bandiEsperti.business.model.TipoProfessione;

public interface ITipoProfessioneDAO {
	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#persist(it.ccse.bandiEsperti.business.model.TipoLaurea)
	 */
	public abstract void persist(TipoProfessione transientInstance);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#remove(it.ccse.bandiEsperti.business.model.TipoLaurea)
	 */
	public abstract void remove(TipoProfessione persistentInstance);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#merge(it.ccse.bandiEsperti.business.model.TipoLaurea)
	 */
	public abstract TipoProfessione merge(TipoProfessione detachedInstance);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#findById(java.lang.Integer)
	 */
	public abstract TipoProfessione findById(Integer id);

	public abstract Set<TipoProfessione> findAll() throws Exception;
}
