package it.ccse.bandiEsperti.business.dao;
import java.util.Set;
import org.hibernate.Session;
import it.ccse.bandiEsperti.business.model.DatoreDiLavoro;
public interface IDatoreDiLavoroDAO {
	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#persist(it.ccse.bandiEsperti.business.model.TipoLaurea)
	 */
	public abstract void persist(DatoreDiLavoro transientInstance);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#remove(it.ccse.bandiEsperti.business.model.TipoLaurea)
	 */
	public abstract void remove(DatoreDiLavoro persistentInstance);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#merge(it.ccse.bandiEsperti.business.model.TipoLaurea)
	 */
	public abstract DatoreDiLavoro merge(DatoreDiLavoro detachedInstance) throws Exception;

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#findById(java.lang.Integer)
	 */
	public abstract DatoreDiLavoro findById(Integer id);

	public abstract Set<DatoreDiLavoro> findAll() throws Exception;
	
	public abstract Integer save(DatoreDiLavoro detachedInstance) throws Exception;

	public abstract void merge(DatoreDiLavoro element, Session session) throws Exception;

	public abstract void delete(int id);
}
