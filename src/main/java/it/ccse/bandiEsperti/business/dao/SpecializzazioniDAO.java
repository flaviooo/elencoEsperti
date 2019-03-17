package it.ccse.bandiEsperti.business.dao;

// Generated 3-giu-2015 12.01.14 by Hibernate Tools 3.4.0.CR1

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;




import it.ccse.bandiEsperti.business.model.Competenza;
import it.ccse.bandiEsperti.business.model.Pubblicazione;
import it.ccse.bandiEsperti.business.model.Specializzazione;




import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.zkoss.zkplus.hibernate.HibernateUtil;

/**
 * Home object for domain model class Specializzazione.
 * @see it.ccse.bandiEsperti.business.dao.Specializzazione
 * @author Hibernate Tools
 */

public class SpecializzazioniDAO  extends BaseDAO implements ISpecializzazioniDAO, IAdministrationDAO {

	private static final Log log = LogFactory.getLog(SpecializzazioniDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see it.ccse.bandiEsperti.business.dao.IPaesiDAO#persist(it.ccse.bandiEsperti.business.model.Paesi)
	 */
	@Override
	public void persist(Specializzazione transientInstance) throws Exception {
		log.debug("persisting Specializzazione instance");
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
	public void remove(Specializzazione persistentInstance) throws Exception {
		log.debug("removing Specializzazione instance");
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
	public Specializzazione merge(Specializzazione detachedInstance) throws Exception {
		log.debug("merging Specializzazione instance");
		try {
			Specializzazione result = entityManager.merge(detachedInstance);
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
	public Specializzazione findById(Integer id) throws Exception {
		log.debug("getting Paesi instance with id: " + id);
		try {
			Specializzazione instance = (Specializzazione)HibernateUtil.getSessionFactory().openSession().get(Specializzazione.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	
	@Override
	public Set<Specializzazione> findAll() throws Exception {
		Session session = null;
        Transaction transation = null;
        Set<Specializzazione> listaSpecializzazioni = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transation = session.beginTransaction();
			Criteria criteria = session.createCriteria(Specializzazione.class);
			criteria.addOrder(Order.asc("nome"));
			listaSpecializzazioni= new HashSet<Specializzazione>(criteria.list());
		} finally {
			 transation.commit();
			 session.close();
		}
		return listaSpecializzazioni;
	}
	
	@Override
	public Integer save(Serializable element) throws Exception {
		log.debug("save Delibera instance");
		Integer id = null;
		Session session = null;
        Transaction transation = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transation = session.beginTransaction();
	        id = (Integer)session.save(element);
	    } finally {
			 transation.commit();
			 session.close();
		}
		return id;
		
	}
	
	@Override
	public void delete(Integer id) throws Exception {
		Session session = null;
        Transaction transation = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transation = session.beginTransaction();
			Specializzazione specializzazione = (Specializzazione) session.load(Specializzazione.class, id);
			session.delete(specializzazione);
		} finally {
			transation.commit();
			session.close();
		}
	}
	
	/** Merge con commit */
	@Override
	public void merge(Specializzazione element, Session session) throws Exception {
	    @SuppressWarnings("unused")
	    Specializzazione i2 = (Specializzazione) session.get(Specializzazione.class, new Integer(element.getId()));
		i2 = (Specializzazione) session.merge(element); 
	}
	
	
	@Override
	public boolean checkIfExist(Serializable specializzazione) throws Exception {
		boolean res=false;
		Session session = null;
        Transaction transation = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transation = session.beginTransaction();
			Criteria criteriaPubblicazione = session.createCriteria(Pubblicazione.class).add( Restrictions.eq("specializzazione", (Specializzazione)specializzazione));
			List<Pubblicazione> listPubblicazione = criteriaPubblicazione.list();
			if(listPubblicazione!=null && listPubblicazione.size()>0){
				res=true;
			}else{
				Criteria criteriaSpecializzazione = session.createCriteria(Competenza.class).add( Restrictions.eq("specializzazione", (Specializzazione)specializzazione));
				List<Pubblicazione> listSpecializzazione = criteriaSpecializzazione.list();
				if(listSpecializzazione!=null && listSpecializzazione.size()>0){
					res=true;
				}
			}
		} finally {
			 transation.commit();
			 session.close();
		}
		
		
		return res;
	}
	
}
