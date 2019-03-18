package it.ccse.bandiEsperti.business.dao;
import java.util.Set;

import it.ccse.bandiEsperti.business.model.TipoLaurea;

public interface ITipoLaureaDAO {
	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#persist(it.ccse.bandiEsperti.business.model.TipoLaurea)
	 */
	public abstract void persist(TipoLaurea transientInstance);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#remove(it.ccse.bandiEsperti.business.model.TipoLaurea)
	 */
	public abstract void remove(TipoLaurea persistentInstance);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#merge(it.ccse.bandiEsperti.business.model.TipoLaurea)
	 */
	public abstract TipoLaurea merge(TipoLaurea detachedInstance);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#findById(java.lang.Integer)
	 */
	public abstract TipoLaurea findById(Integer id);

	public abstract Set<TipoLaurea> findAll() throws Exception;
}
