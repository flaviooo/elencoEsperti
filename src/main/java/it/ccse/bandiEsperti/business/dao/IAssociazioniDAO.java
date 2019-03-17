package it.ccse.bandiEsperti.business.dao;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import it.ccse.bandiEsperti.business.model.Associazioni;
import it.ccse.bandiEsperti.business.model.Delibera;
import it.ccse.bandiEsperti.business.model.Esperto;
import it.ccse.bandiEsperti.business.model.Tema;

public interface IAssociazioniDAO {
	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#persist(it.ccse.bandiEsperti.business.model.TipoLaurea)
	 */
	public abstract void persist(Associazioni transientInstance);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#remove(it.ccse.bandiEsperti.business.model.TipoLaurea)
	 */
	public abstract void remove(Associazioni persistentInstance);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#merge(it.ccse.bandiEsperti.business.model.TipoLaurea)
	 */
	public abstract Associazioni merge(Associazioni detachedInstance);

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IProvincieDAO#findById(java.lang.Integer)
	 */
	public abstract Associazioni findById(Integer id);

	public abstract Set<Associazioni> findAll() throws Exception;
	public abstract Integer save(Associazioni detachedInstance) throws Exception;

	public abstract void delete(Integer id);
	
	public abstract void merge(Associazioni Associazioni, Session session) throws Exception;

	public abstract void delete(int id) throws Exception;

	public abstract  int delete(int idEsperto, int idDelibera) throws Exception;

	int delete(Esperto esperto, Delibera delibera) throws Exception;

	Set<Associazioni> ricerca(List<Esperto> listEsperto, List<Tema> listTema,
			List<Delibera> listDelibera) throws Exception;

	
	
}
