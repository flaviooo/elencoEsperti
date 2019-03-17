package  it.ccse.bandiEsperti.business.dao;

// Generated 3-giu-2015 12.01.14 by Hibernate Tools 3.4.0.CR1




import java.util.HashSet;
import java.util.Set;

import it.ccse.bandiEsperti.business.model.TipoProfessione;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.zkplus.hibernate.HibernateUtil;

/**
 * Home object for domain model class TipoLaurea.
 * @see it.ccse.bandiEsperti.business.dao.TipoLaurea
 * @author Hibernate Tools
 */

public class TipoProfessioneDAO extends BaseDAO implements ITipoProfessioneDAO{

	private static final Log log = LogFactory.getLog(TipoProfessioneDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(TipoProfessione transientInstance) {
		log.debug("persisting TipoLaurea instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TipoProfessione persistentInstance) {
		log.debug("removing TipoLaurea instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public TipoProfessione merge(TipoProfessione detachedInstance) {
		log.debug("merging TipoLaurea instance");
		try {
			TipoProfessione result = (TipoProfessione)HibernateUtil.getSessionFactory().openSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public TipoProfessione findById(Integer id) {
		log.debug("getting TipoLaurea instance with id: " + id);
		try {
			TipoProfessione instance = (TipoProfessione)HibernateUtil.getSessionFactory().openSession().get(TipoProfessione.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Set<TipoProfessione> findAll() throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(TipoProfessione.class);
		Set<TipoProfessione> listaTipoProfessione =new HashSet<TipoProfessione>(criteria.list());
		t.commit();
		session.close();
		return listaTipoProfessione;
	}
}
