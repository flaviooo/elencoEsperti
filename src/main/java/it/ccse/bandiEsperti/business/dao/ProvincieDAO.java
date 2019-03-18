package it.ccse.bandiEsperti.business.dao;

// Generated 3-giu-2015 12.01.14 by Hibernate Tools 3.4.0.CR1



import java.util.HashSet;
import java.util.Set;

import it.ccse.bandiEsperti.business.model.Provincie;
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
 * Home object for domain model class Provincie.
 * @see it.ccse.bandiEsperti.business.dao.Provincie
 * @author Hibernate Tools
 */

public class ProvincieDAO extends BaseDAO implements IProvincieDAO{

	private static final Log log = LogFactory.getLog(ProvincieDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Provincie transientInstance) {
		log.debug("persisting Provincie instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Provincie persistentInstance) {
		log.debug("removing Provincie instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Provincie merge(Provincie detachedInstance) {
		log.debug("merging Provincie instance");
		try {
			Provincie result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Provincie findById(Integer id) {
		log.debug("getting Provincie instance with id: " + id);
		try {
			Provincie instance = (Provincie)HibernateUtil.getSessionFactory().openSession().get(Provincie.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Set<Provincie> findByCodiceRegione(String codiceRegione) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Provincie.class);
		
		criteria.createAlias("regioni", "reg").add(Restrictions.eq("reg.codiceRegione", codiceRegione));
		Set<Provincie> listaProvincie =new HashSet<Provincie>(criteria.list());
		t.commit();
		session.close();
		return listaProvincie;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Provincie findByCodiceProvincia(String codiceProvincia) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Provincie.class);
		criteria.add(Restrictions.eq("codiceProvincia", codiceProvincia));
		Provincie provincia =(Provincie) criteria.uniqueResult();
		t.commit();
		session.close();
		return provincia;
	}
	
	
	
	@Override
	@SuppressWarnings("unchecked")
	public Set<Provincie> findAll() throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Provincie.class);
		Set<Provincie> listaProvincie =new HashSet<Provincie>(criteria.list());
		t.commit();
		session.close();
		return listaProvincie;
	}

	
}
