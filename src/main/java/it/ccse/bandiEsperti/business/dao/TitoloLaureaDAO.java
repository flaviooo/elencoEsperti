package  it.ccse.bandiEsperti.business.dao;

// Generated 3-giu-2015 12.01.14 by Hibernate Tools 3.4.0.CR1




import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



import it.ccse.bandiEsperti.business.model.Istruzione;
import it.ccse.bandiEsperti.business.model.TipoLaurea;
import it.ccse.bandiEsperti.business.model.TitoloLaurea;

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
 * Home object for domain model class TitoloLaurea.
 * @see it.ccse.bandiEsperti.business.dao.TitoloLaurea
 * @author Hibernate Tools
 */

public class TitoloLaureaDAO extends BaseDAO implements ITitoloLaureaDAO, IAdministrationDAO{

	private static final Log log = LogFactory.getLog(TitoloLaureaDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void persist(TitoloLaurea transientInstance) {
		log.debug("persisting TitoloLaurea instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}
	@Override
	public void remove(TitoloLaurea persistentInstance) {
		log.debug("removing TitoloLaurea instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}
	@Override
	public TitoloLaurea merge(TitoloLaurea detachedInstance) {
		log.debug("merging TitoloLaurea instance");
		try {
			TitoloLaurea result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
	
	@Override
	public void merge(TitoloLaurea titoloLaurea, Session session) throws Exception {
		@SuppressWarnings("unused")
		TitoloLaurea i2 = (TitoloLaurea) session.get(TitoloLaurea.class, new Integer(titoloLaurea.getId()));
		i2 = (TitoloLaurea) session.merge(titoloLaurea);
	}
	
	@Override
	public TitoloLaurea findById(Integer id) {
		log.debug("getting TitoloLaurea instance with id: " + id);
		try {
			TitoloLaurea instance = (TitoloLaurea)HibernateUtil.getSessionFactory().openSession().get(TitoloLaurea.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Set<TitoloLaurea> findAll() throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(TitoloLaurea.class);
		Set<TitoloLaurea> listaTitoloLaurea= new HashSet<TitoloLaurea>(criteria.list()); ;
		t.commit();
		session.close();
		return listaTitoloLaurea;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Set<TitoloLaurea> findByTipoLaurea(TipoLaurea tipoLaurea) throws Exception {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(TitoloLaurea.class);
		criteria.add(Restrictions.eq("tipoLaurea", tipoLaurea));
		Set<TitoloLaurea> listaTitoloLaurea =new HashSet<TitoloLaurea>(criteria.list());
		t.commit();
		session.close();
		return listaTitoloLaurea;
	}
	
	
	@Override
	public boolean checkIfExist(Serializable titoloLaurea) throws Exception {
		boolean res=false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Criteria criteria = session.createCriteria(Istruzione.class).add( Restrictions.eq("titoloLaurea", (TitoloLaurea)titoloLaurea));
		List<TitoloLaurea> list = criteria.list();
		t.commit();
		session.close();
		if(list.size()>0){
			res=true;
		}
		return res;
	}
		
	
	@Override
	public void delete(Integer id)  throws Exception {
		Transaction t = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			t = session.beginTransaction();
			TitoloLaurea e = (TitoloLaurea) session.load(TitoloLaurea.class, id);
			session.delete(e);
		
		} finally {
			t.commit();
			session.close();	
		}
	}
	
	
	
	@Override
	public Integer save(TitoloLaurea element) throws Exception {
		log.debug("save TitoloLaurea instance");
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

	
}
