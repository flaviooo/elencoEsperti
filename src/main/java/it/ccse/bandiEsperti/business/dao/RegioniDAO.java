package it.ccse.bandiEsperti.business.dao;

// Generated 3-giu-2015 12.01.14 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import it.ccse.bandiEsperti.business.model.Paesi;
import it.ccse.bandiEsperti.business.model.Regioni;

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
 * Home object for domain model class Regioni.
 * @see it.ccse.bandiEsperti.business.dao.Regioni
 * @author Hibernate Tools
 */

public class RegioniDAO  extends BaseDAO implements IRegioniDAO {

	private static final Log log = LogFactory.getLog(RegioniDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IRegioniDAO#persist(it.ccse.bandiEsperti.business.model.Regioni)
	 */
	@Override
	public void persist(Regioni transientInstance) {
		log.debug("persisting Regioni instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IRegioniDAO#remove(it.ccse.bandiEsperti.business.model.Regioni)
	 */
	@Override
	public void remove(Regioni persistentInstance) {
		log.debug("removing Regioni instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IRegioniDAO#merge(it.ccse.bandiEsperti.business.model.Regioni)
	 */
	@Override
	public Regioni merge(Regioni detachedInstance) {
		log.debug("merging Regioni instance");
		try {
			Regioni result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IRegioniDAO#findById(java.lang.Integer)
	 */
	@Override
	public Regioni findById(Integer id) {
		log.debug("getting Regioni instance with id: " + id);
		try {
			Regioni instance = (Regioni)HibernateUtil.getSessionFactory().openSession().get(Regioni.class, id);
			
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IRegioniDAO#findAll()
	 */
	
	
	@Override
	@SuppressWarnings("unchecked")
	public Set<Regioni> findAll() throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Regioni.class);
		Set<Regioni> listaRegioni= new HashSet<Regioni>(criteria.list());
		t.commit();
		session.close();
		return listaRegioni;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Regioni findByCodiceRegione(String codiceRegione) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Regioni.class);
		criteria.add(Restrictions.eq("codiceRegione", codiceRegione));
		Regioni Regione =(Regioni) criteria.uniqueResult();
		t.commit();
		session.close();
		return Regione;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public Set<Regioni> listaRegione(Paesi paesi) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Regioni.class);
		criteria.add(Restrictions.eq("paesi", paesi));
		Set<Regioni> listaRegioni= new HashSet<Regioni>(criteria.list());
		t.commit();
		session.close();
		return listaRegioni;
	}
}
