package it.ccse.bandiEsperti.business.dao;
import java.io.Serializable;
import java.util.Set;

import org.hibernate.Session;

import it.ccse.bandiEsperti.business.model.Delibera;

public interface IDeliberaDAO {
	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#persist(it.ccse.bandiEsperti.business.model.TipoLaurea)
	 */
	public abstract void persist(Delibera transientInstance);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#remove(it.ccse.bandiEsperti.business.model.TipoLaurea)
	 */
	public abstract void remove(Delibera persistentInstance);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#merge(it.ccse.bandiEsperti.business.model.TipoLaurea)
	 */
	public abstract Delibera merge(Delibera detachedInstance);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#findById(java.lang.Integer)
	 */
	public abstract Delibera findById(Integer id);

	public abstract Set<Delibera> findAll() throws Exception;
	public abstract Integer save(Serializable detachedInstance) throws Exception;

	public abstract void delete(Integer id);
	
	public abstract void merge(Delibera delibera, Session session) throws Exception; 

	
	
}
