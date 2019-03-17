package  it.ccse.bandiEsperti.business.dao;

// Generated 3-giu-2015 12.01.14 by Hibernate Tools 3.4.0.CR1




import java.util.HashSet;
import java.util.Set;

import it.ccse.bandiEsperti.business.model.TipoLaurea;
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

public class TipoLaureaDAO extends BaseDAO implements ITipoLaureaDAO{

	private static final Log log = LogFactory.getLog(TipoLaureaDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(TipoLaurea transientInstance) {
		log.debug("persisting TipoLaurea instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TipoLaurea persistentInstance) {
		log.debug("removing TipoLaurea instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public TipoLaurea merge(TipoLaurea detachedInstance) {
		log.debug("merging TipoLaurea instance");
		try {
			TipoLaurea result = (TipoLaurea)HibernateUtil.getSessionFactory().openSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public TipoLaurea findById(Integer id) {
		log.debug("getting TipoLaurea instance with id: " + id);
		try {
			TipoLaurea instance = (TipoLaurea)HibernateUtil.getSessionFactory().openSession().get(TipoLaurea.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Set<TipoLaurea> findAll() throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(TipoLaurea.class);
		Set<TipoLaurea> listaTipoLaurea =new HashSet<TipoLaurea>(criteria.list());
		t.commit();
		session.close();
		return listaTipoLaurea;
	}
}
