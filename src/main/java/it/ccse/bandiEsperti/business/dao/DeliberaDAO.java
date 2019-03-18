package  it.ccse.bandiEsperti.business.dao;

// Generated 3-giu-2015 12.01.14 by Hibernate Tools 3.4.0.CR1




import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import it.ccse.bandiEsperti.business.model.Delibera;
import it.ccse.bandiEsperti.business.model.Tema;

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
 * Home object for domain model class TipoLaurea.
 * @see it.ccse.bandiEsperti.business.dao.TipoLaurea
 * @author Hibernate Tools
 */

public class DeliberaDAO extends BaseDAO implements IDeliberaDAO{

	private static final Log log = LogFactory.getLog(DeliberaDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Delibera transientInstance) {
		log.debug("persisting TipoLaurea instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Delibera persistentInstance) {
		log.debug("removing TipoLaurea instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Delibera merge(Delibera detachedInstance) {
		log.debug("merging TipoLaurea instance");
		try {
			Delibera result = (Delibera)HibernateUtil.getSessionFactory().openSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public Delibera findById(Integer id) {
		log.debug("getting TipoLaurea instance with id: " + id);
		try {
			Criteria criteria = HibernateUtil.getSessionFactory().openSession().createCriteria(Delibera.class);
			criteria.add(Restrictions.eq("id", id));
			Delibera instance = (Delibera) criteria.uniqueResult();
			
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	
	@Override
	@SuppressWarnings("unchecked")
	public Set<Delibera> findAll() throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Delibera.class);
		Set<Delibera> listaDelibera =new HashSet<Delibera>(criteria.list());
		t.commit();
		session.close();
		return listaDelibera;
	}

	public Integer save(Serializable element) throws Exception {
		log.debug("save Delibera instance");
		Integer id = null;
		 Transaction t = null;
		 Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
	        t = session.beginTransaction();
	        id = (Integer)session.save(element);
				
		} finally {
			t.commit();
			session.close();	
		}
		return id;
	}
	
	@Override
	public void delete(Integer id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Delibera e = (Delibera) session.load(Delibera.class, id);
		session.delete(e);
		t.commit();
		session.close();
	}
	
	/** Merge con commit */
	@Override
	public void merge(Delibera element, Session session) throws Exception {
	    @SuppressWarnings("unused")
	    Delibera i2 = (Delibera) session.get(Delibera.class, new Integer(element.getId()));
		i2 = (Delibera) session.merge(element); 
	}
	

}
