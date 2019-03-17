package  it.ccse.bandiEsperti.business.dao;

// Generated 3-giu-2015 12.01.14 by Hibernate Tools 3.4.0.CR1




import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import it.ccse.bandiEsperti.business.model.Atenei;
import it.ccse.bandiEsperti.business.model.Citta;
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
 * Home object for domain model class Ateneo.
 * @see it.ccse.bandiEsperti.business.dao.Ateneo
 * @author Hibernate Tools
 */

public class AteneoDAO extends BaseDAO implements IAteneoDAO{

	private static final Log log = LogFactory.getLog(AteneoDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void persist(Atenei transientInstance) {
		log.debug("persisting Atenei instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}
	
	@Override
	public void remove(Atenei persistentInstance) {
		log.debug("removing Atenei instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}
	
	/** Merge con commit */
	@Override
	public void merge(Atenei element, Session session) throws Exception {
	    @SuppressWarnings("unused")
	    Atenei i2 = (Atenei) session.get(Atenei.class, new Integer(element.getId()));
		i2 = (Atenei) session.merge(element); 
	}
	
	@Override
	public Atenei findById(Integer id) {
		log.debug("getting Paesi instance with id: " + id);
		try {
			Atenei instance = (Atenei)HibernateUtil.getSessionFactory().openSession().get(Atenei.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	public Set<Atenei> findAll() throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Atenei.class);
		Set<Atenei> listaAteneo =new HashSet<Atenei>(criteria.list());
		t.commit();
		session.close();
		return listaAteneo;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Set<Atenei> findByCitta(Citta citta) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Atenei.class);
		criteria.add(Restrictions.eq("citta", citta));
		Set<Atenei> listaAtenei =new HashSet<Atenei>(criteria.list());
		t.commit();
		session.close();
		return listaAtenei;
	}

	
	public Integer save(Serializable element) throws Exception{
		log.debug("save Ateneo instance");
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
		Atenei e = (Atenei) session.load(Atenei.class, id);
		session.delete(e);
		t.commit();
		session.close();
	}
	
}
