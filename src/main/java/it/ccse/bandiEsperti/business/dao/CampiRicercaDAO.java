package  it.ccse.bandiEsperti.business.dao;

// Generated 3-giu-2015 12.01.14 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import it.ccse.bandiEsperti.business.model.CampiRicerca;


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

public class CampiRicercaDAO extends BaseDAO implements ICampiRicercaDAO{

	private static final Log log = LogFactory.getLog(CampiRicercaDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(CampiRicerca transientInstance) {
		log.debug("persisting TipoLaurea instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(CampiRicerca persistentInstance) {
		log.debug("removing TipoLaurea instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public CampiRicerca merge(CampiRicerca detachedInstance) {
		log.debug("merging TipoLaurea instance");
		try {
			CampiRicerca result = (CampiRicerca)HibernateUtil.getSessionFactory().openSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public CampiRicerca findById(Integer id) {
		log.debug("getting TipoLaurea instance with id: " + id);
		try {
			CampiRicerca instance = (CampiRicerca)HibernateUtil.getSessionFactory().openSession().get(CampiRicerca.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	public Set<CampiRicerca> findAll() throws Exception {
		Session session = null;
        Transaction transation = null;
        Set<CampiRicerca> listaCampiRicerca = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transation = session.beginTransaction();
			Criteria criteria = session.createCriteria(CampiRicerca.class);
			listaCampiRicerca= new HashSet<CampiRicerca>(criteria.list());
		} finally {
			 transation.commit();
			 session.close();
		}
		return listaCampiRicerca;
	}
	
	
}
