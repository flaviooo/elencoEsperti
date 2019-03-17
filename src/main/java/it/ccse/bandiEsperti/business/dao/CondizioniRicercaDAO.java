package  it.ccse.bandiEsperti.business.dao;

// Generated 3-giu-2015 12.01.14 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;

import java.util.Set;
import it.ccse.bandiEsperti.business.model.CondizioniRicerca;
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

public class CondizioniRicercaDAO extends BaseDAO implements ICondizioniRicercaDAO{

	private static final Log log = LogFactory.getLog(CondizioniRicercaDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(CondizioniRicerca transientInstance) {
		log.debug("persisting TipoLaurea instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(CondizioniRicerca persistentInstance) {
		log.debug("removing TipoLaurea instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public CondizioniRicerca merge(CondizioniRicerca detachedInstance) {
		log.debug("merging TipoLaurea instance");
		try {
			CondizioniRicerca result = (CondizioniRicerca)HibernateUtil.getSessionFactory().openSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public CondizioniRicerca findById(Integer id) {
		log.debug("getting TipoLaurea instance with id: " + id);
		try {
			CondizioniRicerca instance = (CondizioniRicerca)HibernateUtil.getSessionFactory().openSession().get(CondizioniRicerca.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	public Set<CondizioniRicerca> findAll() throws Exception {
		Session session = null;
        Transaction transation = null;
        Set<CondizioniRicerca> listaCampiRicerca = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transation = session.beginTransaction();
			Criteria criteria = session.createCriteria(CondizioniRicerca.class);
			listaCampiRicerca= new HashSet<CondizioniRicerca>(criteria.list());
		} finally {
			 transation.commit();
			 session.close();
		}
		return listaCampiRicerca;
	}
	
	
}
