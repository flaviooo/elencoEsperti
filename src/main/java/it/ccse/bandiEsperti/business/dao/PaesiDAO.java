package it.ccse.bandiEsperti.business.dao;

// Generated 3-giu-2015 12.01.14 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import it.ccse.bandiEsperti.business.model.Paesi;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.zkoss.zkplus.hibernate.HibernateUtil;

/**
 * Home object for domain model class Paesi.
 * @see it.ccse.bandiEsperti.business.dao.Paesi
 * @author Hibernate Tools
 */

public class PaesiDAO   extends BaseDAO implements IPaesiDAO {

	private static final Log log = LogFactory.getLog(PaesiDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IPaesiDAO#persist(it.ccse.bandiEsperti.business.model.Paesi)
	 */
	@Override
	public void persist(Paesi transientInstance) {
		log.debug("persisting Paesi instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IPaesiDAO#remove(it.ccse.bandiEsperti.business.model.Paesi)
	 */
	@Override
	public void remove(Paesi persistentInstance) {
		log.debug("removing Paesi instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IPaesiDAO#merge(it.ccse.bandiEsperti.business.model.Paesi)
	 */
	@Override
	public Paesi merge(Paesi detachedInstance) {
		log.debug("merging Paesi instance");
		try {
			Paesi result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IPaesiDAO#findById(java.lang.Integer)
	 */
	@Override
	public Paesi findById(Integer id) {
		log.debug("getting Paesi instance with id: " + id);
		try {
			Paesi instance = (Paesi)HibernateUtil.getSessionFactory().openSession().get(Paesi.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	public Paesi findByCodicePaese(String codicePaese) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Paesi.class);
		criteria.add(Restrictions.eq("codicePaese", codicePaese));
		Paesi paese =(Paesi) criteria.uniqueResult();
		t.commit();
		session.close();
		return paese;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Set<Paesi> findAll() throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Paesi.class);
		Set<Paesi> listaPaesi= new HashSet<Paesi>(criteria.list()); ;
		t.commit();
		session.close();
		return listaPaesi;
	}
}
