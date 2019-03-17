package  it.ccse.bandiEsperti.business.dao;

// Generated 3-giu-2015 12.01.14 by Hibernate Tools 3.4.0.CR1




import java.util.HashSet;
import java.util.Set;

import it.ccse.bandiEsperti.business.model.DatoreDiLavoro;



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

public class DatoreDiLavoroDAO extends BaseDAO implements IDatoreDiLavoroDAO{

	private static final Log log = LogFactory.getLog(DatoreDiLavoroDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(DatoreDiLavoro transientInstance) {
		log.debug("persisting TipoLaurea instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(DatoreDiLavoro persistentInstance) {
		log.debug("removing TipoLaurea instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}


	@Override
	public void delete(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		DatoreDiLavoro e = (DatoreDiLavoro) session.load(DatoreDiLavoro.class, id);
		session.delete(e);
		t.commit();
		session.close();
	}

	
	public Integer save(DatoreDiLavoro element) throws Exception {
		log.debug("save DatoreDiLavoro instance");
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
	
	/** Merge con commit */
	@Override
	public void merge(DatoreDiLavoro element, Session session) throws Exception {
	    @SuppressWarnings("unused")
	    DatoreDiLavoro i2 = (DatoreDiLavoro) session.get(DatoreDiLavoro.class, new Integer(element.getId()));
		i2 = (DatoreDiLavoro) session.merge(element); 
	}
	
	
	public DatoreDiLavoro merge(DatoreDiLavoro detachedInstance) {
		log.debug("merging TipoLaurea instance");
		try {
			DatoreDiLavoro result = (DatoreDiLavoro)HibernateUtil.getSessionFactory().openSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public DatoreDiLavoro findById(Integer id) {
		log.debug("getting TipoLaurea instance with id: " + id);
		try {
			DatoreDiLavoro instance = (DatoreDiLavoro)HibernateUtil.getSessionFactory().openSession().get(DatoreDiLavoro.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Set<DatoreDiLavoro> findAll() throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(DatoreDiLavoro.class);
		Set<DatoreDiLavoro> listaDatoriDiLavoro=new HashSet<DatoreDiLavoro>(criteria.list());
		t.commit();
		session.close();
		return listaDatoriDiLavoro;
	}
}
